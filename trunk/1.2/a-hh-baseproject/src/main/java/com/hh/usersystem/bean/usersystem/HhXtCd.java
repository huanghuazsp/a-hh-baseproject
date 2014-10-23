package com.hh.usersystem.bean.usersystem;

import java.util.ArrayList;
import java.util.List;

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
@Table(name = "HH_XT_CD")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Order(fields = "order", sorts = "desc")
public class HhXtCd extends BaseTreeNodeEntity<HhXtCd>  {

	// Fields

	// private String text;
	// private String node;
	// private String icon;
	private String vsj;
	// private Long leaf;
	private String vbz;
	// private int nlx;
	private String vdtp = "/hhcommon/images/big/apple/20.png";
	private String vpname;
	private String params;

	// Constructors

	/** default constructor */
	public HhXtCd() {
	}

	public HhXtCd(String id, String text, String vsj, String icon, int exp,
			int leaf) {
		this.setId(id);
		this.setText(text);
		this.setVsj(vsj);
		this.setIcon(icon);
		this.setExpanded(exp);
		this.setLeaf(leaf);
	}

	// @Column(name = "TEXT", length = 64)
	// public String getText() {
	// return this.text;
	// }
	//
	// public void setText(String text) {
	// this.text = text;
	// }
	//
	// @Column(name = "NODE", length = 128)
	// public String getNode() {
	// return this.node;
	// }
	//
	// public void setNode(String node) {
	// this.node = node;
	// }
	//
	// @Column(name = "ICON", length = 128)
	// public String getIcon() {
	// return this.icon;
	// }
	//
	// public void setIcon(String icon) {
	// this.icon =icon;
	// }

	@Column(name = "VSJ", length = 256)
	public String getVsj() {
		return this.vsj;
	}

	public void setVsj(String vsj) {
		this.vsj = vsj;
	}

	// @Column(name = "LEAF", precision = 1, scale = 0)
	// public Long getLeaf() {
	// return this.leaf;
	// }
	//
	// public void setLeaf(Long leaf) {
	// this.leaf = leaf;
	// }

	@Column(name = "VBZ", length = 512)
	public String getVbz() {
		return this.vbz;
	}

	public void setVbz(String vbz) {
		this.vbz = vbz;
	}

	// @Column(name = "NLX", precision = 1, scale = 0)
	// public int getNlx() {
	// return this.nlx;
	// }
	//
	// public void setNlx(int nlx) {
	// this.nlx = nlx;
	// }

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

}