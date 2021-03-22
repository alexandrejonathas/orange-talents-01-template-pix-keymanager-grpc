package br.com.zup.exceptions

import java.lang.RuntimeException

class AcessoNegadoException(message: String = "Acesso negado"): RuntimeException(message)