package br.com.zup.chaves.deleta

import br.com.zup.DeletaChavePixGrpcRequest
import br.com.zup.DeletaChavePixGrpcResponse
import br.com.zup.DeletaChavePixGrpcServiceGrpc
import br.com.zup.KeymanagerGrpcResponse
import br.com.zup.chaves.paraDeletarChaveRequest
import br.com.zup.handlers.ErrorHandler
import io.grpc.stub.StreamObserver
import org.slf4j.LoggerFactory
import javax.inject.Singleton

@ErrorHandler
@Singleton
class DeletaChaveEndpoint(
    val chaveService: DeletaChaveService
)
    : DeletaChavePixGrpcServiceGrpc.DeletaChavePixGrpcServiceImplBase() {

    val logger = LoggerFactory.getLogger(this::class.java)

    override fun deletar(
        request: DeletaChavePixGrpcRequest?,
        responseObserver: StreamObserver<DeletaChavePixGrpcResponse>?
    ) {
        logger.info("Dados da request $request")

        chaveService.deleta(request?.paraDeletarChaveRequest()!!)
        val response = DeletaChavePixGrpcResponse.newBuilder()
            .setMessage("Chave removida").build()

        responseObserver!!.onNext(response)
        responseObserver.onCompleted()
    }
}