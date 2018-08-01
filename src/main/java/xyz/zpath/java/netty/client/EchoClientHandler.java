package xyz.zpath.java.netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: zhengfh
 * @date: 2018/8/1
 * @time: 21:10
 */
public class EchoClientHandler extends ChannelInboundHandlerAdapter {
    private final ByteBuf message;

    public EchoClientHandler() {
        message = Unpooled.buffer(EchoClient.SIZE);
        for (int i = 0; i < message.capacity(); i++) {
            message.writeByte((byte) i);

        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("connected");
        ctx.writeAndFlush(Unpooled.copiedBuffer("This message from client!", CharsetUtil.UTF_8));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        System.out.println("Msg from server: " + in.toString(CharsetUtil.UTF_8));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
