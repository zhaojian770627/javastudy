package com.zj.study.framework.netty.advantage.vo;

/**
 * @author Mark老师 享学课堂 https://enjoy.ke.qq.com 往期课程和VIP课程咨询 依娜老师 QQ：2133576719
 *         类说明：消息实体类
 */
public final class Message {

	private MessageHeader myHeader;

	private Object body;

	public final MessageHeader getMyHeader() {
		return myHeader;
	}

	public final void setMessageHeader(MessageHeader myHeader) {
		this.myHeader = myHeader;
	}

	public final Object getBody() {
		return body;
	}

	public final void setBody(Object body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "MyMessage [myHeader=" + myHeader + "][body=" + body + "]";
	}
}
