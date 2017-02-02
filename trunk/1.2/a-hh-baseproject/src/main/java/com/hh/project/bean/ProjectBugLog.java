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
@Comment("错误日志")
@Order
@SuppressWarnings("serial")
@Entity
@Table(name="PROJECT_BUG_LOG")
public class ProjectBugLog  extends BaseEntity{
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
	
	private int type;
	
	@Comment("类型【1：解决，2：重现，9：关闭】")
	@Column(name="TYPE")
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	private String bugId;
	
	@Comment("bugId")
	@Column(name="BUG_ID", length = 36)
	public String getBugId() {
		return bugId;
	}
	public void setBugId(String bugId) {
		this.bugId = bugId;
	}
	
}