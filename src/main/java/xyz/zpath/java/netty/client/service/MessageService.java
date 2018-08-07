/**
 * Created by zhengfh on 2018/8/7
 *
 * @author zhengfh
 **/
package xyz.zpath.java.netty.client.service;

import xyz.zpath.java.rpc.protobuf.RpcNode;

/**
 * @author zhengfh
 * @date 2018/8/7
 **/
public interface MessageService {
    /**
     * 获取消息
     * @param request
     * @return
     */
    RpcNode.MessageResult getMessage(RpcNode.MessageRequest request);
}
