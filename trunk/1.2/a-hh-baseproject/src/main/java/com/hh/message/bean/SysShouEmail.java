package com.hh.message.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.hh.hibernate.util.base.BaseTwoEntity;

@Entity
@Table(name = "SYS_SHOU_EMAIL")
public class SysShouEmail extends BaseTwoEntity implements java.io.Serializable {
	private String users;
	private String type;
	private String content;
	private String title;
	private String files;
	private String sendUser;
	private String shouUser;

	private String userNames;
	private String sendUserName;

	private int read = 0;

	public static enum Type {
	}

	@Column(name = "TYPE_", length = 16)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "READ_", precision = 1, scale = 0)
	public int getRead() {
		return read;
	}

	public void setRead(int read) {
		this.read = read;
	}

	@Lob
	public String getUsers() {
		return users;
	}

	public void setUsers(String users) {
		this.users = users;
	}

	@Lob
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(length = 128)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Lob
	public String getFiles() {
		return files;
	}

	public void setFiles(String files) {
		this.files = files;
	}

	@Column(length = 36)
	public String getSendUser() {
		return sendUser;
	}

	public void setSendUser(String sendUser) {
		this.sendUser = sendUser;
	}

	@Column(length = 36)
	public String getShouUser() {
		return shouUser;
	}

	public void setShouUser(String shouUser) {
		this.shouUser = shouUser;
	}

	@Transient
	public String getUserNames() {
		return userNames;
	}

	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}

	@Transient
	public String getSendUserName() {
		return sendUserName;
	}

	public void setSendUserName(String sendUserName) {
		this.sendUserName = sendUserName;
	}

}
