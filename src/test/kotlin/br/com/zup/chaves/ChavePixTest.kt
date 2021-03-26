package br.com.zup.chaves

import br.com.zup.contas.Conta
import br.com.zup.contas.Instituicao
import br.com.zup.contas.TipoConta
import br.com.zup.contas.Titular
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*

internal class ChavePixTest {

    companion object {
        val TIPOS_DE_CHAVES_EXCETO_ALEATORIA = TipoDaChave.values().filterNot {
            it == TipoDaChave.CHAVE_ALEATORIA
        }
    }

    @Test
    fun `deve passar quando chave pertencer ao cliente`(){
        val clienteId = UUID.randomUUID().toString()

        with(novaChave(tipo = TipoDaChave.CHAVE_ALEATORIA, clienteId = clienteId)){
            assertTrue(pertence(clienteId))
        }
    }

    @Test
    fun `não deve passar quando chave pertencer a outro cliente`(){
        val clienteId = UUID.randomUUID().toString()
        val outroClienteId = UUID.randomUUID().toString()

        with(novaChave(tipo = TipoDaChave.CHAVE_ALEATORIA, clienteId = clienteId)){
            assertFalse(pertence(outroClienteId))
        }
    }

    @Test
    fun `deve passar quando a chave for aleatoria`(){
        with(novaChave(tipo = TipoDaChave.CHAVE_ALEATORIA)){
            assertTrue(this.isAleatoria())
        }
    }

    @Test
    fun `não deve passar quando a chave for do tipo aleatoria`(){
        TIPOS_DE_CHAVES_EXCETO_ALEATORIA.forEach {
            println(it.name)
            assertFalse(novaChave(tipo = it).isAleatoria())
        }
    }

    @Test
    fun `deve atualizar chave quando o tipo for aleatoria`(){
        with(novaChave(tipo = TipoDaChave.CHAVE_ALEATORIA)){
            assertTrue(atualiza("nova-chave"))
            assertEquals("nova-chave", chave)
        }
    }

    @Test
    fun `não deve atualizar chave quando o tipo não for aleatoria`(){
        val chaveOriginal = "chave-original"
        TIPOS_DE_CHAVES_EXCETO_ALEATORIA.forEach {
            with(novaChave(tipo = it, chave = chaveOriginal)){
                assertFalse(atualiza("nova-chave"))
                assertEquals(chaveOriginal, chave)
            }
        }
    }

    private fun novaChave(
        tipo: TipoDaChave,
        chave: String = UUID.randomUUID().toString(),
        clienteId: String = UUID.randomUUID().toString()
    ): ChavePix{
        return ChavePix(
            chave = chave,
            tipo = tipo.name,
            conta = Conta(
                agencia = "1234",
                numero = "56789",
                instituicao = Instituicao("ITAU UNIBANCO SA", "607890"),
                titular = Titular(
                    id = clienteId,
                    cpf = "999.999.999-99",
                    nome = "João das Couves"
                ),
                tipo = TipoConta.CONTA_CORRENTE
            )
        )
    }

}