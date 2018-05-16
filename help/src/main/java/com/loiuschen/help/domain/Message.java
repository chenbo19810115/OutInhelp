package com.loiuschen.help.domain;

public class Message{
	String title;
	String time;
	String content;
	String tixianid;
	public String getTixianid() {
		return tixianid;
	}
	public void setTixianid(String tixianid) {
		this.tixianid = tixianid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}