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
@Comment("模块/任务")
@Order
@SuppressWarnings("serial")
@Entity
@Table(name="PROJECT_MODULAR")
public class ProjectModular  extends BaseEntityTree<ProjectModular>{
	private String describe;
	
	@Comment("描述")
	@Lob
	@Column(name="DESCRIBE_")
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	
	private String projectId;
	
	@Comment("项目id")
	@Column(name="PROJECT_ID", length = 36)
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	private int input;
	
	@Comment("投入")
	@Column(name="INPUT_")
	public int getInput() {
		return input;
	}
	public void setInput(int input) {
		this.input = input;
	}
	
	
	private int type;

	@Comment("类型【0：任务，1：模块】")
	@Column(name="TYPE_")
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
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
	
	
}