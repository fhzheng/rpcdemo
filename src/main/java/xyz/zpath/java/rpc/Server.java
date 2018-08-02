/**
 * Created by zhengfh on 2018/8/2
 *
 * @author zhengfh
 **/
package xyz.zpath.java.rpc;

import io.grpc.*;
import xyz.zpath.java.rpc.services.MessageServiceImpl;

import java.io.IOException;

/**
 * @author zhengfh
 * @date 2018/8/2
 **/
public class Server {
    private static int port = 8879;

    public static void main(String... args) throws IOException, InterruptedException {
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }
        io.grpc.Server server = ServerBuilder.forPort(port)
                .addService(new MessageServiceImpl())
                .build();
        server.start();
        System.out.println("Server Started at " + port);
        server.awaitTermination();
    }
}
