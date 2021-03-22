package br.com.zup.chaves.cosulta

import br.com.zup.chaves.ChavePix
import br.com.zup.chaves.ChavePixClient
import br.com.zup.chaves.ChavePixRepository
import br.com.zup.contas.ContaClient
import br.com.zup.exceptions.AcessoNegadoException
import br.com.zup.exceptions.ChavePixInvalidaException
import br.com.zup.exceptions.ChavePixNaoEncontradaException
import br.com.zup.validations.ValidUUID
import io.micronaut.core.annotation.Introspected
import io.micronaut.http.HttpStatus
import java.lang.IllegalStateException
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Introspected
sealed class Filtro {

    abstract fun filtra(repository: ChavePixRepository, chavePixClient: ChavePixClient): ChavePix

    @Introspected
    data class PorPixId(
        @field:NotBlank @field:ValidUUID val clienteId: String,
        @field:NotNull val chaveId: Long
    ): Filtro() {

        fun chaveId() = chaveId

        fun clienteId() = UUID.fromString(clienteId)

        override fun filtra(repository: ChavePixRepository, chavePixClient: ChavePixClient):
                ChavePix {
            val possivelChave = repository.findById(chaveId)
            if(possivelChave.isEmpty){
                throw ChavePixNaoEncontradaException("chave pix ${chaveId} não encontrada")
            }
            val chavePix = possivelChave.get()
            if(!chavePix.pertence(clienteId)){
                throw AcessoNegadoException("Você não tem permissao para acesar o recurso")
            }

            return chavePix
        }
    }

    @Introspected
    data class PorChave(@field:NotBlank @Size(max=77) val chave: String)
        : Filtro(){
        override fun filtra(repository: ChavePixRepository, chavePixClient: ChavePixClient): ChavePix {
            val possicelChave = repository.findByChave(chave)
            if(possicelChave.isEmpty){
                throw ChavePixNaoEncontradaException("chave pix ${chave} não encontrada")
            }
            val chavePix = possicelChave.get()
            val consultaChavePix = chavePixClient.consulta(chave)

            if(consultaChavePix.status != HttpStatus.OK){
                ChavePixInvalidaException("chave pix $chave inválida")
            }

            return chavePix
        }

    }

    @Introspected
    class Invalido(): Filtro(){
        override fun filtra(repository: ChavePixRepository, chavePixClient: ChavePixClient): ChavePix {
            throw IllegalStateException("Filtro inválido")
        }

    }

}