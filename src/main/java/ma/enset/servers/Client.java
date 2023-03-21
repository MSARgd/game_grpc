package ma.enset.servers;
import io.grpc.stub.StreamObserver;
import ma.enset.subs.Game;
public class Client {
    private final StreamObserver<Game.guessResponse> responseObserver;
    public Client(StreamObserver<Game.guessResponse> responseObserver) {
        this.responseObserver = responseObserver;
    }
    public StreamObserver<Game.guessResponse> getResponseObserver() {
        return responseObserver;
    }
}
