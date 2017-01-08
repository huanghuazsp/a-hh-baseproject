package com.hh.usersystem.bean.usersystem;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.hh.hibernate.util.base.BaseEntity;

/**
 * HhXtJs entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "US_ROLE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UsRole extends BaseEntity  {

	private String text;
	private String vbz;
	
	private String jssx;
	private String jssxText;

	// Constructors

	/** default constructor */
	public UsRole() {
	}

	@Column(name = "TEXT", length = 64)
	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Column(name = "VBZ", length = 512)
	public String getVbz() {
		return this.vbz;
	}

	public void setVbz(String vbz) {
		this.vbz = vbz;
	}

	private int state;
	@Column(name="STATE_")
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
	
	@Column(name = "JSSX", length = 64)
	public String getJssx() {
		return jssx;
	}

	public void setJssx(String jssx) {
		this.jssx = jssx;
	}

	@Column(name = "JSSX_TEXT", length = 64)
	public String getJssxText() {
		return jssxText;
	}

	public void setJssxText(String jssxText) {
		this.jssxText = jssxText;
	}
	
	
	
}