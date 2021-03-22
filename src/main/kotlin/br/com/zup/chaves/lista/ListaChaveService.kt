package br.com.zup.chaves.lista

import br.com.zup.chaves.ChavePix
import br.com.zup.chaves.ChavePixRepository
import io.micronaut.validation.Validated
import javax.inject.Singleton
import javax.validation.Valid

@Validated
@Singleton
class ListaChaveService(
    val chavePixRepository: ChavePixRepository
) {
    fun lista(@Valid dadosParaConsulta: ListaChavePix?): List<ChavePix> {
        return chavePixRepository.findByTitularId(dadosParaConsulta?.clienteId!!)
    }

}
