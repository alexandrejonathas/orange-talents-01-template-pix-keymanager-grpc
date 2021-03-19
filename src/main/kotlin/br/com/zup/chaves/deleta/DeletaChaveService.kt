package br.com.zup.chaves.deleta

import br.com.zup.chaves.ChavePixClient
import br.com.zup.chaves.ChavePixRepository
import br.com.zup.exceptions.ChaveNaoEncontradaException
import io.micronaut.http.HttpStatus
import io.micronaut.validation.Validated
import org.slf4j.LoggerFactory
import javax.inject.Singleton
import javax.transaction.Transactional
import javax.validation.Valid

@Validated
@Singleton
class DeletaChaveService(
    val chavePixRepository: ChavePixRepository,
    val chavePixClient: ChavePixClient
) {

    val logger = LoggerFactory.getLogger(this::class.java)

    @Transactional
    fun deleta(@Valid deletarChavePixRequest: DeletarChavePixRequest){
        val possivelChave = chavePixRepository.findById(deletarChavePixRequest.chaveId)

        if(possivelChave.isEmpty){
            throw ChaveNaoEncontradaException("chave pix ${deletarChavePixRequest.chaveId} não encontrada")
        }

        val chavePix = possivelChave.get()
        val request = DeletarChaveXMLRequest(
            key = chavePix.chave,
            participant = chavePix.conta.instituicao?.ispb!!)

        chavePixRepository.delete(chavePix)

        val response = chavePixClient.deleta(chavePix.chave, request)

        if(response.status != HttpStatus.OK){
            val problema = response.body() as DeletarChaveXMLProblema?
            logger.info("Problema: $problema")

            var message = if(problema != null) "${problema.title}: ${problema?.detail}"
                else "não foi possível remover a chave, tente novamente mais tarde"

            throw Exception(message)
        }

    }
}

