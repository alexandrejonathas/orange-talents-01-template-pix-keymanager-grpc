package br.com.zup.chaves

import br.com.zup.contas.Conta
import java.time.LocalDateTime

data class NovaChaveResponse(
    val keyType: KeyType,
    val key: String,
    val bankAccount: BankAccountResponse,
    val owner: OwnerResponse,
    val createdAt: LocalDateTime
){
    fun paraChave(conta: Conta): Chave {
        return Chave(key, keyType.name, conta, createdAt)
    }
}