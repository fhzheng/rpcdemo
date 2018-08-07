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
    /**
     * 服务接口名称
     *
     * @return
     */
    Class<?> value();

    /**
     * 命名空间，默认根目录
     * @return
     */
    String namespace();

    /**
     * 服务版本
     *
     * @return
     */
    String version() default "";
}
