package br.com.zup.exceptions

import java.lang.RuntimeException

class ChavePixInvalidaException(message: String = "chave pix inválida"): RuntimeException(message)