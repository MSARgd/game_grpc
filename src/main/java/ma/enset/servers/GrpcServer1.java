package ma.enset.servers;


import io.grpc.stub.StreamObserver;
import ma.enset.subs.Game;
import ma.enset.subs.GameServiceGrpc;

public class GrpcServer1 extends GameServiceGrpc.GameServiceImplBase {
    @Override
    public StreamObserver<Game.guessRequest> fullDirectionStream(StreamObserver<Game.guessResponse> responseObserver) {
        return new StreamObserver<Game.guessRequest>() {
            @Override
            public void onNext(Game.guessRequest guessRequest) {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {

            }
        };
    }
}
