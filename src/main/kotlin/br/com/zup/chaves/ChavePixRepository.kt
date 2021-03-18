package br.com.zup.chaves

import io.micronaut.data.annotation.Query
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository
import java.util.*

@Repository
interface ChavePixRepository : JpaRepository<ChavePix, Long> {

    @Query("select c from ChavePix c where c.chave = :chave")
    fun findByChave(chave: String): Optional<ChavePix>
}