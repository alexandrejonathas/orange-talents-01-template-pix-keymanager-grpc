package br.com.zup.chaves.cosulta

import br.com.zup.validations.ValidUUID
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Introspected
data class ConsultaChavePix(
    @field:NotNull
    val chaveId: Long,
    @field:ValidUUID
    @field:NotBlank
    val clienteId: String,
)