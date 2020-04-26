package com.zj.study.framework.netty.http.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.util.CharsetUtil;

public class HttpClientInboundHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// 开始对服务器的响应做处理
		FullHttpResponse httpResponse = (FullHttpResponse) msg;
		System.out.println(httpResponse.headers());
		ByteBuf content = httpResponse.content();
		System.out.println(content.toString(CharsetUtil.UTF_8));
		content.release();
	}

}
