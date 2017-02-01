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
import com.hh.hibernate.dao.inf.Comment;
@Comment("Bug")
@Order
@SuppressWarnings("serial")
@Entity
@Table(name="PROJECT_BUG")
public class ProjectBug  extends BaseEntity{
	private String text;
	
	@Comment("名称")
	@Column(name="TEXT", length = 128)
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	private String projectId;
	
	@Comment("项目ID")
	@Column(name="PROJECT_ID", length = 36)
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	private String projectIdText;
	
	@Comment("项目名称")
	@Column(name="PROJECT_ID_TEXT", length = 128)
	public String getProjectIdText() {
		return projectIdText;
	}
	public void setProjectIdText(String projectIdText) {
		this.projectIdText = projectIdText;
	}
	
	private String modularId;
	
	@Comment("模块ID")
	@Column(name="MODULAR_ID", length = 36)
	public String getModularId() {
		return modularId;
	}
	public void setModularId(String modularId) {
		this.modularId = modularId;
	}
	
	private String modularIdText;
	
	@Comment("模块名称")
	@Column(name="MODULAR_ID_TEXT", length = 128)
	public String getModularIdText() {
		return modularIdText;
	}
	public void setModularIdText(String modularIdText) {
		this.modularIdText = modularIdText;
	}
	
	private String describe_;
	
	@Comment("描述")
	@Lob
	@Column(name="DESCRIBE_")
	public String getDescribe_() {
		return describe_;
	}
	public void setDescribe_(String describe_) {
		this.describe_ = describe_;
	}
	
	private String processingPeople;
	
	@Comment("处理人")
	@Column(name="PROCESSING_PEOPLE", length = 36)
	public String getProcessingPeople() {
		return processingPeople;
	}
	public void setProcessingPeople(String processingPeople) {
		this.processingPeople = processingPeople;
	}
	
	private String processingPeopleText;
	
	@Comment("处理人名称")
	@Column(name="PROCESSING_PEOPLE_TEXT", length = 128)
	public String getProcessingPeopleText() {
		return processingPeopleText;
	}
	public void setProcessingPeopleText(String processingPeopleText) {
		this.processingPeopleText = processingPeopleText;
	}
	
	private String handlingUsers;
	
	@Comment("抄送人")
	@Lob
	@Column(name="HANDLING_USERS")
	public String getHandlingUsers() {
		return handlingUsers;
	}
	public void setHandlingUsers(String handlingUsers) {
		this.handlingUsers = handlingUsers;
	}
	
	private String handlingUsersText;
	
	@Comment("抄送人名称")
	@Lob
	@Column(name="HANDLING_USERS_TEXT")
	public String getHandlingUsersText() {
		return handlingUsersText;
	}
	public void setHandlingUsersText(String handlingUsersText) {
		this.handlingUsersText = handlingUsersText;
	}
	
	private int state;
	
	@Comment("状态【0：新建，1：已解决，2：重现，9：关闭】")
	@Column(name="STATE")
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	
}