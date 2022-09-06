package com.nc.core.common.utils;

import com.nc.core.common.props.OkHttpConfigurationProperties;
import com.nc.core.common.props.ThreadPoolProperties;

import java.util.concurrent.*;

/**
 * @description:
 * @author: jjxu
 * @time: 2021/7/6
 * @package: com.sino.common.utils
 */
public class ThreadPoolUtil {

	private ThreadPoolUtil() {
	}

	private static volatile ThreadPoolExecutor threadPool;

	/**
	 * dcs获取线程池
	 * @return 线程池对象
	 */
	public static ThreadPoolExecutor getThreadPool() {
		if (threadPool != null) {
			return threadPool;
		}
		synchronized (ThreadPoolUtil.class) {
			if (threadPool == null) {
				ThreadPoolProperties prop = SpringApplicationUtil.getBean(ThreadPoolProperties.class);
				if(prop ==null){
					prop = new ThreadPoolProperties();
					prop.setCapacity(16);
					prop.setCorePoolSize(8);
					prop.setKeepAliveTime(30L);
					prop.setMaximumPoolSize(20);
				}
					threadPool = new ThreadPoolExecutor(prop.getCorePoolSize(), prop.getMaximumPoolSize(), prop.getKeepAliveTime(), TimeUnit.SECONDS,
						new LinkedBlockingQueue<>(prop.getCapacity()), new ThreadPoolExecutor.CallerRunsPolicy());
				/*threadPool = new ThreadPoolExecutor(ThreadPoolProperties.corePoolSize, ThreadPoolProperties.maximumPoolSize, ThreadPoolProperties.keepAliveTime, TimeUnit.SECONDS,
						new LinkedBlockingQueue<>(ThreadPoolProperties.capacity), new ThreadPoolExecutor.CallerRunsPolicy());*/
				/*threadPool = new ThreadPoolExecutor(16, 8, 60, TimeUnit.SECONDS,
						new LinkedBlockingQueue<>(64), new ThreadPoolExecutor.CallerRunsPolicy());*/
			}
			return threadPool;
		}
	}

	/**
	 * 无返回值直接执行
	 * @param runnable
	 */
	public  static void execute(Runnable runnable){
		getThreadPool().execute(runnable);
	}

	/**
	 * 返回值直接执行
	 * @param callable
	 */
	public  static <T> Future<T> submit(Callable<T> callable){
		return   getThreadPool().submit(callable);
	}

	/**
	 * 关闭线程池
	 */
	public static void shutdown() {
		getThreadPool().shutdown();
	}

}
