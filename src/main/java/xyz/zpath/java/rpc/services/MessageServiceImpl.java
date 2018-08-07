/**
 * Created by zhengfh on 2018/8/2
 *
 * @author zhengfh
 **/
package xyz.zpath.java.rpc.services;

import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;
import com.sun.corba.se.impl.protocol.giopmsgheaders.MessageBase;
import io.grpc.stub.StreamObserver;
import xyz.zpath.java.rpc.annotation.RpcService;
import xyz.zpath.java.rpc.protobuf.MessageServiceGrpc;
import xyz.zpath.java.rpc.protobuf.RpcNode;

/**
 * @author zhengfh
 * @date 2018/8/2
 **/
@RpcService(value = MessageServiceGrpc.MessageServiceImplBase.class,
        namespace = "MessageSvc")
public class MessageServiceImpl implements RpcNode.MessageService.Interface {
    /**
     * <code>rpc GetMessage(.MessageRequest) returns (.MessageResult);</code>
     *
     * @param controller
     * @param request
     * @param done
     */
    @Override
    public void getMessage(RpcController controller, RpcNode.MessageRequest request, RpcCallback<RpcNode.MessageResult> done) {
        System.out.println(request);
        RpcNode.MessageResult result = RpcNode.MessageResult.newBuilder()
                .setId(1)
                .setMsg("hello world")
                .build();
        done.run(result);
    }
}
