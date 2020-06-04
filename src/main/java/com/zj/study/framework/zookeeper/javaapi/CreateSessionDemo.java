package com.zj.study.framework.zookeeper.javaapi;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class CreateSessionDemo {

	private final static String CONNECTSTRING = "10.6.255.181:2181";
	private static CountDownLatch countDownLatch = new CountDownLatch(1);

	public static void main(String[] args) throws Exception {
		ZooKeeper zooKeeper = new ZooKeeper(CONNECTSTRING, 5000, new Watcher() {
			public void process(WatchedEvent watchedEvent) {
				// 如果当前的连接状态是连接成功的，那么通过计数器去控制
				if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
					countDownLatch.countDown();
					System.out.println(watchedEvent.getState());
				}
			}
		});
		countDownLatch.await();
		System.out.println(zooKeeper.getState());
	}

}
