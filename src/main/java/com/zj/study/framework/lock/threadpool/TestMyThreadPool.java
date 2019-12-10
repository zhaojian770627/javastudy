package com.zj.study.framework.lock.threadpool;

import com.zj.study.framework.lock.Tools;

public class TestMyThreadPool {
	public static void main(String[] args) throws InterruptedException {
		// 创建3个线程的线程池
		MyThreadPool t = new MyThreadPool(3, 0);
		t.execute(new MyTask("testA"));
		t.execute(new MyTask("testB"));
		t.execute(new MyTask("testC"));
		t.execute(new MyTask("testD"));
		t.execute(new MyTask("testE"));
		System.out.println(t);
		Thread.sleep(10000);
		t.destroy();// 所有线程都执行完成才destory
		System.out.println(t);
	}

	// 任务类
	static class MyTask implements Runnable {

		private String name;

		public MyTask(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		@Override
		public void run() {// 执行任务
			try {
				Thread.sleep(Tools.getRandomInt(1000) + 2000);
			} catch (InterruptedException e) {
				System.out.println(Thread.currentThread().getId() + " sleep InterruptedException:"
						+ Thread.currentThread().isInterrupted());
			}
			System.out.println("任务 " + name + " 完成");
		}
	}
}
