package com.hh.usersystem.bean.usersystem;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.hh.hibernate.util.base.BaseTwoEntity;

/**
 * HhXtJs entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "US_ROLE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UsRole extends BaseTwoEntity  {

	private String text;
	private String vbz;
	private int nlx;
	
	private String jssx;

	// Constructors

	/** default constructor */
	public UsRole() {
	}

	@Column(name = "NLX", precision = 1, scale = 0)
	public int getNlx() {
		return nlx;
	}

	public void setNlx(int nlx) {
		this.nlx = nlx;
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
	
	@Column(name = "JSSX", length = 64)
	public String getJssx() {
		return jssx;
	}

	public void setJssx(String jssx) {
		this.jssx = jssx;
	}

	private Integer state;
	@Column(name="STATE_")
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		if (state == null) {
			state = 0;
		}
		this.state = state;
	}
	
}