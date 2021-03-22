package br.com.zup.chaves.lista

import br.com.zup.ChaveResponse
import br.com.zup.ListaChavePixGrpcServiceGrpc
import br.com.zup.ListaChavePixRequest
import br.com.zup.ListaChavePixResponse
import br.com.zup.chaves.paraListaChavePix
import br.com.zup.chaves.toTimestampGrpc
import br.com.zup.handlers.ErrorHandler
import io.grpc.stub.StreamObserver
import javax.inject.Singleton

@ErrorHandler
@Singleton
class ListaChavePixEndpoint(
    val listaChaveService: ListaChaveService
): ListaChavePixGrpcServiceGrpc.ListaChavePixGrpcServiceImplBase() {

    override fun listar(request: ListaChavePixRequest?, responseObserver: StreamObserver<ListaChavePixResponse>?) {

        var dadosParaConsulta = request?.paraListaChavePix()
        var chaves = listaChaveService.lista(dadosParaConsulta)

        var chavesResponse: MutableList<ChaveResponse> = mutableListOf()

        chaves.forEach {
            chavesResponse.add(ChaveResponse.newBuilder()
                .setChaveId(it?.id!!)
                .setClienteId(it.conta.titular?.id)
                .setTipoDaChave(it.tipo)
                .setChave(it.chave)
                .setTipoDaConta(it.conta.tipo?.name)
                .setCriadoEm(it.criadoEm?.toTimestampGrpc())
                .build())
        }

        var response = ListaChavePixResponse.newBuilder()
            .addAllChaves(chavesResponse)
            .build()

        responseObserver!!.onNext(response)
        responseObserver.onCompleted()
    }

}