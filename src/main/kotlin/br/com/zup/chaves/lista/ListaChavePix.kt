package br.com.zup.chaves.lista

import br.com.zup.validations.ValidUUID
import javax.validation.constraints.NotBlank

class ListaChavePix(
    @field:ValidUUID
    @field:NotBlank val clienteId: String
) {
}