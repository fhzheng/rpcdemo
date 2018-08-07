/**
 * Created by zhengfh on 2018/8/7
 *
 * @author zhengfh
 **/
package xyz.zpath.java.netty.client;

import com.google.protobuf.Any;
import com.google.protobuf.Message;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.DefaultPromise;
import xyz.zpath.java.rpc.protobuf.RpcNode;

/**
 * @author zhengfh
 * @date 2018/8/7
 **/
public class ClientHandler extends ChannelInboundHandlerAdapter {

    private DefaultPromise<Object> promise;
    private String serviceType;
    private String serviceMethod;
    private String requestType;
    private Object requestValue;
    private int i;

    public ClientHandler(DefaultPromise<Object> promise, String serviceType, String serviceMethod, String requestType, Object requestValue) {
        this.promise = promise;
        this.serviceType = serviceType;
        this.serviceMethod = serviceMethod;
        this.requestType = requestType;
        this.requestValue = requestValue;
        //FIXME 删掉这里的测试代码，不然可能对别的接口调用报错
        this.i = ((RpcNode.ClassRequest) requestValue).getId();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("connected" + i);
        RpcNode.RpcRequest.Builder builder = RpcNode.RpcRequest.newBuilder();
        Any any = Any.pack((Message) requestValue);
        builder.setServiceMethod(this.serviceMethod)
                .setServiceType(this.serviceType)
                .setRequestType(this.requestType)
                .setRealRequest(any);
        RpcNode.RpcRequest rpcRequest = builder.build();
        ctx.writeAndFlush(Unpooled.copiedBuffer(rpcRequest.toByteArray()));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("disconnected" + i);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        byte[] message = new byte[in.readableBytes()];
        in.getBytes(0, message);
        RpcNode.ClassResult classResult = RpcNode.ClassResult.parseFrom(message);
        promise.setSuccess(classResult);
        ctx.close();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
