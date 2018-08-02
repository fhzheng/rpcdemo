package xyz.zpath.java.rpc.services;

import io.grpc.stub.StreamObserver;
import xyz.zpath.java.rpc.annotation.RpcService;
import xyz.zpath.java.rpc.protobuf.RpcNode;
import xyz.zpath.java.rpc.protobuf.SchoolServiceGrpc;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: zhengfh
 * @date: 2018/8/1
 * @time: 23:19
 */
@RpcService(name = "SchoolService")
public class SchoolServiceImpl extends SchoolServiceGrpc.SchoolServiceImplBase {
    /**
     * @param request
     * @param responseObserver
     */
    @Override
    public void getSchool(RpcNode.SchoolRequest request, StreamObserver<RpcNode.SchoolResult> responseObserver) {
        System.out.println(request);
        RpcNode.SchoolResult result = RpcNode.SchoolResult.newBuilder()
                .setId(request.getId())
                .setName("测试学校")
                .setAddress("武汉市")
                .build();
        responseObserver.onNext(result);
        responseObserver.onCompleted();
    }
}
