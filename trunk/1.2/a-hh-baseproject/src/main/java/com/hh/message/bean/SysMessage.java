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
	private String content;
	
	private String sendHeadpic = "";
	private String sendUserId = "";
	private String sendUserName = "";


	private String readUserId = "";
	private String deleteUserId = "";

	private int read = 0;
	
	
	private String toObjectId = "";
	private String toObjectName = "";
	private String toObjectHeadpic = "";
	
	
//	private String toUserId = "";
//	private String toUserName = "";
//	private String toHeadpic = "";
	private int sendObjectType = 0;
	
	private enum Type {
		message
	}

	@Lob
	@Column(name = "CONTENT")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Lob
	@Column(name = "READ_USERID")
	public String getReadUserId() {
		return readUserId;
	}

	public void setReadUserId(String readUserId) {
		this.readUserId = readUserId;
	}

	@Lob
	@Column(name = "DELETE_USERID")
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
	
	@Column(name="SEND_OBJECT_TYPE")
	public int getSendObjectType() {
		return sendObjectType;
	}

	public void setSendObjectType(int sendObjectType) {
		this.sendObjectType = sendObjectType;
	}

	
	@Column(name="SEND_HEADPIC")
	public String getSendHeadpic() {
		return sendHeadpic;
	}

	public void setSendHeadpic(String sendHeadpic) {
		this.sendHeadpic = sendHeadpic;
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

	@Column(name="TO_OBJECTID",length=36)
	public String getToObjectId() {
		return toObjectId;
	}

	public void setToObjectId(String toObjectId) {
		this.toObjectId = toObjectId;
	}

	@Column(name="TO_OBJECTNAME",length=128)
	public String getToObjectName() {
		return toObjectName;
	}

	public void setToObjectName(String toObjectName) {
		this.toObjectName = toObjectName;
	}

	@Column(name="TO_OBJECT_HEADPIC")
	public String getToObjectHeadpic() {
		return toObjectHeadpic;
	}

	public void setToObjectHeadpic(String toObjectHeadpic) {
		this.toObjectHeadpic = toObjectHeadpic;
	}

//	@Column(name="TO_OBJECTID",length=36)
//	public String getToUserId() {
//		return toUserId;
//	}
//
//	public void setToUserId(String toUserId) {
//		this.toUserId = toUserId;
//	}
//
//	@Column(name="TO_USERNAME",length=128)
//	public String getToUserName() {
//		return toUserName;
//	}
//
//	public void setToUserName(String toUserName) {
//		this.toUserName = toUserName;
//	}
//
//	@Column(name="TO_HEADPIC")
//	public String getToHeadpic() {
//		return toHeadpic;
//	}
//
//	public void setToHeadpic(String toHeadpic) {
//		this.toHeadpic = toHeadpic;
//	}
	
	
	
	
	
}
