package br.com.zup.chaves

import br.com.zup.*
import br.com.zup.ConsultaChavePixRequest.FiltroCase.*
import br.com.zup.TipoDaChave
import br.com.zup.chaves.cosulta.Filtro
import br.com.zup.chaves.deleta.DeletarChavePixRequest
import br.com.zup.chaves.lista.ListaChavePix
import com.google.protobuf.Timestamp
import io.micronaut.validation.validator.Validator
import java.time.LocalDateTime
import javax.validation.ConstraintViolationException
import java.time.ZoneOffset

import java.time.Instant




fun CadastraChavePixRequest.paraChave() : NovaChavePix {
    return NovaChavePix(
        clientId = clientId,
        tipoDaChave = when(tipoDaChave) {
            TipoDaChave.UNKNOWN_TIPO_CHAVE -> null
            else -> br.com.zup.chaves.TipoDaChave.valueOf(tipoDaChave.name)
        },
        chave = chave,
        tipoDaConta = when(tipoDaConta) {
            TipoDaConta.UNKNOWN_TIPO_CONTA -> null
            else -> TipoDaConta.valueOf(tipoDaConta.name)
        }
    )
}

fun DeletaChavePixGrpcRequest.paraDeletarChaveRequest(): DeletarChavePixRequest {
    return DeletarChavePixRequest(
        chaveId = chaveId,
        clienteId = clienteId
    )
}

fun ConsultaChavePixRequest.paraFiltro(
    validator: Validator
): Filtro {

    var filtro = when(filtroCase) {
        CHAVEID -> chaveId.let {
            Filtro.PorPixId(clienteId = it.clienteId, chaveId = it.chaveId)
        }
        CHAVE -> Filtro.PorChave(chave)
        FILTRO_NOT_SET -> Filtro.Invalido()
    }

    val violations = validator.validate(filtro)
    if(violations.isNotEmpty()){
        throw ConstraintViolationException(violations)
    }
    return filtro
}

fun ListaChavePixRequest.paraListaChavePix(): ListaChavePix {
    return ListaChavePix(clienteId = clienteId)
}

fun LocalDateTime.toTimestampGrpc(): Timestamp {

    val instant: Instant = this.toInstant(ZoneOffset.UTC)

    return Timestamp.newBuilder()
        .setSeconds(instant.epochSecond)
        .setNanos(instant.nano)
        .build()
}