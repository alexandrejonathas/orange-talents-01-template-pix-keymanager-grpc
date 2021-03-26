package br.com.zup.chaves

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class TipoDaChaveTest {

    @Nested
    inner class CHAVE_ALEATORIA {

        @Test
        fun `deve passar quando chave for nula ou vazia`(){
            with(TipoDaChave.CHAVE_ALEATORIA){
                assertTrue(valida(null))
                assertTrue(valida(""))
            }
        }

        @Test
        fun `não deve passar quando possuir um valor`(){
            with(TipoDaChave.CHAVE_ALEATORIA){
                assertFalse(valida("Um valor qualquer"))
            }
        }

    }

    @Nested
    inner class CPF {

        @Test
        fun `deve ser passar quando possuir um valor válido`() {
            with(TipoDaChave.CPF){
                assertTrue(valida("35060731332"))
            }
        }

        @Test
        fun `não deve passar quando possuir um valor inválido`(){
            with(TipoDaChave.CPF){
                assertFalse(valida("35060731331"))
            }
        }

        @Test
        fun `não deve passar quando possuir um valor nulo ou vazio`(){
            with(TipoDaChave.CPF){
                assertFalse(valida(null))
                assertFalse(valida(""))
            }
        }

        @Test
        fun `não deve passar quando possuir letras`(){
            with(TipoDaChave.CPF){
                assertFalse(valida("3506073133a"))
            }
        }

    }

    @Nested
    inner class TELEFONE_CELULAR {

        @Test
        fun `deve ser passar quando o número for válido`(){
            with(TipoDaChave.TELEFONE_CELULAR){
                assertTrue(valida("+5581999999999"))
            }
        }

        @Test
        fun `não deve passar quando o número for inválido`(){
            with(TipoDaChave.TELEFONE_CELULAR){
                assertFalse(valida("81999999999"))
                assertFalse(valida("+5581a99999999"))
            }
        }

        @Test
        fun `não deve passar quando número for nulo ou vazio`(){
            with(TipoDaChave.TELEFONE_CELULAR){
                assertFalse(valida(null))
                assertFalse(valida(""))
            }
        }
    }

    @Nested
    inner class EMAIL {

        @Test
        fun `deve passar quando for um valor válido`(){
            with(TipoDaChave.EMAIL){
                assertTrue(valida("zup.edu@zup.com.br"))
            }
        }

        @Test
        fun `não deve passar quando for um valor inválido`(){
            with(TipoDaChave.EMAIL){
                assertFalse(valida("zup.edu@zup.com."))
            }
        }

        @Test
        fun `não deve passar quando o valor for nulo ou vazio`(){
            with(TipoDaChave.EMAIL){
                assertFalse(valida(null))
                assertFalse(valida(""))
            }
        }

    }

}