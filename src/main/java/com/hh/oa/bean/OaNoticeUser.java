package com.hh.oa.bean;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.Table;

import com.hh.hibernate.util.base.BaseOneEntity;

@Entity(name = "OA_NOTICE_USER")
@Table(indexes = { @Index(name = "OA_NOTICE_USER_INDEX_USER_ID_OBJECT_ID", columnNames = { "USER_ID", "OBJECT_ID" }) }, appliesTo = "OA_NOTICE_USER")
public class OaNoticeUser extends BaseOneEntity {

	private String objectId;
	private String userId;
	private String userName;
	private int read;

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

	@Column(name = "READ_")
	public int getRead() {
		return read;
	}

	public void setRead(int read) {
		this.read = read;
	}

	@Column(name = "OBJECT_ID", length = 36)
	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	

}
