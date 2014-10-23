package com.hh.system.bean;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.hh.hibernate.dao.inf.Order;
import com.hh.hibernate.util.base.BaseTwoEntity;

@Entity
@Order
@Table(name = "HH_XT_ERROR")
public class HHXtError extends BaseTwoEntity {
	private String name;
	private String message;
	private String allMessage;
	
	private String createUserName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Lob
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Lob
	public String getAllMessage() {
		return allMessage;
	}

	public void setAllMessage(String allMessage) {
		this.allMessage = allMessage;
	}

	@Transient
	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	
	

}
