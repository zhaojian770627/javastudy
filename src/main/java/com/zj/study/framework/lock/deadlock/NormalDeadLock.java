package com.zj.study.framework.lock.deadlock;

import com.zj.study.framework.lock.SleepTools;

public class NormalDeadLock {
	private static Object valueFirst = new Object();// 第一个锁
	private static Object valueSecond = new Object();// 第二个锁

	// 先拿第一个锁，再拿第二个锁
	private static void fisrtToSecond() throws InterruptedException {
		String threadName = Thread.currentThread().getName();
		synchronized (valueFirst) {
			System.out.println(threadName + " get first");
			SleepTools.ms(100);
			synchronized (valueSecond) {
				System.out.println(threadName + " get second");
			}
		}
	}

	// 先拿第二个锁，再拿第一个锁
	private static void SecondToFisrt() throws InterruptedException {
		String threadName = Thread.currentThread().getName();
		synchronized (valueSecond) {
			System.out.println(threadName + " get second");
			SleepTools.ms(100);
			synchronized (valueFirst) {
				System.out.println(threadName + " get first");
			}
		}
	}

	// 执行先拿第二个锁，再拿第一个锁
	private static class TestThread extends Thread {

		private String name;

		public TestThread(String name) {
			this.name = name;
		}

		public void run() {
			Thread.currentThread().setName(name);
			try {
				SecondToFisrt();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		Thread.currentThread().setName("TestDeadLock");
		TestThread testThread = new TestThread("SubTestThread");
		testThread.start();
		try {
			fisrtToSecond();// 先拿第一个锁，再拿第二个锁
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}