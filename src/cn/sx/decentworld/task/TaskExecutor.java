/**
 * 
 */
package cn.sx.decentworld.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName: TaskExecutor.java
 * @Description:
 * @author: cj
 * @date: 2015年11月28日 下午2:04:08
 */
public class TaskExecutor
{
    /**
     * 线程池中只有一个线程
     */
    private static ExecutorService PacketListenerExecutorService = Executors.newFixedThreadPool(1);

    public static void Execute(Runnable runnable)
    {
        PacketListenerExecutorService.execute(runnable);
    }
}
