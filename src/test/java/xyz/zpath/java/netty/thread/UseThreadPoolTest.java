package xyz.zpath.java.netty.thread;


/**
 * @author zhengfh
 * @date 2018/8/1
 **/
public class UseThreadPoolTest {
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            String name = "task" + i;
            UseThreadPool.getPool().execute(name);
        }
    }

}
