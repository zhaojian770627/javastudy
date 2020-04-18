package com.zj.study.framework.netty.ch1.aio.server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class AioWriteHandler implements CompletionHandler<Integer, ByteBuffer> {
	private AsynchronousSocketChannel channel;

	public AioWriteHandler(AsynchronousSocketChannel channel) {
		this.channel = channel;
	}

	@Override
	public void completed(Integer result, ByteBuffer attachment) {
		if (attachment.hasRemaining()) {
			channel.write(attachment, attachment, this);
		} else {
			// 读取客户端传回的数据
			ByteBuffer readBuffer = ByteBuffer.allocate(1024);
			// 异步读数据
			channel.read(readBuffer, readBuffer, new AioReadHandler(channel));
		}
	}

	@Override
	public void failed(Throwable exc, ByteBuffer attachment) {
		try {
			channel.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
