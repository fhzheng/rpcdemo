/**
 * Created by zhengfh on 2018/8/2
 *
 * @author zhengfh
 **/
package xyz.zpath.java.rpc.annotation;

import java.lang.annotation.*;

/**
 * @author zhengfh
 * @date 2018/8/2
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RpcService {
    String name();
}
