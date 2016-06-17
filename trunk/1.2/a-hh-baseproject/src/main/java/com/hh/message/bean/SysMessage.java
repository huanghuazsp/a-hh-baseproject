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
	private String type;
	private String content;
	private String title;
//	private String files;

	
	private String headpic = "";
	private String sendUserId = "";
	private String sendUserName = "";

	private String userId = "";
	private String userName = "";

	private String deptId = "";
	private String deptName = "";

	private String orgId = "";
	private String orgName = "";

	private String groupId = "";
	private String groupName = "";

	private String readUserId = "";
	private String deleteUserId = "";

	private int read = 0;
	
	
	private String sendObjectId = "";
	private String sendObjectName = "";
	private String sendObjectHeadpic = "";
	private int sendObjectType = 0;

	private enum Type {
		message
	}

	@Column(name = "TYPE_", length = 16)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Lob
	@Column(name = "CONTENT")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(length = 128, name = "TITLE")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

//	@Lob
//	@Column(name = "FILES")
//	public String getFiles() {
//		return files;
//	}
//
//	public void setFiles(String files) {
//		this.files = files;
//	}

	@Column(name = "SEND_USERID", length = 36)
	public String getSendUserId() {
		return sendUserId;
	}

	public void setSendUserId(String sendUserId) {
		this.sendUserId = sendUserId;
	}

	@Column(name = "SEND_USERNAME", length = 128)
	public String getSendUserName() {
		return sendUserName;
	}

	public void setSendUserName(String sendUserName) {
		this.sendUserName = sendUserName;
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

	@Column(name = "DEPT_ID", length = 36)
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	@Column(name = "DEPT_NAME", length = 128)
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(name = "ORG_ID", length = 36)
	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	@Column(name = "ORG_NAME", length = 128)
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@Column(name = "GROUP_ID", length = 36)
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	@Column(name = "GROUP_NAME", length = 128)
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Column(name="SEND_OBJECT_ID",length=36)
	public String getSendObjectId() {
		return sendObjectId;
	}

	public void setSendObjectId(String sendObjectId) {
		this.sendObjectId = sendObjectId;
	}

	@Column(name="SEND_OBJECT_NAME",length=128)
	public String getSendObjectName() {
		return sendObjectName;
	}

	public void setSendObjectName(String sendObjectName) {
		this.sendObjectName = sendObjectName;
	}

	
	@Column(name="SEND_OBJECT_TYPE")
	public int getSendObjectType() {
		return sendObjectType;
	}

	public void setSendObjectType(int sendObjectType) {
		this.sendObjectType = sendObjectType;
	}
	
	@Column(name="HEADPIC")
	public String getHeadpic() {
		return headpic;
	}

	public void setHeadpic(String headpic) {
		this.headpic = headpic;
	}

	@Column(name="SEND_OBJECT_HEADPIC")
	public String getSendObjectHeadpic() {
		return sendObjectHeadpic;
	}

	public void setSendObjectHeadpic(String sendObjectHeadpic) {
		this.sendObjectHeadpic = sendObjectHeadpic;
	}

}
