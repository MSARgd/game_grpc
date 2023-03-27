package ma.enset.services;
import io.grpc.stub.StreamObserver;
import ma.enset.intreprotors.ClientMetadataInterceptor;
import ma.enset.servers.Client;
import ma.enset.servers.GrpcServer;
import ma.enset.subs.Game;
import ma.enset.subs.GameServiceGrpc;
public class GameGrpcServices extends GameServiceGrpc.GameServiceImplBase {
    ClientMetadataInterceptor clientMetadataInterceptor;
    public void setClientMetadataInterceptor(ClientMetadataInterceptor clientMetadataInterceptor) {
        this.clientMetadataInterceptor = clientMetadataInterceptor;
    }

    final int magiqNumber =100;
    int counterClient =0;
    private GrpcServer grpcServer;
    public void setGrpcServer(GrpcServer grpcServer) {
        this.grpcServer = grpcServer;
    }
    public GameGrpcServices(GrpcServer grpcServer) {
        this.grpcServer = grpcServer;
    }

    public GameGrpcServices()
    {
    }
    @Override
    public StreamObserver<Game.guessRequest> fullDirectionStream(StreamObserver<Game.guessResponse> responseObserver) {
        return  new StreamObserver<Game.guessRequest>() {
            @Override
            public void onNext(Game.guessRequest guessRequest) {
                String clientName = guessRequest.getClientName();
                Client client = new Client(responseObserver);
                grpcServer.addClient(client);
                if(magiqNumber==guessRequest.getNumber()){
                    System.out.println("Bravo vous avez gagné");
                    Game.guessResponse response = Game.guessResponse.newBuilder()
                            .setClientName("SERVER")
                            .setIsTrue(true)
                            .setNumber(magiqNumber)
                            .setEventialitee("Bravo vous avez gagné")
                            .build();

                    responseObserver.onNext(response);
                    String clientIp = clientMetadataInterceptor.clientIp;
//                    grpcServer.brodcastingMessage(responseObserver);
                    grpcServer.brodcastingWinner("Winner is "+clientIp,responseObserver);
//                    responseObserver.onCompleted();
                } else if (magiqNumber<=guessRequest.getNumber()) {
                    System.out.println("Voter nomber est plus grand");
                    Game.guessResponse response = Game.guessResponse.newBuilder()
                            .setClientName("SERVER")
                            .setEventialitee("Voter nomber est plus grand")
                            .build();
                    responseObserver.onNext(response);
                }else {
                    System.out.println("Voter nomber est plus petit");
                    Game.guessResponse response = Game.guessResponse.newBuilder()
                            .setClientName("SERVER")
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
                try {
                    responseObserver.onCompleted();
                }catch (Exception e){

                }

            }
        };
    }

}