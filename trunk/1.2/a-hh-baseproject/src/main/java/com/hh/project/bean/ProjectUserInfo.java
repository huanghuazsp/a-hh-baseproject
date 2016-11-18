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
	private String userName;
	
	@Column(name="USER_NAME", length = 64)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	//角色
	private String roleName;
	
	@Column(name="ROLE_NAME", length = 64)
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
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
	
}