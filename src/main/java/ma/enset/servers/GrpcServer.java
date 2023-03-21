package ma.enset.servers;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import ma.enset.intreprotors.ClientMetadataInterceptor;
import ma.enset.services.GameGrpcServices;
import ma.enset.subs.Game;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class GrpcServer {
    final int s =100;
    List<Client> clientList = new ArrayList<>();
    public void addClient(Client client) {
        clientList.add(client);
    }
    public void removeClient(Client client) {
        clientList.remove(client);
    }
    public void startServer(GameGrpcServices gameGrpcServices) throws IOException, InterruptedException {
        ClientMetadataInterceptor clientMetadataInterceptor = new ClientMetadataInterceptor();
        gameGrpcServices.setClientMetadataInterceptor(clientMetadataInterceptor);
        Server server = ServerBuilder.forPort(2023)
                .addService(gameGrpcServices)
                .intercept(clientMetadataInterceptor)
                .build();
        server.start();
        server.awaitTermination();
    }
    public static void main(String[] args) throws IOException, InterruptedException {
        GameGrpcServices gameGrpcServices = new GameGrpcServices();
        GrpcServer grpcServer = new GrpcServer();
        gameGrpcServices.setGrpcServer(grpcServer);
        grpcServer.startServer(gameGrpcServices);
    }
    public void brodcastingWinner(String winner,StreamObserver<Game.guessResponse> responseObserver){
        Game.guessResponse response = Game.guessResponse.newBuilder()
                .setClientName("SERVER")
                .setEventialitee(winner)
                .build();
        for (Client client : clientList) {
            if (client.getResponseObserver()!=responseObserver){
                client.getResponseObserver().onNext(response);
                client.getResponseObserver().onCompleted();
            }else {
                client.getResponseObserver().onCompleted();
            }
        }
    }
}