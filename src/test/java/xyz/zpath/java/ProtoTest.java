package xyz.zpath.java;

import io.grpc.stub.StreamObserver;
import org.junit.Test;
import xyz.zpath.java.rpc.protobuf.MessageServiceGrpc;
import xyz.zpath.java.rpc.protobuf.RpcNode;

import static org.junit.Assert.*;

/**
 * @author zhengfh
 * @date 2018/8/2
 **/
public class ProtoTest extends MessageServiceGrpc.MessageServiceImplBase {

    @Test
    public void main() {
    }

    /**
     * @param request
     * @param responseObserver
     */
    @Override
    public void getMessage(RpcNode.MessageRequest request,
                           StreamObserver<RpcNode.MessageResult> responseObserver) {
        System.out.println(request);
        RpcNode.MessageResult result = RpcNode.MessageResult.newBuilder()
                .setId(request.getId())
                .setMsg("hello world")
                .build();
        responseObserver.onNext(result);
        responseObserver.onCompleted();
    }
}