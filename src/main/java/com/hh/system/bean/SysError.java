package com.hh.system.bean;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.hh.hibernate.dao.inf.Order;
import com.hh.hibernate.util.base.BaseEntity;

@Entity
@Order
@Table(name = "SYS_ERROR")
public class SysError extends BaseEntity {
	private String name;
	private String message;
	private String allMessage;
	

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

}
