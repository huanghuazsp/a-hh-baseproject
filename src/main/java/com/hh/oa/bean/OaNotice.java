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
@Table(name="OA_NOTICE")
public class OaNotice  extends BaseTwoEntity{
	
	private String content;
	private String title;
	private String range;
	private String rangeText;
	
	private String type;
	private String typeText;
	
	private int deploy;
	
	private String deployDept;
	
	
	private int rangeType;
	
	@Lob
	@Column(name="CONTENT")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(length = 128,name="TITLE")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Lob
	@Column(name="RANGE_")
	public String getRange() {
		return range;
	}

	public void setRange(String range) {
		this.range = range;
	}

	@Lob
	@Column(name="RANGE_TEXT")
	public String getRangeText() {
		return rangeText;
	}

	public void setRangeText(String rangeText) {
		this.rangeText = rangeText;
	}

	@Column(name="TYPE_",length=36)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name="TYPE_TEXT",length=128)
	public String getTypeText() {
		return typeText;
	}

	public void setTypeText(String typeText) {
		this.typeText = typeText;
	}

	@Column(name="DEPLOY")
	public int getDeploy() {
		return deploy;
	}

	public void setDeploy(int deploy) {
		this.deploy = deploy;
	}

	@Column(name="DEPLOY_DEPT",length=256)
	public String getDeployDept() {
		return deployDept;
	}

	public void setDeployDept(String deployDept) {
		this.deployDept = deployDept;
	}

	@Column(name="RANGE_TYPE")
	public int getRangeType() {
		return rangeType;
	}

	public void setRangeType(int rangeType) {
		this.rangeType = rangeType;
	}
	
	
	
}