package br.com.zup.chaves

import br.com.zup.KeymanagerGrpcRequest
import br.com.zup.KeymanagerGrpcResponse
import br.com.zup.KeymanagerGrpcServiceGrpc
import io.grpc.stub.StreamObserver
import org.slf4j.LoggerFactory
import javax.inject.Singleton

@ErrorHandler
@Singleton
class CadastraChaveEndpoint(
    val service: NovaChavePixService
): KeymanagerGrpcServiceGrpc.KeymanagerGrpcServiceImplBase() {

    val logger = LoggerFactory.getLogger(this::class.java)

    override fun cadastrar(request: KeymanagerGrpcRequest?,
                           responseObserver: StreamObserver<KeymanagerGrpcResponse>?) {

        logger.info("Dados da request $request")

        val novaChave = request?.paraChave()
        val chaveCriada = service.registra(novaChave)

        val response = KeymanagerGrpcResponse.newBuilder()
            .setKeyId(chaveCriada.id.toString()).build()

        responseObserver!!.onNext(response)
        responseObserver.onCompleted()
    }

}

