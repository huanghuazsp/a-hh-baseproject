package com.hh.project.bean;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Lob;
import com.hh.hibernate.util.base.*;
import com.hh.hibernate.dao.inf.Order;
import com.hh.hibernate.dao.inf.Comment;
@Comment("立项申请")
@Order
@SuppressWarnings("serial")
@Entity
@Table(name="PROJECT_APPROVAL")
public class ProjectApproval  extends BaseEntity{
	private String applyUser;
	
	@Comment("申请人")
	@Column(name="APPLY_USER", length = 36)
	public String getApplyUser() {
		return applyUser;
	}
	public void setApplyUser(String applyUser) {
		this.applyUser = applyUser;
	}
	
	private String applyUserText;
	
	@Comment("申请人名称")
	@Column(name="APPLY_USER_TEXT", length = 128)
	public String getApplyUserText() {
		return applyUserText;
	}
	public void setApplyUserText(String applyUserText) {
		this.applyUserText = applyUserText;
	}
	
	private Date applyDate;
	
	@Comment("申请时间")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="APPLY_DATE",length = 7)
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	
	private Date approvalDate;
	
	@Comment("立项时间")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="APPROVAL_DATE",length = 7)
	public Date getApprovalDate() {
		return approvalDate;
	}
	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}
	
	private String applyComment;
	
	@Comment("申请内容")
	@Lob
	@Column(name="APPLY_COMMENT")
	public String getApplyComment() {
		return applyComment;
	}
	public void setApplyComment(String applyComment) {
		this.applyComment = applyComment;
	}
	
	private String deptManager;
	
	@Comment("部门经理")
	@Column(name="DEPT_MANAGER", length = 36)
	public String getDeptManager() {
		return deptManager;
	}
	public void setDeptManager(String deptManager) {
		this.deptManager = deptManager;
	}
	
	private String deptManagerText;
	
	@Comment("部门经理")
	@Column(name="DEPT_MANAGER_TEXT", length = 128)
	public String getDeptManagerText() {
		return deptManagerText;
	}
	public void setDeptManagerText(String deptManagerText) {
		this.deptManagerText = deptManagerText;
	}
	
	private String deptManagerComment;
	
	@Comment("部门经理意见")
	@Lob
	@Column(name="DEPT_MANAGER_COMMENT")
	public String getDeptManagerComment() {
		return deptManagerComment;
	}
	public void setDeptManagerComment(String deptManagerComment) {
		this.deptManagerComment = deptManagerComment;
	}
	
	private String branchDeputyManager;
	
	@Comment("分管副总")
	@Column(name="BRANCH_DEPUTY_MANAGER", length = 36)
	public String getBranchDeputyManager() {
		return branchDeputyManager;
	}
	public void setBranchDeputyManager(String branchDeputyManager) {
		this.branchDeputyManager = branchDeputyManager;
	}
	
	private String branchDeputyManagerText;
	
	@Comment("分管副总")
	@Column(name="BRANCH_DEPUTY_MANAGER_TEXT", length = 128)
	public String getBranchDeputyManagerText() {
		return branchDeputyManagerText;
	}
	public void setBranchDeputyManagerText(String branchDeputyManagerText) {
		this.branchDeputyManagerText = branchDeputyManagerText;
	}
	
	private String branchDeputyManagerComment;
	
	@Comment("分管副总意见")
	@Lob
	@Column(name="BRANCH_DEPUTY_MANAGER_COMMENT")
	public String getBranchDeputyManagerComment() {
		return branchDeputyManagerComment;
	}
	public void setBranchDeputyManagerComment(String branchDeputyManagerComment) {
		this.branchDeputyManagerComment = branchDeputyManagerComment;
	}
	
	private String overallManager;
	
	@Comment("总经理")
	@Column(name="OVERALL_MANAGER", length = 36)
	public String getOverallManager() {
		return overallManager;
	}
	public void setOverallManager(String overallManager) {
		this.overallManager = overallManager;
	}
	
	private String overallManagerText;
	
	@Comment("总经理")
	@Column(name="OVERALL_MANAGER_TEXT", length = 128)
	public String getOverallManagerText() {
		return overallManagerText;
	}
	public void setOverallManagerText(String overallManagerText) {
		this.overallManagerText = overallManagerText;
	}
	
	private String overallManagerComment;
	
	@Comment("总经理意见")
	@Lob
	@Column(name="OVERALL_MANAGER_COMMENT")
	public String getOverallManagerComment() {
		return overallManagerComment;
	}
	public void setOverallManagerComment(String overallManagerComment) {
		this.overallManagerComment = overallManagerComment;
	}
	
	private String projectId;
	
	@Comment("项目ID")
	@Column(name="PROJECT_ID", length = 36)
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	private String projectIdText;

	@Comment("项目")
	@Column(name="PROJECT_ID_TEXT", length = 128)
	public String getProjectIdText() {
		return projectIdText;
	}
	public void setProjectIdText(String projectIdText) {
		this.projectIdText = projectIdText;
	}
	
}