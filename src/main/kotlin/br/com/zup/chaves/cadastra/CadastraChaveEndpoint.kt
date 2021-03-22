package br.com.zup.chaves.cadastra

import br.com.zup.CadastraChavePixRequest
import br.com.zup.CadastraChavePixResponse
import br.com.zup.CadastraChavePixGrpcServiceGrpc
import br.com.zup.handlers.ErrorHandler
import br.com.zup.chaves.paraChave
import io.grpc.stub.StreamObserver
import org.slf4j.LoggerFactory
import javax.inject.Singleton

@ErrorHandler
@Singleton
class CadastraChaveEndpoint(
    val service: CadastraChavePixService
): CadastraChavePixGrpcServiceGrpc.CadastraChavePixGrpcServiceImplBase() {

    val logger = LoggerFactory.getLogger(this::class.java)

    override fun cadastrar(request: CadastraChavePixRequest?,
                           responseObserver: StreamObserver<CadastraChavePixResponse>?) {

        logger.info("Dados da request $request")

        val novaChave = request?.paraChave()
        val chaveCriada = service.registra(novaChave)

        val response = CadastraChavePixResponse.newBuilder()
            .setKeyId(chaveCriada.id.toString()).build()

        responseObserver!!.onNext(response)
        responseObserver.onCompleted()
    }

}

