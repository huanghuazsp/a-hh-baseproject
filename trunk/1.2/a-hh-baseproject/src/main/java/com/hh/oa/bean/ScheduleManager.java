package com.hh.oa.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.hh.hibernate.util.base.BaseTwoEntity;

@Entity
@Table(name = "OA_SCHEDULE_MANAGER")
public class ScheduleManager extends BaseTwoEntity {
	
	private String userId;
	private String comment;
	private int isRead;
	
	@Column(name = "USER_ID",length=36)
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Column(name = "COMMENT_",length=2000)
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	@Column(name = "IS_READ", length = 1)
	public int getIsRead() {
		return isRead;
	}
	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}
}
