package com.hh.message.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.hh.hibernate.dao.inf.Order;
import com.hh.hibernate.util.base.BaseTwoEntity;

@SuppressWarnings("serial")
@Entity
@Table(name = "SYS_MESSAGE")
@Order
public class SysMessage extends BaseTwoEntity {
	private String type;
	private String content;
	private String title;
	private String files;
	
	private String users="";
	private String userNames="";
	private String sendUserId="";
	private String sendUserName="";
	private String readUserId="";
	private String deleteUserId="";
	
	
	
	private int read=0;

	private enum Type {
		message
	}

	
	@Column(name = "TYPE_", length = 16)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Lob
	@Column(name="USERS")
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
	@Column(name="FILES")
	public String getFiles() {
		return files;
	}

	public void setFiles(String files) {
		this.files = files;
	}

	@Lob
	@Column(name="USER_NAMES")
	public String getUserNames() {
		return userNames;
	}

	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}

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

	@Lob
	@Column(name="READ_USERID")
	public String getReadUserId() {
		return readUserId;
	}

	public void setReadUserId(String readUserId) {
		this.readUserId = readUserId;
	}

	@Lob
	@Column(name="DELETE_USERID")
	public String getDeleteUserId() {
		return deleteUserId;
	}

	public void setDeleteUserId(String deleteUserId) {
		this.deleteUserId = deleteUserId;
	}


	@Transient
	public int getRead() {
		return read;
	}

	public void setRead(int read) {
		this.read = read;
	}

	
}
