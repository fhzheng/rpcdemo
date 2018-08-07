package xyz.zpath.java.netty.client.service;

import xyz.zpath.java.rpc.protobuf.RpcNode;

/**
 * @author zhengfh
 * @date 2018/8/7
 **/
public interface ClassService {
    /**
     * 获取班级
     * @param request
     * @return
     */
    RpcNode.ClassResult getclass(RpcNode.ClassRequest request);

    /**
     * 获取
     * @param request
     * @return
     */
    RpcNode.ClassResult deleteClass(RpcNode.ClassRequest request);
}
