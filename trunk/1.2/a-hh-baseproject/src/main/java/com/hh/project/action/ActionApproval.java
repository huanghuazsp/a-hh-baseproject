package com.hh.project.action;
import org.springframework.beans.factory.annotation.Autowired;
import com.hh.system.util.base.BaseServiceAction;
import com.hh.project.bean.ProjectApproval;
import com.hh.system.service.impl.BaseService;
import com.hh.project.service.impl.ProjectApprovalService;

@SuppressWarnings("serial")
public class ActionApproval extends BaseServiceAction< ProjectApproval > {
	@Autowired
	private ProjectApprovalService projectapprovalService;
	public BaseService<ProjectApproval> getService() {
		return projectapprovalService;
	}
}
 