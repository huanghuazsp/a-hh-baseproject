package com.hh.oa.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.hh.hibernate.dao.inf.Comment;
import com.hh.hibernate.util.base.BaseEntity;

@Comment("日程安排")
@Entity
@Table(name = "SYS_SCHEDULE")
public class Schedule extends BaseEntity {
	private boolean allDay;
	private String content;
	
	private String participants;
	
	private String level ;
	private int isOk;
	private Date start;
	private Date end;
	
	private String userId;
	
	private String summary;
	
	private String projectId;
	private String projectIdText;
	
	
	private String modularId;
	private String modularIdText;
	
	@Comment("是否全天")
	public boolean isAllDay() {
		return allDay;
	}
	public void setAllDay(boolean allDay) {
		this.allDay = allDay;
	}
	
	@Comment("用户id")
	@Column(name = "USER_ID",length=36)
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Comment("日程内容")
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
	
	@Comment("日程开始时间")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "START_", length = 7)
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	
	@Comment("日程结束时间")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "END_", length = 7)
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	
	@Comment("是否完成")
	public int getIsOk() {
		return isOk;
	}
	public void setIsOk(int isOk) {
		this.isOk = isOk;
	}
	@Comment("日程优先级")
	@Column(name = "LEVEL_",length=2)
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	
	@Comment("总结")
	@Lob
	@Column(name = "SUMMARY_")
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	@Comment("所属项目")
	@Column(name = "PROJECT_ID",length=32)
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	
	@Comment("所属项目名称")
	@Column(name = "PROJECT_ID_TEXT",length=128)
	public String getProjectIdText() {
		return projectIdText;
	}
	public void setProjectIdText(String projectIdText) {
		this.projectIdText = projectIdText;
	}
	
	@Comment("所属模块")
	@Column(name = "MODULAR_ID",length=32)
	public String getModularId() {
		return modularId;
	}
	public void setModularId(String modularId) {
		this.modularId = modularId;
	}
	@Comment("所属模块名称")
	@Column(name = "MODULAR_ID_TEXT",length=128)
	public String getModularIdText() {
		return modularIdText;
	}
	public void setModularIdText(String modularIdText) {
		this.modularIdText = modularIdText;
	}
	
}
