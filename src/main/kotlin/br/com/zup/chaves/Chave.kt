package br.com.zup.chaves

import br.com.zup.contas.Conta
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "chaves")
class Chave(
    @Column(name = "pix")
    val key: String,
    val tipo: String,
    @field:ManyToOne
    val conta: Conta,
    val criadoEm: LocalDateTime?) {

    @Id
    @GeneratedValue
    var id: Long? = null

}
