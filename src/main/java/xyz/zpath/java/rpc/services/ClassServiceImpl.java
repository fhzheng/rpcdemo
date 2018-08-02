/**
 * Created by zhengfh on 2018/8/2
 *
 * @author zhengfh
 **/
package xyz.zpath.java.rpc.services;

import io.grpc.stub.StreamObserver;
import xyz.zpath.java.rpc.annotation.RpcService;
import xyz.zpath.java.rpc.protobuf.ClassServiceGrpc;
import xyz.zpath.java.rpc.protobuf.RpcNode;

/**
 * @author zhengfh
 * @date 2018/8/2
 **/
@RpcService(name = "ClassService")
public class ClassServiceImpl extends ClassServiceGrpc.ClassServiceImplBase {
    /**
     * @param request
     * @param responseObserver
     */
    @Override
    public void getClass(RpcNode.ClassRequest request, StreamObserver<RpcNode.ClassResult> responseObserver) {
        System.out.println(request);
        RpcNode.ClassResult result = RpcNode.ClassResult.newBuilder()
                .setId(request.getId())
                .setName("班级1")
                .build();
        responseObserver.onNext(result);
        responseObserver.onCompleted();
    }
}
