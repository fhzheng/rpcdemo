package xyz.zpath.java.netty.client;


import xyz.zpath.java.rpc.protobuf.Client;
import xyz.zpath.java.rpc.protobuf.RpcNode;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: zhengfh
 * @date: 2018/8/1
 * @time: 21:26
 */
public class RpcClient {
    public static void main(String[] args) {
        RpcNode.Caller caller = RpcNode.Caller.newBuilder()
                .setId(1)
                .setMethod("getUserInfo")
                .setParamter("1234567788").build();
        int serializedSize = caller.getSerializedSize();
        System.out.println(caller);
        System.out.println(serializedSize);
    }
}
