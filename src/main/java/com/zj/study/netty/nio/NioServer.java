package com.zj.study.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class NioServer {

	private static Map<String, SocketChannel> clientMap = new HashMap<>();

	public static void main(String[] args) throws IOException {
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.configureBlocking(false);
		ServerSocket serverSocket = serverSocketChannel.socket();
		serverSocket.bind(new InetSocketAddress(8899));

		Selector selector = Selector.open();
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

		while (true) {
			try {
				selector.select();
				Set<SelectionKey> selectionKeys = selector.selectedKeys();
				for (SelectionKey selectionKey : selectionKeys) {
					final SocketChannel client;
					if (selectionKey.isAcceptable()) {
						ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
						client = server.accept();
						client.configureBlocking(false);
						SelectionKey sk = client.register(selector, SelectionKey.OP_READ);
						String key = "[" + UUID.randomUUID().toString() + "]";
						sk.attach(key);
						clientMap.put(key, client);
					} else if (selectionKey.isReadable()) {
						client = (SocketChannel) selectionKey.channel();

						String clientKey = selectionKey.attachment() != null ? selectionKey.attachment().toString()
								: "Unknow Client";

						ByteBuffer readBuffer = ByteBuffer.allocate(1024);
						int count = client.read(readBuffer);
						if (count > 0) {
							readBuffer.flip();
							Charset charset = Charset.forName("utf-8");
							String receivedMessage = String.valueOf(charset.decode(readBuffer).array());
							System.out.println(client + ":" + receivedMessage);

							for (SocketChannel channel : clientMap.values()) {
								ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
								writeBuffer.put((clientKey + ":" + receivedMessage).getBytes());
								writeBuffer.flip();
								channel.write(writeBuffer);
							}
						}

					}
				}
				selectionKeys.clear();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

}
