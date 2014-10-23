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
@Table(name = "OA_SCHEDULE")
public class Schedule extends BaseTwoEntity {
	private int ctype;
	private boolean allDay;
	private String content;
	
	private String participants;
	
	private String level ;
	private int isOk;
	private Date start;
	private Date end;
	
	private String userId;
	
	
	public boolean isAllDay() {
		return allDay;
	}
	public void setAllDay(boolean allDay) {
		this.allDay = allDay;
	}
	@Column(name = "USER_ID",length=36)
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Transient
	public int getCtype() {
		return ctype;
	}
	public void setCtype(int ctype) {
		this.ctype = ctype;
	}
	
	@Lob
	@Column(name = "CONTENT_")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
	@Transient
	public String getParticipants() {
		return participants;
	}
	public void setParticipants(String participants) {
		this.participants = participants;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "START_", length = 7)
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "END_", length = 7)
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	public int getIsOk() {
		return isOk;
	}
	public void setIsOk(int isOk) {
		this.isOk = isOk;
	}
	@Column(name = "LEVEL_",length=2)
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	
}
