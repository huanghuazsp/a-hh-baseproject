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
	private String params;
	private String path;
	private String title;
	private String jsCode;
	private String shouUser;
	private String createUserName;
	private String shouUserName;
	private int isRead;
	@Lob
	@Column(name = "JS_CODE")
	public String getJsCode() {
		return jsCode;
	}

	public void setJsCode(String jsCode) {
		this.jsCode = jsCode;
	}

	@Lob
	@Column(name = "PARAMS")
	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	@Column(name = "PATH", length = 256)
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Column(name = "TITLE", length = 256)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	@Column(name = "SHOUUSER", length = 36)
	public String getShouUser() {
		return shouUser;
	}

	public void setShouUser(String shouUser) {
		this.shouUser = shouUser;
	}

	public int getIsRead() {
		return isRead;
	}

	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}

	
	@Column(name = "CREATEUSER_NAME", length = 64)
	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	@Transient
	public String getShouUserName() {
		return shouUserName;
	}

	public void setShouUserName(String shouUserName) {
		this.shouUserName = shouUserName;
	}

	
}
