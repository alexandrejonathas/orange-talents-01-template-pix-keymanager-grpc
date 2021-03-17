package br.com.zup.chaves

import io.micronaut.data.annotation.Query
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository
import java.util.*

@Repository
interface ChaveRepository : JpaRepository<Chave, Long> {

    @Query("select c from Chave c where c.tipo = :tipo and c.key = :key")
    fun findByTipoAndKey(tipo: KeyType, key: String): Optional<Chave>
}