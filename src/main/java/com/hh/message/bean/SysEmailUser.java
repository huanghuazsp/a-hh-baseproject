package com.hh.message.bean;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.Table;

import com.hh.hibernate.util.base.BaseOneEntity;

@Entity(name = "SYS_EMAIL_USER")
@Table(indexes = { @Index(name = "SYS_EMAIL_USER_INDEX_USER_ID_EMAIL_ID", columnNames = { "USER_ID", "EMAIL_ID" }) }, appliesTo = "SYS_EMAIL_USER")
public class SysEmailUser extends BaseOneEntity {

	private String emailId;
	private String userId;
	private String userName;
	private int read;
	private int type;

	@Column(name = "USER_ID", length = 36)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "USER_NAME", length = 128)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "READ_")
	public int getRead() {
		return read;
	}

	public void setRead(int read) {
		this.read = read;
	}

	@Column(name = "EMAIL_ID", length = 36)
	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	@Index(name = "SYS_EMAIL_USER_INDEX_TYPE_")
	@Column(name = "TYPE_")
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
