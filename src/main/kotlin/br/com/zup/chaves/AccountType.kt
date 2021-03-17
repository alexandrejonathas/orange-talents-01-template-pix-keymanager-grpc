package br.com.zup.chaves

import br.com.zup.contas.TipoConta

enum class AccountType {
    CACC, SVGS;

    companion object {
        fun getAccountType(tipoConta: TipoConta): AccountType{
            if("CONTA_CORRENTE".equals(tipoConta.name)){
                return CACC
            }
            return SVGS
        }
    }
}