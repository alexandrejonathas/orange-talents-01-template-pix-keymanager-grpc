package br.com.zup.chaves

data class OwnerRequest(
    val type: String,
    val name: String,
    val taxIdNumber: String
)