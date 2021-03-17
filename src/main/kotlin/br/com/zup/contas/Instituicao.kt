package br.com.zup.contas

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "instituicoes")
class Instituicao(
    val nome: String,
    val ispb: String
) {
    @Id
    @GeneratedValue
    var id: Long? = null
}
