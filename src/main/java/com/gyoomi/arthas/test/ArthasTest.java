package com.gyoomi.arthas.test;

import org.apache.tomcat.util.threads.TaskThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.concurrent.*;

/**
 * 类功能描述
 *
 * @author Leon
 * @version 2020/3/1 17:33
 */
public class ArthasTest
{

	private static final Logger lg = LoggerFactory.getLogger(ArthasTest.class);

	private static HashSet hashSet = new HashSet();

	/**
	 * 线程池，大小 1
	 */
	private static ExecutorService executorService = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(1024), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

	public static void main(String[] args)
	{
		// 模拟 CPU 过高，这里注释掉了，测试时可以打开
		// cpu();
		// 模拟线程阻塞
		thread();
		// 模拟线程死锁
		deadThread();
		// 不断的向 hashSet 集合增加数据
		addHashSetThread();
	}

	/**
	 * 不断的向 hashSet 集合添加数据
	 */
	public static void addHashSetThread()
	{
		// 初始化常量
		new Thread(() ->
		{
			int count = 0;
			while (true)
			{
				try
				{
					hashSet.add("count" + count);
					Thread.sleep(10000);
					count++;
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}).start();
	}

	private static void cpu()
	{
		cpuNormal();
		cpuHigh();
	}


	/**
	 * 模拟线程阻塞,向已经满了的线程池提交线程
	 */
	private static void thread()
	{
		Thread thread = new Thread(() ->
		{
			while (true)
			{
				lg.debug("thread start");
				try
				{
					Thread.sleep(3000);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		});
		// 添加到线程
		executorService.submit(thread);
	}

	private static void deadThread()
	{
		// 创建资源
		Object resourceA = new Object();
		Object resourceB = new Object();
		// 创建线程
		Thread threadA = new Thread(() ->
		{
			synchronized (resourceA)
			{
				lg.info(Thread.currentThread() + " get ResourceA");
				try
				{
					Thread.sleep(1000);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				lg.info(Thread.currentThread() + "waiting get resourceB");
				synchronized (resourceB)
				{
					lg.info(Thread.currentThread() + " get resourceB");
				}
			}
		});

		Thread threadB = new Thread(() ->
		{
			synchronized (resourceB)
			{
				lg.info(Thread.currentThread() + " get ResourceB");
				try
				{
					Thread.sleep(1000);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				lg.info(Thread.currentThread() + "waiting get resourceA");
				synchronized (resourceA)
				{
					lg.info(Thread.currentThread() + " get resourceA");
				}
			}
		});
		threadA.start();
		threadB.start();
	}

	/**
	 * 极度cpu消耗
	 */
	private static void cpuHigh()
	{
		Thread thread = new Thread(() ->
		{
			while (true)
			{
				lg.info("cpu start 100%...");
			}
		});

		executorService.submit(thread);
	}

	/**
	 * 普通cpu消耗
	 */
	private static void cpuNormal()
	{
		for (int i = 0; i < 10; i++)
		{
			new Thread(() ->
			{
				while (true)
				{
					lg.info("cpu start...");

					try
					{
						Thread.sleep(3000);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
			}).start();
		}
	}


}
