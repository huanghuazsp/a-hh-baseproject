package com.hh.message.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.hh.hibernate.util.base.BaseTwoEntity;

@Entity
@Table(name = "SYS_SEND_EMAIL")
public class SysSendEmail extends BaseTwoEntity implements java.io.Serializable {
	private String users;
	private String type;
	private String content;
	private String title;
	private String files;

	private enum Type {
		yfs, cgx
	}

	
	@Column(name = "TYPE_", length = 16)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

}
