 package com.hh.system.bean;
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
@Table(name="SYS_RESOURCES")
public class SystemResources  extends BaseTwoEntity{
	//text
	private String text;
	private String files;
	@Column(name="TEXT")
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	//img
	private String img;
	
	@Column(name="IMG")
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	
	//type
	private String type;
	
	@Column(name="TYPE")
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@Lob
	@Column(name = "FILES_")
	public String getFiles() {
		return files;
	}

	public void setFiles(String files) {
		this.files = files;
	}
}