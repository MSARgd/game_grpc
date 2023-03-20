package ma.enset.servers;

import io.grpc.Grpc;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerCall;
import ma.enset.intreprotors.ClientMetadataInterceptor;
import ma.enset.services.GameGrpcServices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GrpcServer {
    final int s =100;
    List<Client> clientList = new ArrayList<>();

    public static void main(String[] args) throws IOException, InterruptedException {

        Server server = ServerBuilder.forPort(2023)
                .addService(new GameGrpcServices())
                .intercept( new ClientMetadataInterceptor())
                .build();
        server.start();

        server.awaitTermination();
    }
    public void brodcastingWinner(String winner){

    }
}