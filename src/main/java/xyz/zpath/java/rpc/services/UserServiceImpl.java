package xyz.zpath.java.rpc.services;

import io.grpc.stub.StreamObserver;
import xyz.zpath.java.rpc.annotation.RpcService;
import xyz.zpath.java.rpc.protobuf.RpcNode;
import xyz.zpath.java.rpc.protobuf.UserServiceGrpc;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: zhengfh
 * @date: 2018/8/1
 * @time: 23:19
 */
@RpcService(value = UserServiceGrpc.UserServiceImplBase.class,
        namespace = "UserSvc")
public class UserServiceImpl extends UserServiceGrpc.UserServiceImplBase {
    /**
     * @param request
     * @param responseObserver
     */
    @Override
    public void getUser(RpcNode.UserRequest request, StreamObserver<RpcNode.UserResult> responseObserver) {
        System.out.println(request);
        RpcNode.UserResult result = RpcNode.UserResult.newBuilder()
                .setId(1)
                .setName(request.getName())
                .setAddress("wuhan")
                .build();
        responseObserver.onNext(result);
        responseObserver.onCompleted();
    }
}
