package com.hh.system.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.hh.hibernate.util.base.BaseOneEntity;
import com.hh.system.util.Check;
import com.hh.system.util.Convert;
import com.hh.system.util.SysParam;

@SuppressWarnings("serial")
@Entity
@Table(name = "SYS_PARAMS")
public class SysParams extends BaseOneEntity {
	private int logSql;
	private int dataBaseSql;
	
	private int dataBaseRequest;
	
	private int power;
	private String sysName;
	private String sysIcon;
	private int onePage;
	private String sysImg;
	private String loginBackImg;
	
	public int getLogSql() {
		return logSql;
	}
	public void setLogSql(int logSql) {
		this.logSql = logSql;
	}
	public int getDataBaseSql() {
		return dataBaseSql;
	}
	public void setDataBaseSql(int dataBaseSql) {
		this.dataBaseSql = dataBaseSql;
	}
	public int getPower() {
		return power;
	}
	public void setPower(int power) {
		this.power = power;
	}
	@Column(name="SYS_NAME",length=128)
	public String getSysName() {
		return sysName;
	}
	public void setSysName(String sysName) {
		this.sysName = sysName;
	}
	@Column(name="SYS_ICON",length=256)
	public String getSysIcon() {
		return sysIcon;
	}
	
	@Transient
	public String getSysIcon2() {
		String href = Convert.toString(sysIcon);
		if (href.indexOf(".") == -1) {
			href = "system-File-download?params={id:'"
					+ href + "'}";
		}
		return href;
	}
	
	public void setSysIcon(String sysIcon) {
		this.sysIcon = sysIcon;
	}
	public int getOnePage() {
		return onePage;
	}
	public void setOnePage(int onePage) {
		this.onePage = onePage;
	}
	@Column(name="SYS_IMG",length=256)
	public String getSysImg() {
		return sysImg;
	}
	
	@Transient
	public String getSysImg2() {
		String href = Convert.toString(sysImg);
		if (href.indexOf(".") == -1) {
			href = "system-File-download?params={id:'"
					+ href + "'}";
		}
		return href;
	}
	
	public void setSysImg(String sysImg) {
		this.sysImg = sysImg;
	}
	@Column(name="LOGIN_BACK_IMG",length=256)
	public String getLoginBackImg() {
		if (Check.isEmpty(loginBackImg)) {
			return "/hhcommon/images/background/background.png";
		}
		return loginBackImg;
	}
	public void setLoginBackImg(String loginBackImg) {
		this.loginBackImg = loginBackImg;
	}
	@Column(columnDefinition="INT default 0")
	public int getDataBaseRequest() {
		return dataBaseRequest;
	}
	public void setDataBaseRequest(int dataBaseRequest) {
		this.dataBaseRequest = dataBaseRequest;
	}
	
	
}
