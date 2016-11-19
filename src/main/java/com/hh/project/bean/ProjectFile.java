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
@Table(name="PROJECT_FILE")
public class ProjectFile  extends BaseTwoEntity{
	//文档名称
	private String text;
	
	@Column(name="TEXT", length = 512)
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	//类型
	private String type;
	
	@Column(name="TYPE", length = 64)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	//附件id
	private String fileStr;
	
	@Lob
	@Column(name="FILE_STR")
	public String getFileStr() {
		return fileStr;
	}
	public void setFileStr(String fileStr) {
		this.fileStr = fileStr;
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