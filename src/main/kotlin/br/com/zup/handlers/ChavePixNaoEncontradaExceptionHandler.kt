package br.com.zup.handlers

import br.com.zup.exceptions.ChaveNaoEncontradaException
import io.grpc.Status
import javax.inject.Singleton

@Singleton
class ChavePixNaoEncontradaExceptionHandler
    : ExceptionHandler<ChaveNaoEncontradaException> {
    override fun handle(e: ChaveNaoEncontradaException): StatusWithDetails {
        return StatusWithDetails(
            Status.NOT_FOUND.withDescription(e.message).withCause(e)
        )
    }

    override fun supports(e: Exception): Boolean {
        return e is ChaveNaoEncontradaException
    }
}