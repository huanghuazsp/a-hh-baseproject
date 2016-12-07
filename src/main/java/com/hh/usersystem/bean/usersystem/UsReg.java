package com.hh.usersystem.bean.usersystem;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.hh.hibernate.util.base.BaseEntitySimple;

@Entity
@Table(name = "US_REG")
public class UsReg extends BaseEntitySimple {
	private String email;
	private String code;

	@Column(name="EMAIL_")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	@Column(name="CODE_")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}