package com.hh.usersystem.bean.usersystem;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.hh.hibernate.util.base.BaseTwoEntity;

@Entity
@Table(name = "SYS_OPER")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SysOper extends BaseTwoEntity {
	private String text;
	private String menuId;
	private String menuIdText;
	private String menuUrl;
//	private String vsj;
	private String vurl;
	private String pageText;
	private String operLevel;
	
	//类型1是菜单
	private int type;

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

	@Transient
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	
	@Column(name = "MENU_ID", length = 128)
	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	@Column(name = "MENU_ID_TEXT", length = 128)
	public String getMenuIdText() {
		return menuIdText;
	}

	public void setMenuIdText(String menuIdText) {
		this.menuIdText = menuIdText;
	}
	
	

}