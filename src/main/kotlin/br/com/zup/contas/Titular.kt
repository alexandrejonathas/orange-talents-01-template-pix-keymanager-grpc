package br.com.zup.contas

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "titulares")
class Titular(
    @Id
    val id: String,
    val nome: String,
    val cpf: String
)
