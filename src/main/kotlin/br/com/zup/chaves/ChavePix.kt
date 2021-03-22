package br.com.zup.chaves

import br.com.zup.contas.Conta
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
@Table(name = "chaves_pix")
class ChavePix(
    @field:NotBlank
    var chave: String,
    @field:NotNull
    val tipo: String,
    @field:ManyToOne
    val conta: Conta) {

    @Id
    @GeneratedValue
    var id: Long? = null

    var criadoEm: LocalDateTime? = null

    fun pertence(clienteId: String): Boolean{
        return this.conta.titular?.id.equals(clienteId)
    }
}
