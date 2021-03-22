package br.com.zup.chaves

import br.com.zup.chaves.deleta.DeletarChaveXMLRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import io.micronaut.http.client.annotation.Client

@Client("\${bcb.pix.server-url}")
interface ChavePixClient {

    @Post("/api/v1/pix/keys",
        consumes = [MediaType.APPLICATION_XML], produces = [MediaType.APPLICATION_XML])
    fun cadastra(@Body createPixKeyRequest: NovaChaveRequest): NovaChaveResponse

    @Delete("/api/v1/pix/keys/{key}",
        consumes = [MediaType.APPLICATION_XML], produces = [MediaType.APPLICATION_XML])
    fun deleta(@PathVariable key: String, @Body deletePixKeyRequest: DeletarChaveXMLRequest): HttpResponse<Any>

    @Get("/api/v1/pix/keys/{key}",
        consumes = [MediaType.APPLICATION_XML], produces = [MediaType.APPLICATION_XML])
    fun consulta(@PathVariable key: String): HttpResponse<Any>

}