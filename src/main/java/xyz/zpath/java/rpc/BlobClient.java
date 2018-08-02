package xyz.zpath.java.rpc;

import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import xyz.zpath.java.rpc.protobuf.BlobServiceGrpc;
import xyz.zpath.java.rpc.protobuf.ClassServiceGrpc;
import xyz.zpath.java.rpc.protobuf.RpcNode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: zhengfh
 * @date: 2018/8/2
 * @time: 22:05
 */
public class BlobClient {
    private static int port = 8879;
    private static String FILE_NAME = "test.mp4";

    public static void main(String[] args) throws InterruptedException, FileNotFoundException {
        final ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:" + port)
                .usePlaintext()
                .build();
        BlobServiceGrpc.BlobServiceStub stub = BlobServiceGrpc.newStub(channel);
        RpcNode.BlobRequest request = RpcNode.BlobRequest.newBuilder()
                .setId(1)
                .build();
        File file = new File(FILE_NAME);
        final FileOutputStream fos = new FileOutputStream(file);
        stub.getBlob(request, new StreamObserver<RpcNode.BlobFrame>() {
            @Override
            public void onNext(RpcNode.BlobFrame blobFrame) {
                System.out.println(blobFrame.getId());
                int size = blobFrame.getSize();
                ByteString byteString = blobFrame.getContent();
                try {
                    byte[] buffer;
                    if (byteString.size() == blobFrame.getSize()) {
                        buffer = byteString.toByteArray();
                    } else {
                        buffer = byteString.substring(0, size).toByteArray();
                    }
                    fos.write(buffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
                channel.shutdownNow();
            }

            @Override
            public void onCompleted() {
                System.out.println("completed");
                channel.shutdownNow();
            }
        });
        channel.awaitTermination(1000000, TimeUnit.MILLISECONDS);
    }
}
