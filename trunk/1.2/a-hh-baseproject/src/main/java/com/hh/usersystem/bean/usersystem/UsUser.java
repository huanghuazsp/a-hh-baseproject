package com.hh.usersystem.bean.usersystem;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.hh.hibernate.dao.inf.Order;
import com.hh.hibernate.util.base.BaseEntity;
import com.hh.system.util.Convert;
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
public class UsUser extends BaseEntity implements IUser {
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

	private List<SysMenu> menuList = new ArrayList<SysMenu>();// 菜单
	private List<UsRole> roleList = new ArrayList<UsRole>();// 角色
	private List<SysMenu> desktopQuickList = new ArrayList<SysMenu>();// 桌面快捷方式
	private Map<String, SysOper> operMap = new HashMap<String, SysOper>();// 用户的操作权限
	
	private List<String> operUrlList = new ArrayList<String>();//url权限控制
	private List<String> operPageTextList = new ArrayList<String>();//页面文字权限控制

	private Map<String, List<String>> operPageTextMap = new HashMap<String, List<String>>();//页面文字权限控制

	private String roleIds;// 角色ID
	private String roleIdsText;// 角色ID

	private String vzmbj = StaticProperties.HHXT_USERSYSTEM_ZMBJ;
	private int pageSize = 15;
	private String theme;

	private UsOrganization dept;// 部门
	private UsOrganization org;// 机构
	private UsOrganization job;

	private String jobId;
	private String deptId;
	private String orgId;

	private String jobIdText;
	private String deptIdText;
	private String orgIdText;
	
	private String usGroupIds;
	private String sysGroupIds;
	
	private String propertys;
	
	private Map<String, Object> propertysMap;
	
	private String mobileHead;

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
	public List<SysMenu> getDesktopQuickList() {
		return desktopQuickList;
	}

	public void setDesktopQuickList(List<SysMenu> desktopQuickList) {
		this.desktopQuickList = desktopQuickList;
	}

	@Transient
	public List<SysMenu> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<SysMenu> menuList) {
		this.menuList = menuList;
	}

	@Transient
	public List<UsRole> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<UsRole> roleList) {
		this.roleList = roleList;
	}

	@Transient
	public List<String> getOperUrlList() {
		return operUrlList;
	}

	public void setOperUrlList(List<String> operUrlList) {
		this.operUrlList = operUrlList;
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

	@Column(name = "HEADPIC")
	public String getHeadpic() {
		return headpic;
	}

	public void setHeadpic(String headpic) {
		this.headpic = headpic;
	}

	@Transient
	public List<String> getOperPageTextList() {
		return operPageTextList;
	}

	public void setOperPageTextList(List<String> operPageTextList) {
		this.operPageTextList = operPageTextList;
	}

	@Transient
	public Map<String, List<String>> getOperPageTextMap() {
		return operPageTextMap;
	}

	public void setOperPageTextMap(Map<String, List<String>> operPageTextMap) {
		this.operPageTextMap = operPageTextMap;
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

	@Column(name = "JOB_ID_", length = 36)
	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	@Column(name = "DEPT_ID_", length = 36)
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	@Column(name = "ORG_ID_", length = 36)
	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	@Transient
	public Map<String, SysOper> getOperMap() {
		return operMap;
	}

	public void setOperMap(Map<String, SysOper> operMap) {
		this.operMap = operMap;
	}

	@Column(name = "TEXT_PINYIN")
	public String getTextpinyin() {
		return textpinyin;
	}

	public void setTextpinyin(String textpinyin) {
		this.textpinyin = textpinyin;
	}

	@Lob
	@Column(name = "ROLE_IDS")
	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	@Column(name = "JOB_ID_TEXT", length = 64)
	public String getJobIdText() {
		return jobIdText;
	}

	public void setJobIdText(String jobIdText) {
		this.jobIdText = jobIdText;
	}

	@Column(name = "DEPT_ID_TEXT", length = 64)
	public String getDeptIdText() {
		return deptIdText;
	}

	public void setDeptIdText(String deptIdText) {
		this.deptIdText = deptIdText;
	}

	@Column(name = "ORG_ID_TEXT", length = 64)
	public String getOrgIdText() {
		return orgIdText;
	}

	public void setOrgIdText(String orgIdText) {
		this.orgIdText = orgIdText;
	}

	private int state;
	@Column(name="STATE_")
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
	@Transient
	public String getUsGroupIds() {
		return usGroupIds;
	}

	public void setUsGroupIds(String usGroupIds) {
		this.usGroupIds = usGroupIds;
	}
	@Transient
	public String getSysGroupIds() {
		return sysGroupIds;
	}

	public void setSysGroupIds(String sysGroupIds) {
		this.sysGroupIds = sysGroupIds;
	}

	@Lob
	@Column(name="PROPERTYS")
	public String getPropertys() {
		return propertys;
	}

	public void setPropertys(String propertys) {
		this.propertys = propertys;
	}

	@Transient
	public Map<String, Object> getPropertysMap() {
		return propertysMap;
	}

	public void setPropertysMap(Map<String, Object> propertysMap) {
		this.propertysMap = propertysMap;
	}

	@Column(name = "ROLE_IDS_TEXT", length = 64)
	public String getRoleIdsText() {
		return roleIdsText;
	}

	public void setRoleIdsText(String roleIdsText) {
		this.roleIdsText = roleIdsText;
	}

	@Transient
	public String getMobileHead() {
		return mobileHead;
	}

	public void setMobileHead(String mobileHead) {
		this.mobileHead = mobileHead;
	}
	
	public boolean hasRoleId(String role) {
		for (UsRole roleo : roleList) {
			if (Convert.toString(role).equals(roleo.getId()) || Convert.toString(role).equals(roleo.getJssx())) {
				return true;
			}
		}
		return false;
	}

	
}