package com.hh.usersystem.bean.usersystem;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.hh.hibernate.dao.inf.Order;
import com.hh.hibernate.util.base.BaseTwoEntity;
import com.hh.usersystem.IUser;
import com.hh.usersystem.util.steady.StaticProperties;

/**
 * HhXtYh entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "HH_XT_YH")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Order
public class HhXtYh extends BaseTwoEntity implements IUser {
	@Override
	public String toString() {
		return this.getId();
	}

	private String text;
	private String vdlzh;
	private String vmm;
	private int nxb;
	private int nzt;
	private String vdzyj;
	private String vdh;
	private Date dsr;
	private String headpic;

	private List<HhXtCd> hhXtCdList = new ArrayList<HhXtCd>();// 菜单
	private List<HhXtJs> hhXtJsList = new ArrayList<HhXtJs>();// 角色
	private List<HhXtCd> hhXtYhCdZmtbList = new ArrayList<HhXtCd>();// 桌面快捷方式
	private List<HhXtCz> hhXtCzList = new ArrayList<HhXtCz>();// 用户的操作权限
	private List<String> hhXtCzUrlList = new ArrayList<String>();
	private List<String> hhXtCzPageTextList = new ArrayList<String>();

	private Map<String, List<String>> hhXtCzPageTextMap = new HashMap<String, List<String>>();

	private List<String> jsList = new ArrayList<String>();// 角色ID
//	private HHXtZmsx hhXtZmsx;// 用户属性
	private List<Organization> organizationList = new ArrayList<Organization>();// 岗位
	private String orgIdsStr;
	private String jsIdsStr;

	private Organization organization;
	
	
	private String vzmbj = StaticProperties.HHXT_USERSYSTEM_ZMBJ;
	private int pageSize = 15;
	private String defaultOrgId;
	private String theme;
	private String desktopType;
	

//	@OneToOne(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
//	@JoinColumn(name = "ID")
//	public HHXtZmsx getHhXtZmsx() {
//		return hhXtZmsx;
//	}
//
//	public void setHhXtZmsx(HHXtZmsx hhXtZmsx) {
//		this.hhXtZmsx = hhXtZmsx;
//	}

	@Transient
	public List<String> getJsList() {
		return jsList;
	}

	public void setJsList(List<String> jsList) {
		this.jsList = jsList;
	}

	@Transient
	public List<HhXtCd> getHhXtYhCdZmtbList() {
		return hhXtYhCdZmtbList;
	}

	public void setHhXtYhCdZmtbList(List<HhXtCd> hhXtYhCdZmtbList) {
		this.hhXtYhCdZmtbList = hhXtYhCdZmtbList;
	}

	@Transient
	public List<HhXtCd> getHhXtCdList() {
		return hhXtCdList;
	}

	public void setHhXtCdList(List<HhXtCd> hhXtCdList) {
		this.hhXtCdList = hhXtCdList;
	}

	@Transient
	public List<HhXtCz> getHhXtCzList() {
		return hhXtCzList;
	}

	public void setHhXtCzList(List<HhXtCz> hhXtCzList) {
		this.hhXtCzList = hhXtCzList;
	}

	@Transient
	public List<HhXtJs> getHhXtJsList() {
		return hhXtJsList;
	}

	public void setHhXtJsList(List<HhXtJs> hhXtJsList) {
		this.hhXtJsList = hhXtJsList;
	}

	@Transient
	public List<Organization> getOrganizationList() {
		return organizationList;
	}

	public void setOrganizationList(List<Organization> organizationList) {
		this.organizationList = organizationList;
	}

	@Transient
	public List<String> getHhXtCzUrlList() {
		return hhXtCzUrlList;
	}

	public void setHhXtCzUrlList(List<String> hhXtCzUrlList) {
		this.hhXtCzUrlList = hhXtCzUrlList;
	}

	/** default constructor */
	public HhXtYh() {
	}

	@Column(name = "TEXT", length = 64)
	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Column(name = "VDLZH", length = 32)
	public String getVdlzh() {
		return this.vdlzh;
	}

	public void setVdlzh(String vdlzh) {
		this.vdlzh = vdlzh;
	}

	@Column(name = "VMM", length = 16)
	public String getVmm() {
		return this.vmm;
	}

	public void setVmm(String vmm) {
		this.vmm = vmm;
	}

	@Column(name = "NXB", precision = 1, scale = 0)
	public int getNxb() {
		return this.nxb;
	}

	public void setNxb(int nxb) {
		this.nxb = nxb;
	}

	@Column(name = "NZT", precision = 1, scale = 0)
	public int getNzt() {
		return this.nzt;
	}

	public void setNzt(int nzt) {
		this.nzt = nzt;
	}

	@Column(name = "VDZYJ", length = 64)
	public String getVdzyj() {
		return this.vdzyj;
	}

	public void setVdzyj(String vdzyj) {
		this.vdzyj = vdzyj;
	}

	@Column(name = "VDH", length = 32)
	public String getVdh() {
		return this.vdh;
	}

	public void setVdh(String vdh) {
		this.vdh = vdh;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DSR", length = 7)
	public Date getDsr() {
		return this.dsr;
	}

	public void setDsr(Date dsr) {
		this.dsr = dsr;
	}

	@Transient
	public String getOrgIdsStr() {
		return orgIdsStr;
	}

	public void setOrgIdsStr(String orgIdsStr) {
		this.orgIdsStr = orgIdsStr;
	}

	@Transient
	public String getJsIdsStr() {
		return jsIdsStr;
	}

	public void setJsIdsStr(String jsIdsStr) {
		this.jsIdsStr = jsIdsStr;
	}

	public String getHeadpic() {
		return headpic;
	}

	public void setHeadpic(String headpic) {
		this.headpic = headpic;
	}

	@Transient
	public List<String> getHhXtCzPageTextList() {
		return hhXtCzPageTextList;
	}

	public void setHhXtCzPageTextList(List<String> hhXtCzPageTextList) {
		this.hhXtCzPageTextList = hhXtCzPageTextList;
	}

	@Transient
	public Map<String, List<String>> getHhXtCzPageTextMap() {
		return hhXtCzPageTextMap;
	}

	public void setHhXtCzPageTextMap(Map<String, List<String>> hhXtCzPageTextMap) {
		this.hhXtCzPageTextMap = hhXtCzPageTextMap;
	}
	@Transient
	@Override
	public String getOrgId() {
		if (organization != null && organization.getJg()!=null) {
			organization.getJg().getId();
		}
		return "";
	}
	@Transient
	@Override
	public String getOrgText() {
		if (organization != null && organization.getJg()!=null) {
			organization.getJg().getText();
		}
		return "";
	}
	@Transient
	@Override
	public String getJobId() {
		if (organization != null) {
			organization.getId();
		}
		return "";
	}
	@Transient
	@Override
	public String getJobText() {
		if (organization != null) {
			organization.getText();
		}
		return "";
	}
	@Transient
	@Override
	public String getDeptId() {
		if (organization != null && organization.getBm()!=null) {
			organization.getBm().getId();
		}
		return "";
	}
	@Transient
	@Override
	public String getDeptText() {
		if (organization != null && organization.getBm()!=null) {
			organization.getBm().getText();
		}
		return "";
	}

	@Transient
	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	@Column(name="VZMBJ",length=256)
	public String getVzmbj() {
		return vzmbj;
	}

	public void setVzmbj(String vzmbj) {
		this.vzmbj = vzmbj;
	}

	@Column(name = "PAGE_SIZE")
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	@Column(name = "DEFAULT_ORG_ID",length=36)
	public String getDefaultOrgId() {
		return defaultOrgId;
	}

	public void setDefaultOrgId(String defaultOrgId) {
		this.defaultOrgId = defaultOrgId;
	}

	@Column(name = "THEME_",length=36)
	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	@Column(name = "DESKTOP_TYPE_",length=36)
	public String getDesktopType() {
		return desktopType;
	}

	public void setDesktopType(String desktopType) {
		this.desktopType = desktopType;
	}

}