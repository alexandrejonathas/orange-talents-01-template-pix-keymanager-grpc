package br.com.zup.chaves

import br.com.zup.TipoDaConta
import br.com.zup.contas.Conta
import br.com.zup.contas.TipoConta
import br.com.zup.validations.ValidPixKey
import br.com.zup.validations.ValidUUID
import io.micronaut.core.annotation.Introspected
import java.time.LocalDateTime
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@ValidPixKey
@Introspected
data class NovaChavePix(
    @field:ValidUUID
    @field:NotBlank
    val clientId: String,
    @field:NotNull
    val tipoDaChave: TipoDaChave?,
    @field:Size(max = 77)
    val chave: String,
    @field:NotNull
    val tipoDaConta: TipoDaConta?
){
    fun paraChavePix(conta: Conta): ChavePix{
        return ChavePix(
            chave = if(tipoDaChave == TipoDaChave.CHAVE_ALEATORIA) UUID.randomUUID().toString() else chave,
            tipo = tipoDaChave?.name!!,
            conta = conta)
    }

    fun paraChaveRequest(conta: Conta): NovaChaveRequest {
        return NovaChaveRequest(
            KeyType.getKeyType(tipoDaChave!!),
            chave,
            BankAccount(
                conta?.instituicao!!.ispb, conta?.agencia!!, conta?.numero!!,
                AccountType.getAccountType(conta?.tipo!!)
            ),
            Owner("NATURAL_PERSON", conta.titular?.nome!!, conta?.titular!!.cpf)
        )
    }
}

data class NovaChaveRequest(
    val keyType: KeyType,
    val key: String,
    val bankAccount: BankAccount,
    val owner: Owner
)

data class NovaChaveResponse(
    val keyType: KeyType,
    val key: String,
    val bankAccount: BankAccount,
    val owner: Owner,
    val createdAt: LocalDateTime
)

data class Owner(
    val type: String,
    val name: String,
    val taxIdNumber: String
)

data class BankAccount(
    val participant: String,
    val branch: String,
    val accountNumber: String,
    val accountType: AccountType
)

enum class AccountType {
    CACC, SVGS;

    companion object {
        fun getAccountType(tipoConta: TipoConta): AccountType {
            if("CONTA_CORRENTE".equals(tipoConta.name)){
                return CACC
            }
            return SVGS
        }
    }
}

enum class KeyType {

    CPF, CNPJ, PHONE, EMAIL, RANDOM;

    companion object {
        fun getKeyType(tipoDaChave: TipoDaChave): KeyType {
            if (CPF.name.equals(tipoDaChave.name)){
                return CPF
            }else if(CNPJ.name.equals(tipoDaChave.name)){
                return CNPJ
            }else if("TELEFONE_CELULAR".equals(tipoDaChave.name.toString())){
                return PHONE
            }else if(EMAIL.name.equals(tipoDaChave.name)){
                return EMAIL
            }

            return RANDOM
        }
    }

}
