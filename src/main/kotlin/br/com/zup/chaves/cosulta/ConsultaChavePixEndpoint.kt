package br.com.zup.chaves.cosulta

import br.com.zup.*
import br.com.zup.chaves.ChavePixClient
import br.com.zup.chaves.ChavePixRepository
import br.com.zup.chaves.paraFiltro
import br.com.zup.handlers.ErrorHandler
import io.grpc.stub.StreamObserver
import io.micronaut.validation.validator.Validator
import javax.inject.Singleton

@ErrorHandler
@Singleton
class ConsultaChavePixEndpoint(
    val repository: ChavePixRepository,
    val client: ChavePixClient,
    val validator: Validator
)
    : ConsultaChavePixGrpcServiceGrpc.ConsultaChavePixGrpcServiceImplBase() {
    override fun consultar(
        request: ConsultaChavePixRequest?,
        responseObserver: StreamObserver<ConsultaChavePixResponse>?
    ) {
        val filtro = request?.paraFiltro(validator)
        val chavePix = filtro?.filtra(repository, client)

        val response = ConsultaChavePixResponse.newBuilder()
            //.setChaveId(chaveConsulta.chaveId.toString())
            //.setClienteId(chaveConsulta.clienteId)
            .setTipoDaChave(chavePix?.tipo)
            .setChave(chavePix?.chave)
            .setTitular(TitularResponse.newBuilder()
                .setCpf(chavePix?.conta?.titular?.cpf)
                .setNome(chavePix?.conta?.titular?.nome)
            )
            .setConta(ContaResponse.newBuilder()
                .setNome(chavePix?.conta?.instituicao?.nome)
                .setAgencia(chavePix?.conta?.agencia)
                .setNumero(chavePix?.conta?.numero)
                .setTipoDaConta(chavePix?.conta?.tipo?.name)
            )
            .build()

        responseObserver!!.onNext(response)
        responseObserver.onCompleted()
    }
}