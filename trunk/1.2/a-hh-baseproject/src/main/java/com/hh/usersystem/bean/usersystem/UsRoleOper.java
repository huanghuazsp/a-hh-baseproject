package com.hh.usersystem.bean.usersystem;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.hh.hibernate.util.base.BaseTwoEntity;

/**
 * HhXtJsCd entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "US_ROLE_OPER")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UsRoleOper extends BaseTwoEntity implements java.io.Serializable {
	private String jsId;
	private String operLevel;
	
	
	public String getOperLevel() {
		return operLevel;
	}

	public void setOperLevel(String operLevel) {
		this.operLevel = operLevel;
	}
	private SysOper hhXtCz = new SysOper();
	@ManyToOne(cascade={CascadeType.ALL}) 
	public SysOper getHhXtCz() {
		return hhXtCz;
	}

	public void setHhXtCz(SysOper hhXtCz) {
		this.hhXtCz = hhXtCz;
	}

	@Column(name = "JS_ID", nullable = false, length = 36)
	public String getJsId() {
		return this.jsId;
	}

	public void setJsId(String jsId) {
		this.jsId = jsId;
	}
	private String czId;

	@Column(name = "HHXTCZ_ID", length = 36,insertable=false,updatable=false)
	public String getCzId() {
		return czId;
	}

	public void setCzId(String czId) {
		this.czId = czId;
	}
	

}