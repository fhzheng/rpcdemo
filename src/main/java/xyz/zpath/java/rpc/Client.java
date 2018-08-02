/**
 * Created by zhengfh on 2018/8/2
 *
 * @author zhengfh
 **/
package xyz.zpath.java.rpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import xyz.zpath.java.rpc.protobuf.MessageServiceGrpc;
import xyz.zpath.java.rpc.protobuf.RpcNode;

import java.util.concurrent.TimeUnit;

/**
 * @author zhengfh
 * @date 2018/8/2
 **/
public class Client {
    private static int port = 8879;

    public static void main(String[] args) throws InterruptedException {
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }
        final ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:" + port)
                .usePlaintext()
                .build();
        MessageServiceGrpc.MessageServiceStub stub = MessageServiceGrpc.newStub(channel);
        for (int i = 0; i < 100; i++) {

            RpcNode.MessageRequest request = RpcNode.MessageRequest.newBuilder()
                    .setId(i)
                    .build();
            final int finalI = i;
            stub.getMessage(request, new StreamObserver<RpcNode.MessageResult>() {
                @Override
                public void onNext(RpcNode.MessageResult messageResult) {
                    System.out.println(messageResult);
                }

                @Override
                public void onError(Throwable throwable) {
                    throwable.printStackTrace();
                }

                @Override
                public void onCompleted() {
                    System.out.println(finalI + " completed");
                    channel.shutdownNow();
                }
            });
        }
        channel.awaitTermination(1000000, TimeUnit.MILLISECONDS);
    }
}
