package com.hh.message.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Index;

import com.hh.hibernate.util.base.BaseTwoEntity;

@Entity
@Table(name = "SYS_EMAIL")
public class SysEmail extends BaseTwoEntity {
	
	private String type;
	private String content;
	private String title;
	
	private String users="";
	private String userNames="";
	private String sendUserId="";
	private String sendUserName="";
	
	private int read=0;
	
	private int meDelete =0;

	private enum Type {
		yfs, cgx
	}

	@Index(name="SYS_EMAIL_INDEX_TYPE_")
	@Column(name = "TYPE_", length = 16)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Transient
	public String getUsers() {
		return users;
	}

	public void setUsers(String users) {
		this.users = users;
	}

	@Lob
	@Column(name="CONTENT")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(length = 128,name="TITLE")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Lob
	@Column(name="USER_NAMES")
	public String getUserNames() {
		return userNames;
	}

	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}

	
	@Index(name="SYS_EMAIL_INDEX_SEND_USERID")
	@Column(name="SEND_USERID",length=36)
	public String getSendUserId() {
		return sendUserId;
	}

	public void setSendUserId(String sendUserId) {
		this.sendUserId = sendUserId;
	}

	@Column(name="SEND_USERNAME",length=128)
	public String getSendUserName() {
		return sendUserName;
	}

	public void setSendUserName(String sendUserName) {
		this.sendUserName = sendUserName;
	}

	@Transient
	public int getRead() {
		return read;
	}

	public void setRead(int read) {
		this.read = read;
	}

	@Column(name="ME_DELETE")
	public int getMeDelete() {
		return meDelete;
	}

	public void setMeDelete(int meDelete) {
		this.meDelete = meDelete;
	}
	
	
}
