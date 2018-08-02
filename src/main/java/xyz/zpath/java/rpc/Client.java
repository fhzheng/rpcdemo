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
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * @author zhengfh
 * @date 2018/8/2
 **/
public class Client extends Observable {

    private static int port = 8879;
    private int state = 0;
    private Object result = null;

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public int getState() {
        return state;
    }

    public void start() throws InterruptedException {

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
                setResult(classResult);
                setChanged();
                notifyObservers();
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onCompleted() {
            }
        });
        channel.awaitTermination(1000000, TimeUnit.MILLISECONDS);
    }

    static class ClientObserver implements Observer {
        private Callback callback;

        @Override
        public void update(Observable o, Object arg) {
            this.callback.success(((Client) o).getResult());
        }

        ClientObserver(Observable observable, Callback callback) {
            observable.addObserver(this);
            this.callback = callback;
        }

    }

    public static void main(String[] args) throws InterruptedException {
        Client client = new Client();
        new ClientObserver(client, new Callback() {
            @Override
            public void success(Object result) {
                System.out.println(result);
            }

            @Override
            public void error(Throwable throwable) {
            }

            @Override
            public void completed() {
            }
        });
        client.start();
    }
}
