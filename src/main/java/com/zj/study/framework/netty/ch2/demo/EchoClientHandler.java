package com.zj.study.framework.netty.ch2.demo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
		System.out.println("Client accetp:" + msg.toString(CharsetUtil.UTF_8));
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// 往服务器写数据
		ctx.writeAndFlush(Unpooled.copiedBuffer("Hello,Netty", CharsetUtil.UTF_8));
		ByteBuf msg = null;
		String request = "zj" + System.getProperty("line.separator");

		for (int i = 0; i < 100; i++) {
			msg = Unpooled.buffer(request.length());
			msg.writeBytes(request.getBytes());
			ctx.writeAndFlush(msg);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		super.exceptionCaught(ctx, cause);
		ctx.close();
	}

}
