package xyz.zpath.java.netty.server;

import com.google.protobuf.*;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.springframework.util.StringUtils;
import sun.security.validator.ValidatorException;
import xyz.zpath.java.rpc.annotation.RpcService;
import xyz.zpath.java.rpc.protobuf.RpcNode;

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
@ChannelHandler.Sharable
public class RpcServerHandler extends ChannelInboundHandlerAdapter {
    private static HashMap<String, Class> register = null;
    private static HashMap<String, Class> simpleRegister = null;
    private static HashMap<Class, Object> cachedServices = new HashMap<Class, Object>();

    public RpcServerHandler() {
        if (register == null || simpleRegister == null) {
            register = new HashMap<String, Class>();
            simpleRegister = new HashMap<String, Class>();
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
                simpleRegister.put(((RpcService) clazz.getAnnotation(RpcService.class)).namespace(), clazz);
            }
        }
        System.out.println(register);
    }

    /**
     * 使用缓存的方式实例化服务类
     *
     * @param clazz 类名
     * @return 实例化的类
     */
    private Object getServiceObj(Class clazz) {
        Object object = cachedServices.get(clazz);
        if (object == null) {
            try {
                object = clazz.newInstance();
                cachedServices.put(clazz, object);
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
        String namespace = rpcRequest.getNamespace();
        String serviceMethod = rpcRequest.getServiceMethod();
        String requestType = rpcRequest.getRequestType();
        Any realRequest = rpcRequest.getRealRequest();

        if (StringUtils.isEmpty(serviceType) && StringUtils.isEmpty(namespace)) {
            throw new ValidatorException("not valid");
        }

        Class serviceTypeObj;

        if (StringUtils.isEmpty(namespace) && StringUtils.isEmpty(serviceType)) {
            throw new ValidatorException("namespace or class must not be empty!");
        }

        if (!StringUtils.isEmpty(namespace)) {
            serviceTypeObj = simpleRegister.get(namespace);
        } else {
            serviceTypeObj = register.get(serviceType);
        }

        if (serviceTypeObj == null) {
            throw new ClassNotFoundException(String.format("Namespace:%s, Class:%s", namespace, serviceType));
        }

        Class requestObj = Class.forName(requestType);
        if (requestObj == null) {
            throw new ClassNotFoundException(requestType + " not found!");
        }
        Message unpackedMessage = realRequest.unpack(requestObj);
        if (unpackedMessage == null) {
            throw new ClassCastException(requestType);
        }

        @SuppressWarnings("unchecked")
        Method serviceMethodObj = serviceTypeObj.getMethod(serviceMethod, RpcController.class, Class.forName(requestType), RpcCallback.class);
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
