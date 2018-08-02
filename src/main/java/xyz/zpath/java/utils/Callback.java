/**
 * Created by zhengfh on 2018/8/2
 *
 * @author zhengfh
 **/
package xyz.zpath.java.utils;

/**
 * @author zhengfh
 * @date 2018/8/2
 **/
public interface Callback {
    /**
     * @param result
     */
    void success(Object result);

    /**
     * @param throwable
     */
    void error(Throwable throwable);

    /**
     *
     */
    void completed();
}
