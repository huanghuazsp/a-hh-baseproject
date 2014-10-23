package com.hh.usersystem.bean.usersystem;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.hh.hibernate.util.base.BaseTwoEntity;

@SuppressWarnings("serial")
@Entity
@Table(name = "HH_XT_CZ")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "eternal")
public class HhXtCz extends BaseTwoEntity {
	private String text;
	private String vpid;
	private String menuUrl;
//	private String vsj;
	private String vurl;
	private String pageText;
	private String operLevel;

	@Transient
	public String getOperLevel() {
		return operLevel;
	}

	public void setOperLevel(String operLevel) {
		this.operLevel = operLevel;
	}

	@Column(name = "TEXT", length = 64)
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Column(name = "VPID", length = 128)
	public String getVpid() {
		return vpid;
	}

	public void setVpid(String vpid) {
		this.vpid = vpid;
	}

//	@Column(name = "VSJ", length = 256)
//	public String getVsj() {
//		return vsj;
//	}
//
//	public void setVsj(String vsj) {
//		this.vsj = vsj;
//	}

	@Column(name = "VURL", length = 256)
	public String getVurl() {
		return vurl;
	}

	public void setVurl(String vurl) {
		this.vurl = vurl;
	}

	@Column(name = "PAGE_TEXT", length = 256)
	public String getPageText() {
		return pageText;
	}

	public void setPageText(String pageText) {
		this.pageText = pageText;
	}
	@Column(name = "MENU_URL", length = 256)
	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

}