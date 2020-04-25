package com.zj.study.framework.netty.ch2.linebase;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;

public class EchoServer {

	private int port;

	public EchoServer(int port) {
		this.port = port;
	}

	private void start() throws InterruptedException {
		final EchoServerHandler serverHandler = new EchoServerHandler();
		/* 线程组 */
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();/* 服务端启动必备 */
			b.group(group).channel(NioServerSocketChannel.class)
					/* 指明服务器监听端口 */
					.localAddress(new InetSocketAddress(port))
					/*
					 * 接收到连接请求，新启一个socket通信，也就是channel，每个channel 有自己的事件的handler
					 */
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
							ch.pipeline().addLast(serverHandler);
						}
					});
			ChannelFuture f = b.bind().sync();/* 绑定到端口，阻塞等待直到连接完成 */
			/* 阻塞，直到channel关闭 */
			f.channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully().sync();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		int port = 9999;
		EchoServer echoServer = new EchoServer(port);
		System.out.println("服务器即将启动");
		echoServer.start();
		System.out.println("服务器关闭");
	}

}
