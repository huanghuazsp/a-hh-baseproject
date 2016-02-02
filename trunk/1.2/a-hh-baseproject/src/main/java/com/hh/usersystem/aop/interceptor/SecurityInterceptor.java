package com.hh.usersystem.aop.interceptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.hh.hibernate.dao.inf.IHibernateDAO;
import com.hh.system.util.Check;
import com.hh.system.util.Convert;
import com.hh.system.util.SysParam;
import com.hh.usersystem.bean.usersystem.SysOper;
import com.hh.usersystem.bean.usersystem.UsUser;
import com.hh.usersystem.bean.usersystem.UsOrganization;
import com.hh.usersystem.service.impl.LoginUserUtilService;
import com.hh.usersystem.service.impl.OperateService;
import com.hh.usersystem.service.impl.OrganizationService;
import com.hh.usersystem.util.steady.StaticProperties.OperationLevel;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

@SuppressWarnings("serial")
public class SecurityInterceptor implements Interceptor {
	public static List<String> all_manage_request = new ArrayList<String>();

	public static Map<String, List<String>> all_manage_page_text_map = new HashMap<String, List<String>>();

	private static int isLoadManagerRequest = 0;
	@Autowired
	private IHibernateDAO<SysOper> hibernateDAO;
	@Autowired
	private LoginUserUtilService loginUserUtilService;
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private OperateService operateService;

	public void destroy() {
	}

	public void init() {
	}

	public String intercept(ActionInvocation arg0) throws Exception {
		if (SysParam.sysParam.getPower() == 0) {
			return powerControl(arg0);
		} else {
			return arg0.invoke();
		}
	}

	private String powerControl(ActionInvocation arg0) throws Exception {
		String noSecurity = "";
		if (isLoadManagerRequest == 0) {
			operateService.initOperPower();
			isLoadManagerRequest = 1;
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		UsUser hhXtYh = loginUserUtilService.findLoginUser();
		if (hhXtYh == null && Check.isEmpty(request.getParameter("se"))) {
			if (request.getHeader("x-requested-with") == null ? false : request
					.getHeader("x-requested-with").equalsIgnoreCase(
							"XMLHttpRequest")) {
				response.addHeader("sessionstatus", "timeout");
				return "timeout";
			} else {
				// 普通请求
				return "login";
			}
		} else {
			String requestUri = request.getRequestURI().replace(
					request.getContextPath() + "/", "");
			if (all_manage_request.contains(requestUri)) {
				Map<String, SysOper> hhXtCzMap = hhXtYh.getHhXtCzMap();
				boolean isSecurity = false;
				SysOper hhXtCz = hhXtCzMap.get(requestUri);

				if (hhXtCz == null) {
					isSecurity = false;
				} else {
					Object vcreates = request.getParameter("vcreates");
					if (vcreates == null || "".equals(vcreates)) {
						isSecurity = true;
					} else {
						boolean isOper = true;
						for (String vcreate : vcreates.toString().split(",")) {
							if (!vcreate.equals(hhXtYh.getId())) {
								isOper = false;
								break;
							}
						}
						if (isOper) {
							isSecurity = true;
						} else {
							noSecurity = valiOrg(request, hhXtYh, hhXtCz);
							if (Check.isEmpty(noSecurity)) {
								isSecurity = true;
							} else {
								isSecurity = false;
							}
						}
					}
				}

				if (isSecurity) {
					return arg0.invoke();
				} else {
					response.addHeader("sessionstatus", "no_authority");
					request.setAttribute("sessionstatusMsg", "对不起，您没有权限操作此功能！"
							+ noSecurity);
					return "no_authority";
				}
			} else {
				return arg0.invoke();
			}
		}
	}

	private String valiOrg(HttpServletRequest request, UsUser hhXtYh,
			SysOper hhXtCz) {
		String noSecurity;
		String orgids = Convert.toString(request
				.getParameter("orgids"));
		String deptids = Convert.toString(request
				.getParameter("deptids"));
		String jobids = Convert.toString(request
				.getParameter("jobids"));
		if (OperationLevel.BJG.toString().equals(
				hhXtCz.getOperLevel())) {
			UsOrganization organization = hhXtYh.getOrg();
			noSecurity = findNoSecurityStr(orgids,
					organization,
					"<br/>您的操作范围是本<font color=red>机构</font>！");
		} else if (OperationLevel.BBM.toString().equals(
				hhXtCz.getOperLevel())) {
			UsOrganization organization = hhXtYh.getDept();
			noSecurity = findNoSecurityStr(deptids,
					organization,
					"<br/>您的操作范围是本<font color=red>部门</font>！");
		} else if (OperationLevel.BGW.toString().equals(
				hhXtCz.getOperLevel())) {
			UsOrganization organization = hhXtYh.getJob();
			noSecurity = findNoSecurityStr(jobids,
					organization,
					"<br/>您的操作范围是本<font color=red>岗位</font>！");
		} else {
			noSecurity = "<br/>您的操作范围是本<font color=red>人</font>！";
		}
		return noSecurity;
	}

	private String findNoSecurityStr(String deptids, UsOrganization organization,
			String str) {
		String noSecurity = null;
		for (String orgid : deptids.split(",")) {
			UsOrganization dataOrganization = organizationService
					.findObjectById(orgid);
			if (dataOrganization.getCode_().startsWith(organization.getCode_())) {
				noSecurity = str + "<br/>您操作的数据时属于<font color=red>"
						+ dataOrganization.getText() + "</font>！";
				break;
			}
		}
		return noSecurity;
	}

	public static List<String> getAll_manage_request() {
		return all_manage_request;
	}

	public static void setAll_manage_request(List<String> all_manage_request) {
		SecurityInterceptor.all_manage_request = all_manage_request;
	}
}
