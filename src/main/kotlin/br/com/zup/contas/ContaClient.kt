package br.com.zup.contas

import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.QueryValue
import io.micronaut.http.client.annotation.Client

@Client("http://localhost:9091")
interface ContaClient {

    @Get("/api/v1/clientes/{clienteId}/contas")
    fun buscar(@PathVariable clienteId: String, @QueryValue tipo: String): ContaResponse
}