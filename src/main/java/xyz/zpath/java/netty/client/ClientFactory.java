/**
 * Created by zhengfh on 2018/8/7
 *
 * @author zhengfh
 **/
package xyz.zpath.java.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.DefaultPromise;

/**
 * @author zhengfh
 * @date 2018/8/7
 **/
public class ClientFactory {
    private static ClientFactory factory;

    private String host = "127.0.0.1";
    private int port = 28552;

    public static ClientFactory getFactory() {
        if (factory == null) {
            factory = new ClientFactory();
        }
        return factory;
    }

    public DefaultPromise<Object> newPromise(String serviceType, String serviceMethod, String requestType, Object requestValue) {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        DefaultPromise<Object> promise = new DefaultPromise<Object>(workerGroup.next());
        try {
            Bootstrap bootstrap = new Bootstrap()
                    .group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ClientHandler(promise, serviceType, serviceMethod, requestType, requestValue));
            ChannelFuture f = bootstrap.connect(host, port);
            f.channel().closeFuture().await();
            return promise;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
        return promise;
    }
}
