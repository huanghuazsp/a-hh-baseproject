 package com.hh.system.bean;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Lob;
import com.hh.hibernate.util.base.*;
import com.hh.hibernate.dao.inf.Order;
@Order
@SuppressWarnings("serial")
@Entity
@Table(name="SYS_OPER_LOG")
public class SystemOperLog  extends BaseTwoEntity{
	//userName
	private String userName;
	
	@Column(name="USER_NAME", length = 64)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	//userId
	private String userId;
	
	@Column(name="USER_ID", length = 36)
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	//message
	private String message;
	
	@Column(name="MESSAGE", length = 2000)
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}