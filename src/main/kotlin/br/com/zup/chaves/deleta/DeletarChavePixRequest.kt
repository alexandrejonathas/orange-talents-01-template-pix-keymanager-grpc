package br.com.zup.chaves.deleta

import br.com.zup.validations.ValidUUID
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Introspected
data class DeletarChavePixRequest(
  @field:NotNull
  val chaveId: Long,

  @ValidUUID
  @field:NotBlank
  val clienteId: String,
)

data class DeletarChaveXMLRequest(
  val key: String,
  val participant: String
)

data class DeletarChaveXMLProblema(
  val type: String,
  val status: Int,
  val title: String,
  val detail: String,
  val violations: List<DeletarChaveXMLProblemaDetail>
)

data class DeletarChaveXMLProblemaDetail(
  val field: String,
  val message: String
)