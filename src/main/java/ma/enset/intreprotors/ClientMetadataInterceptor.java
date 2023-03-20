package ma.enset.intreprotors;

import io.grpc.*;
public class ClientMetadataInterceptor implements ServerInterceptor {

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
            ServerCall<ReqT, RespT> call,
            Metadata headers,
            ServerCallHandler<ReqT, RespT> next) {

        // retrieve client IP address
        String clientIp = call.getAttributes().get(Grpc.TRANSPORT_ATTR_REMOTE_ADDR).toString();

        // retrieve client metadata
        String metadataValue = headers.get(Metadata.Key.of("metadata-key", Metadata.ASCII_STRING_MARSHALLER));

        ServerCall.Listener<ReqT> listener = next.startCall(call, headers);

        return new ForwardingServerCallListener.SimpleForwardingServerCallListener<ReqT>(listener) {

            @Override
            public void onMessage(ReqT message) {
                // do something with the message
                super.onMessage(message);
            }

            @Override
            public void onCancel() {
                // handle cancelation of the call
                super.onCancel();
            }

            @Override
            public void onComplete() {
                // handle completion of the call
                super.onComplete();
            }

            @Override
            public void onHalfClose() {
                // handle half-closure of the call
                super.onHalfClose();
            }

            @Override
            public void onReady() {
                // handle readiness of the call
                super.onReady();
            }
        };
    }
}

