package br.com.zup.handlers

import br.com.zup.exceptions.ChavePixExistenteException
import io.grpc.Status
import javax.inject.Singleton
import javax.validation.ConstraintViolationException

@Singleton
class ConstraintViolationExceptionHandler: ExceptionHandler<ConstraintViolationException> {
    override fun handle(e: ConstraintViolationException): StatusWithDetails {
        return StatusWithDetails(
            Status.ALREADY_EXISTS.withDescription(e.message).withCause(e)
        )
    }

    override fun supports(e: Exception): Boolean {
        return e is ConstraintViolationException
    }
}