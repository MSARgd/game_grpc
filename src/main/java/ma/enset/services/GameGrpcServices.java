package ma.enset.services;
import io.grpc.stub.StreamObserver;
import ma.enset.servers.Client;
import ma.enset.servers.GrpcServer;
import ma.enset.subs.Game;
import ma.enset.subs.GameServiceGrpc;
public class GameGrpcServices extends GameServiceGrpc.GameServiceImplBase {
    final int magiqNumber =100;
    int counterClient =0;
    private GrpcServer grpcServer;

    public void setGrpcServer(GrpcServer grpcServer) {
        this.grpcServer = grpcServer;
    }

    public GameGrpcServices(GrpcServer grpcServer) {
        this.grpcServer = grpcServer;
    }

    public GameGrpcServices() {
    }
    @Override
    public StreamObserver<Game.guessRequest> fullDirectionStream(StreamObserver<Game.guessResponse> responseObserver) {
        return  new StreamObserver<Game.guessRequest>() {
            @Override
            public void onNext(Game.guessRequest guessRequest) {
                Client client = new Client(responseObserver);
                grpcServer.addClient(client);
                if(magiqNumber==guessRequest.getNumber()){
                    System.out.println("Bravo vous avez gagné");
                    Game.guessResponse response = Game.guessResponse.newBuilder()
                            .setClientName("CLIENT")
                            .setIsTrue(true)
                            .setNumber(magiqNumber)
                            .setEventialitee("Bravo vous avez gagné")
                            .build();
                    responseObserver.onNext(response);
                    grpcServer.brodcastingWinner("Winner is client1");
                    responseObserver.onCompleted();
                } else if (magiqNumber<=guessRequest.getNumber()) {
                    System.out.println("Voter nomber est plus grand");
                    Game.guessResponse response = Game.guessResponse.newBuilder()
                            .setClientName("CLIENT")
                            .setEventialitee("Voter nomber est plus grand")
                            .build();
                    responseObserver.onNext(response);
                }else {
                    System.out.println("Voter nomber est plus petit");
                    Game.guessResponse response = Game.guessResponse.newBuilder()
                            .setClientName("CLIENT")
                            .setEventialitee("Voter nomber est plus petit")
                            .build();
                    responseObserver.onNext(response);
                }
            }
            @Override
            public void onError(Throwable throwable) {
                System.out.println(throwable.getMessage());
            }
            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }
}