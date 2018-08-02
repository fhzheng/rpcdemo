package xyz.zpath.java.rpc.services;

import com.google.protobuf.ByteString;
import io.grpc.stub.StreamObserver;
import xyz.zpath.java.rpc.annotation.RpcService;
import xyz.zpath.java.rpc.protobuf.BlobServiceGrpc;
import xyz.zpath.java.rpc.protobuf.RpcNode;

import java.io.*;
import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: zhengfh
 * @date: 2018/8/2
 * @time: 22:10
 */
@RpcService(name = "BlboService")
public class BlobServiceImpl extends BlobServiceGrpc.BlobServiceImplBase {
    private static final int SIZE = 1024 * 5;
    public static final String FILE_NAME = "test1.mp4";

    /**
     * @param request
     * @param responseObserver
     */
    @Override
    public void getBlob(RpcNode.BlobRequest request, StreamObserver<RpcNode.BlobFrame> responseObserver) {
        getBlobInternel(request, responseObserver);
    }

    private void getBlobInternel(RpcNode.BlobRequest request, StreamObserver<RpcNode.BlobFrame> responseObserver) {
        System.out.println(request);
        try {
            byte[] buffer = new byte[SIZE];
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(FILE_NAME));
            int i = 0;
            int id = 0;
            while (bis.available() > 0) {
                Arrays.fill(buffer, (byte) 0);
                int readSize = bis.read(buffer, i * SIZE, SIZE);
                ByteString byteString = ByteString.copyFrom(buffer);
                RpcNode.BlobFrame frame = RpcNode.BlobFrame.newBuilder()
                        .setContent(byteString)
                        .setId(id)
                        .setSize(readSize)
                        .build();
                responseObserver.onNext(frame);
                id++;
            }
            responseObserver.onCompleted();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
