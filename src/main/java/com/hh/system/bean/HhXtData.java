package com.hh.system.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.hh.hibernate.dao.inf.Order;
import com.hh.hibernate.util.base.BaseTreeNodeEntity;

@Entity
@Table(name = "HH_XT_DATA")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Order
public class HhXtData extends BaseTreeNodeEntity<HhXtData> {
	// private String text;
	private String describe_;
	// private String node;
	private String type;
	// private int leaf;
	// private int expanded;
	
	private int dataType;


	// @Column(name = "EXPANDED", length = 1)
	// public int getExpanded() {
	// return expanded;
	// }
	//
	// public void setExpanded(int expanded) {
	// this.expanded = expanded;
	// }
	//
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

	@Column(name = "DESCRIBE_", length = 1024)
	public String getDescribe_() {
		return describe_;
	}

	public void setDescribe_(String describe_) {
		this.describe_ = describe_;
	}

	// @Column(name = "LEAF", length = 1)
	// public int getLeaf() {
	// return leaf;
	// }
	//
	// public void setLeaf(int leaf) {
	// this.leaf = leaf;
	// }

	@Column(name = "TYPE", length = 32)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getDataType() {
		return dataType;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

}
