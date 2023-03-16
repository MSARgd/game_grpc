package ma.enset.servers;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import ma.enset.services.GameGrpcServices;

import java.io.IOException;

public class grpcServer {
    final int s =100;
    public static void main(String[] args) throws IOException, InterruptedException {

        Server server = ServerBuilder.forPort(2023)
                .addService(new GameGrpcServices())
                .build();
        server.start();
        server.awaitTermination();
    }
    public void brodcastingWinner(String winner){

    }
}