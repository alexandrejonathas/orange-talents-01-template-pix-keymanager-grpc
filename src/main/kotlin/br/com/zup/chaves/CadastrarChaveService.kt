package br.com.zup.chaves

import br.com.zup.KeymanagerGrpcReply
import br.com.zup.KeymanagerGrpcRequest
import br.com.zup.KeymanagerGrpcServiceGrpc
import br.com.zup.contas.Conta
import br.com.zup.contas.ContaClient
import br.com.zup.contas.ContaRepository
import br.com.zup.contas.ContaResponse
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.grpc.stub.StreamObserver
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.exceptions.HttpClientResponseException
import org.slf4j.LoggerFactory
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CadastrarChaveService(
    val keyManagerClient: CadastrarChaveClient,
    val contaClient: ContaClient,
    @Inject val contaRepository: ContaRepository,
    @Inject val chaveRepository: ChaveRepository
): KeymanagerGrpcServiceGrpc.KeymanagerGrpcServiceImplBase() {

    val logger = LoggerFactory.getLogger(this::class.java)

    override fun cadastrar(request: KeymanagerGrpcRequest?, responseObserver: StreamObserver<KeymanagerGrpcReply>?) {

        logger.info("Dados da request $request")

        val clientId = request?.clientId
        if(clientId == null || clientId.isBlank()){
            buildError(responseObserver, Status.INVALID_ARGUMENT, "o campo clientId deve ser informado")
            return
        }

        val tipoDaChave = request?.tipoDaChave
        if(tipoDaChave == null){
            buildError(responseObserver, Status.INVALID_ARGUMENT, "o tipo da chave deve ser informado")
            return
        }

        var chave = request?.chave.let { if(it == null || it.isBlank()) UUID.randomUUID().toString() else it }

        val keyType = KeyType.getKeyType(tipoDaChave)

        if(!keyType.isValid(chave)){
            buildError(responseObserver, keyType.status, keyType.mensagem)
            return
        }

        var possivelConta = contaRepository.findByClientId(clientId);

        var conta = if(possivelConta.isPresent) possivelConta.get()
            else contaClient.buscar(clientId, "CONTA_CORRENTE").let { contaRepository.save(it.paraConta()) }

        try {
            val createPixKeyResponse = NovaChaveRequest(KeyType.getKeyType(tipoDaChave), chave,
                BankAccountRequest(conta?.instituicao!!.ispb, conta?.agencia!!, conta?.numero!!,
                AccountType.getAccountType(conta?.tipo!!)),
                OwnerRequest("NATURAL_PERSON", conta.titular?.nome!!, conta?.titular!!.cpf)
            ).let { keyManagerClient.cadastra(it) }

            var chaveEntity = createPixKeyResponse.paraChave(conta).let { chaveRepository.save(it) }

            val response = KeymanagerGrpcReply.newBuilder().setKeyId(chaveEntity.id.toString()).build()

            responseObserver!!.onNext(response)
            responseObserver.onCompleted()

        }catch (e: HttpClientResponseException){
            if(e.status == HttpStatus.UNPROCESSABLE_ENTITY){
                buildError(responseObserver, Status.ALREADY_EXISTS, "A chave pix já está cadatrada")
            }else {
                buildError(responseObserver, Status.INTERNAL, "Error inesperado")
            }
        }

    }

    private fun buildError(responseObserver: StreamObserver<KeymanagerGrpcReply>?, status: Status, message: String) {
        val error = status.withDescription(message).asRuntimeException()
        responseObserver?.onError(error)
    }

}