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
@Table(name="OA_MEETING")
public class OaMeeting  extends BaseEntity{
	//会议名称
	private String text;
	
	@Column(name="TEXT", length = 256)
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	//可容纳人数
	private int peopleNumber;
	
	@Column(name="PEOPLE_NUMBER")
	public int getPeopleNumber() {
		return peopleNumber;
	}
	public void setPeopleNumber(int peopleNumber) {
		this.peopleNumber = peopleNumber;
	}
	
	//设备情况
	private String device;
	
	@Column(name="DEVICE", length = 1024)
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	
	//所在地点
	private String locale;
	
	@Column(name="LOCALE", length = 1024)
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	
	//会议室描述
	private String describe;
	
	@Column(name="DESCRIBE_", length = 1024)
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	
	//管理员
	private String manager;
	
	@Column(name="MANAGER", length = 36)
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	
	//管理员名称
	private String managerText;
	
	@Column(name="MANAGER_TEXT", length = 64)
	public String getManagerText() {
		return managerText;
	}
	public void setManagerText(String managerText) {
		this.managerText = managerText;
	}
	
}