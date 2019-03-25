package com.abc.userservice.userservice.beans;

import java.io.Serializable;
import java.sql.Timestamp;




public class Notification{

	private int id;
	

	private int receiverId;
	////////////RECEVIERID
	
	private String content;
	
	private Timestamp time;

	public Notification(int receiverId, String content, Timestamp time) {
		super();
		this.receiverId = receiverId;
		this.content = content;
		this.time = time;
	}

	public Notification() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(int receiverId) {
		this.receiverId = receiverId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "Notification [id=" + id + ", receiverId=" + receiverId + ", content=" + content + ", time=" + time
				+ "]";
	}
	 
}

