package xyz.zpath.java.netty.client;

import xyz.zpath.java.netty.client.service.impl.ClassServiceImpl;
import xyz.zpath.java.rpc.protobuf.RpcNode;

import java.util.concurrent.ExecutionException;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: zhengfh
 * @date: 2018/8/1
 * @time: 21:14
 */
public class RpcClient {

    public static void main(String[] args) {
        ClassServiceImpl client = new ClassServiceImpl();
        //TODO 使用转换方法包裹生成protobuf的过程
        for (int i = 0; i < 10; i++) {
            RpcNode.ClassResult result = client.deleteClass(
                    RpcNode.ClassRequest.newBuilder()
                            .setId(i)
                            .build());
            System.out.println(result);
        }
    }
}
