package com.zj.study.framework.netty.ch2.demo;

import java.util.concurrent.atomic.AtomicInteger;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/*
 * 指明这个handler可以在多个channel之间共享，意味这个实现必须线程安全的。
 * */
@ChannelHandler.Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

	private AtomicInteger counter = new AtomicInteger(0);

	/*** 服务端读取到网络数据后的处理 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf in = (ByteBuf) msg;/* netty实现的缓冲区 */
		String request = in.toString(CharsetUtil.UTF_8);
		System.out.println("Server Accept:" + request + " the counter is " + counter.incrementAndGet());
		String resp = "Hello," + request + ",welcome" + System.getProperty("line.separator");
		ctx.write(Unpooled.copiedBuffer(resp, CharsetUtil.UTF_8));
	}

	/*** 服务端读取完成网络数据后的处理 */
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)/* flush掉所有的数据 */
				.addListener(ChannelFutureListener.CLOSE);/* 当flush完成后，关闭连接 */
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		super.exceptionCaught(ctx, cause);
		ctx.close();
	}

}
