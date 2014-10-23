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
import com.hh.system.util.SysParam;
import com.hh.usersystem.bean.usersystem.HhXtCz;
import com.hh.usersystem.bean.usersystem.HhXtYh;
import com.hh.usersystem.bean.usersystem.Organization;
import com.hh.usersystem.service.impl.LoginUserUtilService;
import com.hh.usersystem.service.impl.OperateService;
import com.hh.usersystem.util.steady.StaticProperties.OperationLevel;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

@SuppressWarnings("serial")
public class SecurityInterceptor implements Interceptor {
	public static List<String> all_manage_request = new ArrayList<String>();
	
	public static  Map<String, List<String>> all_manage_page_text_map = new HashMap<String, List<String>>();
	
	private static int isLoadManagerRequest=0;
	@Autowired
	private IHibernateDAO<HhXtCz> hibernateDAO;
	@Autowired
	private LoginUserUtilService loginUserUtilService;
	@Autowired
	private IHibernateDAO<Organization> orgdao;
	@Autowired
	private OperateService operateService;

	public void destroy() {
	}

	public void init() {
	}

	public String intercept(ActionInvocation arg0) throws Exception {
		if (SysParam.hhSysParam.getPower() == 0) {
			return powerControl(arg0);
		} else {
			return arg0.invoke();
		}
	}

	private String powerControl(ActionInvocation arg0) throws Exception {
		String noSecurity = "";

		if (isLoadManagerRequest==0) {
			operateService.initOperPower();
			isLoadManagerRequest=1;
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		if (ActionContext.getContext().getSession().get("loginuser") == null) {

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
				HhXtYh hhXtYh = loginUserUtilService.findLoginUser();
				List<HhXtCz> hhXtCzList = hhXtYh.getHhXtCzList();
				boolean isSecurity = false;
				for (HhXtCz hhXtCz : hhXtCzList) {
					if (requestUri.equals(hhXtCz.getVurl())) {
						Object vcreates = request.getParameter("vcreates");
						if (vcreates == null || "".equals(vcreates)) {
						} else {
							boolean isOper = true;
							for (String vcreate : vcreates.toString()
									.split(",")) {
								if (!vcreate.equals(loginUserUtilService
										.findLoginUser().getId())) {
									isOper = false;
									break;
								}
							}
							if (isOper) {
								isSecurity = true;
								break;
							}
						}

						Object vorgids = request.getParameter("orgids");
						if (vorgids == null || "".equals(vorgids)) {
							isSecurity = true;
							break;
						} else {
							Organization organization = loginUserUtilService
									.findLoginUserOrg();
							if (organization != null) {
								if (OperationLevel.BBM.toString().equals(
										hhXtCz.getOperLevel())) {
									if (organization.getBm() != null) {
										organization = organization.getBm();
									}
									noSecurity = "<br/>您的操作范围是本<font color=red>部门</font>！";
								} else if (OperationLevel.BJG.toString()
										.equals(hhXtCz.getOperLevel())) {
									if (organization.getJg() == null) {
										if (organization.getBm() != null) {
											organization = organization.getBm();
										}
									} else {
										organization = organization.getJg();
									}
									noSecurity = "<br/>您的操作范围是本<font color=red>机构</font>！";
								} else if (OperationLevel.BJT.toString()
										.equals(hhXtCz.getOperLevel())) {
									if (organization.getJt() == null) {
										if (organization.getJg() == null) {
											if (organization.getBm() != null) {
												organization = organization
														.getBm();
											}
										} else {
											organization = organization.getJg();
										}
									} else {
										organization = organization.getJt();
									}
									noSecurity = "<br/>您的操作范围是本<font color=red>集团</font>！";
								} else if (OperationLevel.BGW.toString()
										.equals(hhXtCz.getOperLevel())) {
									// organization = loginUserUtilService
									// .findLoginUserOrg();
									noSecurity = "<br/>您的操作范围是本<font color=red>岗位</font>！";
								} else {
									noSecurity = "<br/>您的操作范围是本<font color=red>人</font>！";
									isSecurity = false;
									break;
								}

								boolean isOper = true;
								for (String orgid : vorgids.toString().split(
										",")) {
									Organization dataOrganization = orgdao
											.findEntityByPK(Organization.class,
													orgid);
									if (dataOrganization.getCode_().indexOf(
											organization.getCode_()) != 0) {
										noSecurity += "<br/>您操作的数据时属于<font color=red>"
												+ dataOrganization.getText()
												+ "</font>！";
										isOper = false;
										break;
									}
								}
								if (isOper) {
									isSecurity = true;
									break;
								} else {
									isSecurity = false;
									break;
								}

							} else {
								if (OperationLevel.BBM.toString().equals(
										hhXtCz.getOperLevel())) {
									noSecurity = "<br/>您的操作范围是本<font color=red>部门</font>！";
								} else if (OperationLevel.BJG.toString()
										.equals(hhXtCz.getOperLevel())) {
									noSecurity = "<br/>您的操作范围是本<font color=red>机构</font>！";
								} else if (OperationLevel.BJT.toString()
										.equals(hhXtCz.getOperLevel())) {
									noSecurity = "<br/>您的操作范围是本<font color=red>集团</font>！";
								} else if (OperationLevel.BGW.toString()
										.equals(hhXtCz.getOperLevel())) {
									noSecurity = "<br/>您的操作范围是本<font color=red>岗位</font>！";
								} else {
									noSecurity = "<br/>您的操作范围是本<font color=red>人</font>！";
								}
								isSecurity = false;
								break;
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


	public static List<String> getAll_manage_request() {
		return all_manage_request;
	}

	public static void setAll_manage_request(List<String> all_manage_request) {
		SecurityInterceptor.all_manage_request = all_manage_request;
	}
}
