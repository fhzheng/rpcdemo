/**
 * Created by zhengfh on 2018/8/7
 *
 * @author zhengfh
 **/
package xyz.zpath.java.netty.client.service.impl;

import io.netty.util.concurrent.DefaultPromise;
import xyz.zpath.java.netty.client.ClientFactory;
import xyz.zpath.java.netty.client.service.ClassService;
import xyz.zpath.java.rpc.protobuf.RpcNode;

import java.util.concurrent.ExecutionException;

/**
 * @author zhengfh
 * @date 2018/8/7
 **/
public class ClassServiceImpl implements ClassService {

    @Override
    public RpcNode.ClassResult getclass(RpcNode.ClassRequest request) {
        ClientFactory factory = ClientFactory.getFactory();
        //TODO 使用代码模板生成代码
        DefaultPromise<Object> promise = factory.newPromise(RpcNode.ClassService.class.getName(),
                "getClass",
                RpcNode.ClassRequest.class.getName(),
                request);
        try {
            return (RpcNode.ClassResult) promise.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public RpcNode.ClassResult deleteClass(RpcNode.ClassRequest request) {
        ClientFactory factory = ClientFactory.getFactory();
        DefaultPromise<Object> promise = factory.newPromise(RpcNode.ClassService.class.getName(),
                "deleteClass",
                RpcNode.ClassRequest.class.getName(),
                request);
        try {
            return (RpcNode.ClassResult) promise.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }
}
