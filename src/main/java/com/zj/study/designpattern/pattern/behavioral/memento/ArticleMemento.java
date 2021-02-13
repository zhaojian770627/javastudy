package com.zj.study.designpattern.pattern.behavioral.memento;

public class ArticleMemento {
	private String title;
	private String content;
	private String imgs;

	public ArticleMemento(String title, String content, String imgs) {
		super();
		this.title = title;
		this.content = content;
		this.imgs = imgs;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public String getImgs() {
		return imgs;
	}
}
