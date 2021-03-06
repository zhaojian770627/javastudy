package com.zj.study.framework.netty.advantage.client;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zj.study.framework.netty.advantage.vo.Message;
import com.zj.study.framework.netty.advantage.vo.MessageHeader;
import com.zj.study.framework.netty.advantage.vo.MessageType;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author Mark老师 享学课堂 https://enjoy.ke.qq.com 往期课程和VIP课程咨询 依娜老师 QQ：2133576719
 *         类说明：发起登录请求
 */
public class LoginAuthReqHandler extends ChannelInboundHandlerAdapter {

	private static final Log LOG = LogFactory.getLog(LoginAuthReqHandler.class);

	/* 建立连接后，发出登录请求 */
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.writeAndFlush(buildLoginReq());
	}

	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		Message message = (Message) msg;

		// 如果是登录/业务握手应答消息，需要判断是否认证成功
		if (message.getMyHeader() != null && message.getMyHeader().getType() == MessageType.LOGIN_RESP.value()) {
			byte loginResult = (byte) message.getBody();
			if (loginResult != (byte) 0) {
				// 握手失败，关闭连接
				ctx.close();
			} else {
				LOG.info("Login is ok : " + message);
				ctx.fireChannelRead(msg);
			}
		} else
			ctx.fireChannelRead(msg);
	}

	private Message buildLoginReq() {
		Message message = new Message();
		MessageHeader myHeader = new MessageHeader();
		myHeader.setType(MessageType.LOGIN_REQ.value());
		message.setMessageHeader(myHeader);
		return message;
	}

	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.fireExceptionCaught(cause);
	}
}
