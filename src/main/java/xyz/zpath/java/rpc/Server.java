/**
 * Created by zhengfh on 2018/8/2
 *
 * @author zhengfh
 **/
package xyz.zpath.java.rpc;

import io.grpc.*;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import xyz.zpath.java.rpc.annotation.RpcService;
import xyz.zpath.java.rpc.protobuf.MessageServiceGrpc;
import xyz.zpath.java.rpc.services.MessageServiceImpl;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Set;

/**
 * @author zhengfh
 * @date 2018/8/2
 **/
public class Server {
    private static int port = 8879;
    private static final HashMap<String, Object> SERVICES = new HashMap<String, Object>();

    public static void main(String... args) throws IOException, InterruptedException, IllegalAccessException, InstantiationException {
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }
        ServerBuilder serverBuilder = ServerBuilder.forPort(port);
        Reflections reflections = new Reflections(
                new ConfigurationBuilder()
                        .setUrls(ClasspathHelper.forPackage("xyz.zpath.java.rpc.service"))
                        .setScanners(new SubTypesScanner(),
                                new TypeAnnotationsScanner())
                        .filterInputsBy(new FilterBuilder().includePackage("xyz.zpath.java.rpc.service"))
        );
        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(RpcService.class);
        for (Class clazz : typesAnnotatedWith) {
            System.out.println(clazz.getSimpleName());
            SERVICES.put(((RpcService)clazz.getAnnotation(RpcService.class)).value().getName(), clazz);
            serverBuilder.addService((BindableService) clazz.newInstance());
        }
        System.out.println(SERVICES);
        io.grpc.Server server = serverBuilder.build();
        server.start();
        System.out.println("Server Started at " + port);
        server.awaitTermination();
    }
}
