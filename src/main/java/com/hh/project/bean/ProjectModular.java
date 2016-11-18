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
@Table(name="PROJECT_MODULAR")
public class ProjectModular  extends BaseTwoEntity{
	//名称
	private String text;
	
	@Column(name="TEXT", length = 128)
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
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