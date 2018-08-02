/**
 * Created by zhengfh on 2018/8/2
 *
 * @author zhengfh
 **/
package xyz.zpath.java.rpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import xyz.zpath.java.rpc.protobuf.ClassServiceGrpc;
import xyz.zpath.java.rpc.protobuf.MessageServiceGrpc;
import xyz.zpath.java.rpc.protobuf.RpcNode;
import xyz.zpath.java.utils.Callback;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * @author zhengfh
 * @date 2018/8/2
 **/
public class Client {
    private static int port = 8879;

    public Client(final Callback callback) throws InterruptedException {
        final ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:" + port)
                .usePlaintext()
                .build();
        ClassServiceGrpc.ClassServiceStub stub = ClassServiceGrpc.newStub(channel);
        RpcNode.ClassRequest request = RpcNode.ClassRequest.newBuilder()
                .setId(1)
                .build();
        stub.getClass(request, new StreamObserver<RpcNode.ClassResult>() {
            @Override
            public void onNext(RpcNode.ClassResult classResult) {
                callback.success(classResult);
            }

            @Override
            public void onError(Throwable throwable) {
                callback.error(throwable);
            }

            @Override
            public void onCompleted() {
                channel.shutdownNow();
                callback.completed();
            }
        });
        channel.awaitTermination(1000000, TimeUnit.MILLISECONDS);
    }

    public static void main(String[] args) throws InterruptedException {
        new Client(new Callback() {
            @Override
            public void success(Object result) {
                System.out.println(result);
            }

            @Override
            public void error(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void completed() {
                System.out.println("completed");
            }
        });
    }
}
