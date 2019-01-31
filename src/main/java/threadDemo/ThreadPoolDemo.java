package threadDemo;

import java.util.concurrent.*;

/**
 * 创建线程池
 *
 * @author bin.yu
 * @create 2018-09-13 20:36
 **/
public class ThreadPoolDemo {

    public static void main(String[] args) {
        int corePoolSize = 5;
        int maximumPoolSize = 20;
        long keepAliveTime = 10;
        TimeUnit unit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(512);
//        ThreadFactory threadFactory
        RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(corePoolSize,maximumPoolSize,keepAliveTime,unit,
                workQueue,handler);

    }
}
