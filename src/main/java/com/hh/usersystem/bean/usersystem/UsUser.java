package com.hh.usersystem.bean.usersystem;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.hh.hibernate.dao.inf.Order;
import com.hh.hibernate.util.base.BaseTwoEntity;
import com.hh.system.util.PinYinUtil;
import com.hh.usersystem.IUser;
import com.hh.usersystem.util.steady.StaticProperties;

/**
 * HhXtYh entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "US_USER")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Order
public class UsUser extends BaseTwoEntity implements IUser {
	@Override
	public String toString() {
		return this.getId();
	}

	private String text;
	private String vdlzh;
	private String vmm;
	private int nxb;
	private String vdzyj;
	private String vdh;
	private Date dsr;
	private String headpic;
	private String textpinyin;

	private List<SysMenu> hhXtCdList = new ArrayList<SysMenu>();// 菜单
	private List<UsRole> hhXtJsList = new ArrayList<UsRole>();// 角色
	private List<SysMenu> hhXtYhCdZmtbList = new ArrayList<SysMenu>();// 桌面快捷方式
	private Map<String, SysOper> hhXtCzMap = new HashMap<String, SysOper>();// 用户的操作权限
	private List<String> hhXtCzUrlList = new ArrayList<String>();
	private List<String> hhXtCzPageTextList = new ArrayList<String>();

	private Map<String, List<String>> hhXtCzPageTextMap = new HashMap<String, List<String>>();

	private List<String> jsList = new ArrayList<String>();// 角色ID
	// private HHXtZmsx hhXtZmsx;// 用户属性
	// private List<Organization> organizationList = new
	// ArrayList<Organization>();// 岗位
	private String orgIdsStr;
	private String jsIdsStr;

	// private Map<String, Organization> organization;

	private String vzmbj = StaticProperties.HHXT_USERSYSTEM_ZMBJ;
	private int pageSize = 15;
	private String theme;
	private String desktopType;

	// @OneToOne(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	// @JoinColumn(name = "ID")
	// public HHXtZmsx getHhXtZmsx() {
	// return hhXtZmsx;
	// }
	//
	// public void setHhXtZmsx(HHXtZmsx hhXtZmsx) {
	// this.hhXtZmsx = hhXtZmsx;
	// }
	private UsOrganization dept;// 部门
	private UsOrganization org;// 机构
	// private Organization jt ;// 集团
	private UsOrganization job;

	private String jobId;
	private String deptId;
	private String orgId;

	@Transient
	public UsOrganization getDept() {
		return dept;
	}

	public void setDept(UsOrganization bm) {
		this.dept = bm;
	}

	@Transient
	public UsOrganization getOrg() {
		return org;
	}

	public void setOrg(UsOrganization jg) {
		this.org = jg;
	}

	// @Transient
	// public Organization getJt() {
	// return jt;
	// }
	//
	// public void setJt(Organization jt) {
	// this.jt = jt;
	// }

	@Transient
	public UsOrganization getJob() {
		return job;
	}

	public void setJob(UsOrganization gw) {
		this.job = gw;
	}

	@Transient
	public List<String> getJsList() {
		return jsList;
	}

	public void setJsList(List<String> jsList) {
		this.jsList = jsList;
	}

	@Transient
	public List<SysMenu> getHhXtYhCdZmtbList() {
		return hhXtYhCdZmtbList;
	}

	public void setHhXtYhCdZmtbList(List<SysMenu> hhXtYhCdZmtbList) {
		this.hhXtYhCdZmtbList = hhXtYhCdZmtbList;
	}

	@Transient
	public List<SysMenu> getHhXtCdList() {
		return hhXtCdList;
	}

	public void setHhXtCdList(List<SysMenu> hhXtCdList) {
		this.hhXtCdList = hhXtCdList;
	}

	@Transient
	public List<UsRole> getHhXtJsList() {
		return hhXtJsList;
	}

	public void setHhXtJsList(List<UsRole> hhXtJsList) {
		this.hhXtJsList = hhXtJsList;
	}

	// @Transient
	// public List<Organization> getOrganizationList() {
	// return organizationList;
	// }
	//
	// public void setOrganizationList(List<Organization> organizationList) {
	// this.organizationList = organizationList;
	// }

	@Transient
	public List<String> getHhXtCzUrlList() {
		return hhXtCzUrlList;
	}

	public void setHhXtCzUrlList(List<String> hhXtCzUrlList) {
		this.hhXtCzUrlList = hhXtCzUrlList;
	}

	/** default constructor */
	public UsUser() {
	}

	@Column(name = "TEXT", length = 64)
	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		textpinyin = PinYinUtil.getPinYin(text);
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
	public String getOrgText() {
		if (getOrg() != null) {
			return getOrg().getText();
		}
		return "";
	}

	@Transient
	@Override
	public String getJobText() {
		if (getJob() != null) {
			return getJob().getText();
		}
		return "";
	}

	@Transient
	@Override
	public String getDeptText() {
		if (getDept() != null) {
			return getDept().getText();
		}
		return "";
	}

	@Column(name = "VZMBJ", length = 256)
	public String getVzmbj() {
		return vzmbj;
	}

	// @Transient
	// public Map<String, Organization> getOrganization() {
	// return organization;
	// }
	//
	// public void setOrganization(Map<String, Organization> organization) {
	// this.organization = organization;
	// }

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

	@Column(name = "THEME_", length = 36)
	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	@Column(name = "DESKTOP_TYPE_", length = 36)
	public String getDesktopType() {
		return desktopType;
	}

	public void setDesktopType(String desktopType) {
		this.desktopType = desktopType;
	}

	@Column(name = "JOB_ID", length = 36)
	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	@Column(name = "DEPT_ID", length = 36)
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	@Column(name = "ORG_ID", length = 36)
	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	@Transient
	public Map<String, SysOper> getHhXtCzMap() {
		return hhXtCzMap;
	}

	public void setHhXtCzMap(Map<String, SysOper> hhXtCzMap) {
		this.hhXtCzMap = hhXtCzMap;
	}

	@Column(name = "TEXT_PINYIN")
	public String getTextpinyin() {
		return textpinyin;
	}

	public void setTextpinyin(String textpinyin) {
		this.textpinyin = textpinyin;
	}

}