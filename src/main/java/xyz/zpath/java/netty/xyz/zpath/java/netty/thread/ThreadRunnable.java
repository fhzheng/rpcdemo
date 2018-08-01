/**
 * Created by zhengfh on 2018/8/1
 *
 * @author zhengfh
 **/
package xyz.zpath.java.netty.xyz.zpath.java.netty.thread;

/**
 * @author zhengfh
 * @date 2018/8/1
 **/
public class ThreadRunnable implements Runnable {
    String name;
    public ThreadRunnable(String name){
        this.name = name;
    }
    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    public void run() {
        System.out.println("start..." + name);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
