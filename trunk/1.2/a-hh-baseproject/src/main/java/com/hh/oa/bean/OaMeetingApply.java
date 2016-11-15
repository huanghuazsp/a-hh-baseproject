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
@Table(name="OA_MEETING_APPLY")
public class OaMeetingApply  extends BaseTwoEntity{
	//会议主题
	private String text;
	
	@Column(name="TEXT", length = 1024)
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	//出席人员
	private String attendUser;
	
	@Lob
	@Column(name="ATTEND_USER")
	public String getAttendUser() {
		return attendUser;
	}
	public void setAttendUser(String attendUser) {
		this.attendUser = attendUser;
	}
	
	//出席人员名称
	private String attendUserText;
	
	@Lob
	@Column(name="ATTEND_USER_TEXT")
	public String getAttendUserText() {
		return attendUserText;
	}
	public void setAttendUserText(String attendUserText) {
		this.attendUserText = attendUserText;
	}
	
	//出席部门
	private String attendOrg;
	
	@Lob
	@Column(name="ATTEND_ORG")
	public String getAttendOrg() {
		return attendOrg;
	}
	public void setAttendOrg(String attendOrg) {
		this.attendOrg = attendOrg;
	}
	
	//出席部门名称
	private String attendOrgText;
	
	@Lob
	@Column(name="ATTEND_ORG_TEXT")
	public String getAttendOrgText() {
		return attendOrgText;
	}
	public void setAttendOrgText(String attendOrgText) {
		this.attendOrgText = attendOrgText;
	}
	
	//开始时间
	private Date start;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="START_DATE",length = 7)
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	
	//结束时间
	private Date end;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="END_DATE",length = 7)
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	
	//描述
	private String describe;
	
	@Lob
	@Column(name="DESCRIBE_")
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	
	//会议id
	private String meetingId;
	
	@Column(name="MEETING_ID", length = 36)
	public String getMeetingId() {
		return meetingId;
	}
	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}
	
	//会议名称
	private String meetingIdText;
	
	@Column(name="MEETING_ID_TEXT", length = 64)
	public String getMeetingIdText() {
		return meetingIdText;
	}
	public void setMeetingIdText(String meetingIdText) {
		this.meetingIdText = meetingIdText;
	}
	
}