package com.hh.usersystem.bean.usersystem;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.hh.hibernate.dao.inf.Order;
import com.hh.hibernate.util.base.BaseTreeNodeEntity;

/**
 * HhXtCd entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SYS_MENU")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Order(fields = "order", sorts = "asc")
public class SysMenu extends BaseTreeNodeEntity<SysMenu> {
	private String vsj;
	private String vbz;
	private String vdtp = "/hhcommon/images/big/apple/20.png";
	private String vpname;
	private String params;
	private int openType;
	
	private String mobileUrl;
	
	public SysMenu(){
		
	}
	public SysMenu(String id, String text, String vsj, String icon, int exp,
			int leaf) {
		this.setId(id);
		this.setText(text);
		this.setVsj(vsj);
		this.setIcon(icon);
		this.setExpanded(exp);
		this.setLeaf(leaf);
	}

	@Column(name = "VSJ", length = 256)
	public String getVsj() {
		return this.vsj;
	}

	public void setVsj(String vsj) {
		this.vsj = vsj;
	}

	@Column(name = "VBZ", length = 512)
	public String getVbz() {
		return this.vbz;
	}

	public void setVbz(String vbz) {
		this.vbz = vbz;
	}

	@Column(name = "VDTP", length = 128)
	public String getVdtp() {
		return this.vdtp;
	}

	public void setVdtp(String vdtp) {
		this.vdtp = vdtp;
	}

	@Transient
	public String getVpname() {
		return vpname;
	}

	public void setVpname(String vpname) {
		this.vpname = vpname;
	}

	@Column(length = 4000)
	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	@Column(name = "OPEN_TYPE", length = 1)
	public int getOpenType() {
		return openType;
	}

	public void setOpenType(int openType) {
		this.openType = openType;
	}
	
	@Column(name = "MOBILE_URL", length = 256)
	public String getMobileUrl() {
		return mobileUrl;
	}
	public void setMobileUrl(String mobileUrl) {
		this.mobileUrl = mobileUrl;
	}
	@Column(name = "LEAF", length = 1)
	public int getLeaf() {
		return leaf;
	}


}