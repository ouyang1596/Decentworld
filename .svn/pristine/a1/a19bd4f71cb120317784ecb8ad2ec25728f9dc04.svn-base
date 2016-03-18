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
public class TaskExecutor {
	private static ExecutorService PacketListenerExecutorService = Executors.newCachedThreadPool();
	public static void Execute(Runnable runnable){
		PacketListenerExecutorService.execute(runnable);
	}
}
