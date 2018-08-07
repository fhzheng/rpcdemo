package xyz.zpath.java.netty.client;

import com.google.protobuf.Any;
import io.grpc.BindableService;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import xyz.zpath.java.rpc.annotation.RpcService;
import xyz.zpath.java.rpc.protobuf.RpcNode;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: zhengfh
 * @date: 2018/8/1
 * @time: 21:10
 */
@Deprecated
public class RpcClientHandler extends ChannelInboundHandlerAdapter{
    private ClientResponse callback;

    public RpcClientHandler() {
//        this.callback = clientResponse;
    }
/*
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("connected");
        RpcNode.RpcRequest.Builder builder = RpcNode.RpcRequest.newBuilder();
        RpcNode.ClassRequest classRequest = RpcNode.ClassRequest.newBuilder()
                .setId(1)
                .build();
        Any any = Any.pack(classRequest);
        builder.setServiceMethod("getClass")
                .setServiceType(RpcNode.ClassService.class.getName())
                .setRequestType(RpcNode.ClassRequest.class.getName())
                .setRealRequest(any);
        RpcNode.RpcRequest rpcRequest = builder.build();
        ctx.writeAndFlush(Unpooled.copiedBuffer(rpcRequest.toByteArray()));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        byte[] message = new byte[in.readableBytes()];
        in.getBytes(0, message);
        RpcNode.ClassResult classResult = RpcNode.ClassResult.parseFrom(message);
        System.out.println("Msg from server: " + classResult.toString());
        ctx.close();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }*/

}
