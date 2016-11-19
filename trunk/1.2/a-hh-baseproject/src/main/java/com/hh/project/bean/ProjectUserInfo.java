 package com.hh.project.bean;
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
@Table(name="PROJECT_USER_INFO")
public class ProjectUserInfo  extends BaseTwoEntity{
	//成员id
	private String user;
	
	@Column(name="USER", length = 36)
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	
	//成员名称
	private String userText;
	
	@Column(name="USER_TEXT", length = 64)
	public String getUserText() {
		return userText;
	}
	public void setUserText(String userText) {
		this.userText = userText;
	}
	
	//角色
	private String role;
	private String roleText;
	
	
	@Column(name="ROLE_",length=64)
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	@Column(name="ROLE_TEXT",length=64)
	public String getRoleText() {
		return roleText;
	}
	public void setRoleText(String roleText) {
		this.roleText = roleText;
	}

	//职责
	private String duty;
	
	@Column(name="DUTY", length = 128)
	public String getDuty() {
		return duty;
	}
	public void setDuty(String duty) {
		this.duty = duty;
	}
	
	//加入日期
	private Date joinDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="JOIN_DATE",length = 7)
	public Date getJoinDate() {
		return joinDate;
	}
	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
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
	
	//项目id
	private String projectId;
	
	@Column(name="PROJECT_ID", length = 36)
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	private String directManager;
	private String directManagerText;

	@Column(name="DIRECT_MANAGER", length = 36)
	public String getDirectManager() {
		return directManager;
	}
	public void setDirectManager(String directManager) {
		this.directManager = directManager;
	}
	
	@Column(name="DIRECT_MANAGER_TEXT", length = 64)
	public String getDirectManagerText() {
		return directManagerText;
	}
	public void setDirectManagerText(String directManagerText) {
		this.directManagerText = directManagerText;
	}
	
	
	
	
}