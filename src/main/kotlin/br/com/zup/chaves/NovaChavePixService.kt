package br.com.zup.chaves

import br.com.zup.contas.ContaClient
import br.com.zup.contas.ContaRepository
import br.com.zup.exceptions.ChavePixExistenteException
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.validation.Validated
import org.slf4j.LoggerFactory
import javax.inject.Singleton
import javax.transaction.Transactional
import javax.validation.Valid

@Validated
@Singleton
class NovaChavePixService(
    val chaveRepository: ChavePixRepository,
    val contaRepository: ContaRepository,
    val contaClient: ContaClient,
    val keyManagerClient: CadastrarChaveClient,
) {

    val logger = LoggerFactory.getLogger(this::class.java)

    @Transactional
    fun registra(@Valid novaChave: NovaChavePix?): ChavePix {

        val possivelChave = chaveRepository.findByChave(novaChave?.chave!!)
        if(possivelChave.isPresent){
            throw ChavePixExistenteException("Chave Pix ${novaChave?.chave} existente")
        }

        var possivelConta = contaRepository.findByClientId(novaChave.clientId);

        var conta = if (possivelConta.isPresent) possivelConta.get()
        else contaClient.buscar(novaChave.clientId, novaChave?.tipoDaConta?.name!!)
            .let { contaResponse -> contaRepository.save(contaResponse.paraConta()) }

        try {
            val createPixResponse = novaChave?.paraChaveRequest(conta)
                .let { chavePix -> keyManagerClient.cadastra(chavePix) }

            var chaveCriada = novaChave.paraChavePix(conta, createPixResponse.createdAt)
                .let { chavePix ->
                chaveRepository.save(chavePix)
            }

            return chaveCriada
        } catch (e: HttpClientResponseException) {
            throw ChavePixExistenteException("A chave pix ${novaChave.chave} já está cadatrada")
        }
    }

}
