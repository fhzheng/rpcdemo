package xyz.zpath.java;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import xyz.zpath.java.rpc.protobuf.RpcNode;

import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: zhengfh
 * @date: 2018/8/1
 * @time: 23:20
 */
public class main {

    public static void main(String[] args) {
//        RpcNode.Caller caller = RpcNode.Caller.newBuilder()
//                .setId(1)
//                .setMethod("getUserInfo")
//                .setParamter("1234567788").build();
//        int serializedSize = caller.getSerializedSize();
//        System.out.println(caller);
//        System.out.println(serializedSize);
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage("java.lang"))
                .setScanners(new SubTypesScanner(),
                        new TypeAnnotationsScanner())
        );
        Set<Class<?>> subTypesOf = reflections.getSubTypesOf(Object.class);
        System.out.println(subTypesOf);
    }
}
