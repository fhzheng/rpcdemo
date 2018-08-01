package xyz.zpath.java.netty.client;


import xyz.zpath.java.rpc.protobuf.Client;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: zhengfh
 * @date: 2018/8/1
 * @time: 21:26
 */
public class RpcClient {
    public static void main(String[] args) {
        Client.Person person = Client.Person.newBuilder()
                .setId(1)
                .setEmail("mtdwss@gmail.com")
                .setName("zhengfh")
                .build();
        System.out.println(person);
    }
}
