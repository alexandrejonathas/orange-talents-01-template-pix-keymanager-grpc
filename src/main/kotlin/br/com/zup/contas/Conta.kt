package br.com.zup.contas

import javax.persistence.*

@Entity
@Table(name = "contas")
class Conta(
    var agencia: String?,
    var numero: String?,
    @field:ManyToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    var instituicao: Instituicao?,
    @field:ManyToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    var titular: Titular?,
    @field:Enumerated(EnumType.STRING)
    var tipo: TipoConta?
) {

    @Id
    @GeneratedValue
    var id: Long? = null

}