/**
 * Created by zhengfh on 2018/8/1
 *
 * @author zhengfh
 **/
package xyz.zpath.java.netty.thread;

/**
 * @author zhengfh
 * @date 2018/8/1
 **/
public class UseThreadPool {
    private ThreadPool threadPool;
    private static UseThreadPool pool = new UseThreadPool();

    private UseThreadPool() {
        int workQueueSize = 80;
        int coreQueueSize = 5;
        int maxSize = 10;

        threadPool = new ThreadPool(workQueueSize, coreQueueSize, maxSize);
    }

    public static UseThreadPool getPool() {
        return pool;
    }

    public void execute(Runnable task) {
        threadPool.execute(task);
    }

    public void execute(String name) {
        execute(new ThreadRunnable(name));
    }
}
