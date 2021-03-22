package br.com.zup.chaves.cosulta

import br.com.zup.chaves.ChavePix
import br.com.zup.chaves.ChavePixRepository
import br.com.zup.exceptions.AcessoNegadoException
import br.com.zup.exceptions.ChavePixNaoEncontradaException
import io.micronaut.validation.Validated
import javax.inject.Singleton
import javax.validation.Valid

@Validated
@Singleton
class ConsultaChavePixService(
    val chavePixRepository: ChavePixRepository
) {
    fun consulta(@Valid chaveConsulta: ConsultaChavePix): ChavePix? {
        val possivelChave = chavePixRepository.findById(chaveConsulta.chaveId)
        if(possivelChave.isEmpty){
            throw ChavePixNaoEncontradaException("chave pix ${chaveConsulta.chaveId} não encontrada")
        }
        val chavePix = possivelChave.get()
        if(!chavePix.pertence(chaveConsulta.clienteId)){
            throw AcessoNegadoException("Você não tem permissao para acesar o recurso")
        }

        return chavePix
    }
}
