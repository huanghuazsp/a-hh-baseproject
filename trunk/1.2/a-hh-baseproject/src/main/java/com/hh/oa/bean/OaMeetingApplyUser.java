 package com.hh.oa.bean;
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
@Table(name="OA_MEETING_APPLY_USER")
public class OaMeetingApplyUser  extends BaseTwoEntity{
	//主ID
	private String objectId;
	
	@Column(name="OBJECT_ID", length = 36)
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
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
	
	//userName
	private String userName;
	
	@Column(name="USER_NAME", length = 64)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	//read
	private int read;
	
	@Column(name="READ_")
	public int getRead() {
		return read;
	}
	public void setRead(int read) {
		this.read = read;
	}
	
}