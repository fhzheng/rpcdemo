/**
 * Created by zhengfh on 2018/8/2
 *
 * @author zhengfh
 **/
package xyz.zpath.java.rpc.services;

import io.grpc.stub.StreamObserver;
import xyz.zpath.java.rpc.protobuf.MessageServiceGrpc;
import xyz.zpath.java.rpc.protobuf.RpcNode;

/**
 * @author zhengfh
 * @date 2018/8/2
 **/
public class MessageServiceImpl extends MessageServiceGrpc.MessageServiceImplBase {
    /**
     * @param request
     * @param responseObserver
     */
    @Override
    public void getMessage(RpcNode.MessageRequest request, StreamObserver<RpcNode.MessageResult> responseObserver) {
        System.out.println(request);
        RpcNode.MessageResult result = RpcNode.MessageResult.newBuilder()
                .setId(request.getId())
                .setMsg("hello world")
                .build();
        responseObserver.onNext(result);
        responseObserver.onCompleted();
    }
}
