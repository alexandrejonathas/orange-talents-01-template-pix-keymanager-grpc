package br.com.zup.handlers

import br.com.zup.exceptions.AcessoNegadoException
import io.grpc.Status
import javax.inject.Singleton

@Singleton
class AcessoNegadoExceptionHandler: ExceptionHandler<AcessoNegadoException> {
    override fun handle(e: AcessoNegadoException): StatusWithDetails {
        return StatusWithDetails(
            Status.PERMISSION_DENIED
                .withDescription(e.message)
                .withCause(e)
        )
    }

    override fun supports(e: Exception): Boolean {
        return e is AcessoNegadoException
    }
}