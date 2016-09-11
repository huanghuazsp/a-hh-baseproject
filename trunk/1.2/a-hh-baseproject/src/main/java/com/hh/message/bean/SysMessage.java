package com.hh.message.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Index;

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
	
	private String objectId = "";
	private String objectName = "";
	private String objectHeadpic = "";
	
	private int sendObjectType = 0;
	
	
	private String params = "";
	

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
	
	@Index(name="SYS_MESSAGE_INDEX_SEND_OBJECT_TYPE")
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

	@Index(name="SYS_MESSAGE_INDEX_TO_OBJECTID")
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

	
	@Column(name="OBJECTID",length=36)
	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	@Column(name="OBJECTNAME",length=128)
	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	@Column(name="OBJECT_HEADPIC")
	public String getObjectHeadpic() {
		return objectHeadpic;
	}

	public void setObjectHeadpic(String objectHeadpic) {
		this.objectHeadpic = objectHeadpic;
	}

	@Column(name="PARAMS_",length=128)
	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	
	
}
