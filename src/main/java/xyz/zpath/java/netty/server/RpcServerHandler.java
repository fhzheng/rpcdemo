package xyz.zpath.java.netty.server;

import com.google.protobuf.*;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.buffer.UnpooledUnsafeDirectByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import xyz.zpath.java.rpc.annotation.RpcService;
import xyz.zpath.java.rpc.protobuf.RpcNode;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: zhengfh
 * @date: 2018/8/1
 * @time: 20:55
 */
public class RpcServerHandler extends ChannelInboundHandlerAdapter {
    private static HashMap<String, Class> register = null;
    private static HashMap<Class, Object> objRegister = new HashMap<Class, Object>();

    public RpcServerHandler() {
        if (register == null) {
            register = new HashMap<String, Class>();
            Reflections reflections = new Reflections(
                    new ConfigurationBuilder()
                            .setUrls(ClasspathHelper.forPackage("xyz.zpath.java.rpc.services"))
                            .setScanners(new SubTypesScanner(),
                                    new TypeAnnotationsScanner())
                            .filterInputsBy(new FilterBuilder().includePackage("xyz.zpath.java.rpc.services"))
            );
            Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(RpcService.class);
            for (Class clazz : typesAnnotatedWith) {
                System.out.println(clazz.getSimpleName());
                register.put(((RpcService) clazz.getAnnotation(RpcService.class)).value().getName(), clazz);
            }
        }
        System.out.println(register);
    }

    private Object getServiceObj(Class clazz) {
        Object object = objRegister.get(clazz);
        if (object == null) {
            try {
                object = clazz.newInstance();
                objRegister.put(clazz, object);
            } catch (InstantiationException e) {
                e.printStackTrace();
                return null;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return null;
            }
        }
        return object;
    }

    @Override
    public void channelRead(final ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("get from client");
        ByteBuf in = (ByteBuf) msg;
        byte[] message = new byte[in.readableBytes()];
        in.getBytes(0, message);
        RpcNode.RpcRequest rpcRequest = RpcNode.RpcRequest.parseFrom(message);
        System.out.println(rpcRequest.toString());
        String serviceType = rpcRequest.getServiceType();
        String serviceMethod = rpcRequest.getServiceMethod();
        String requestType = rpcRequest.getRequestType();
        Any realRequest = rpcRequest.getRealRequest();
        Class serviceTypeObj = register.get(serviceType);
        Class requestObj = Class.forName(requestType);
        Message unpackedMessage = realRequest.unpack(requestObj);

        Method serviceMethodObj = serviceTypeObj.getDeclaredMethod(serviceMethod, RpcController.class, Class.forName(requestType), RpcCallback.class);
        serviceMethodObj.invoke(getServiceObj(serviceTypeObj), new RpcControllerImpl(), unpackedMessage, new RpcCallback<Object>() {
            @Override
            public void run(Object parameter) {
                ctx.writeAndFlush(Unpooled.copiedBuffer(
                        ((AbstractMessageLite) parameter.getClass().cast(parameter))
                                .toByteArray()));
            }
        });

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

}
