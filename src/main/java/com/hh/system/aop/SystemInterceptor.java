package com.hh.system.aop;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.hh.system.service.impl.SaveErrorThread;
import com.hh.system.util.ExceptionUtil;
import com.hh.system.util.ThreadUtil;
import com.hh.usersystem.IUser;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

@SuppressWarnings("serial")
public class SystemInterceptor implements Interceptor {
	private static final Logger logger = Logger.getLogger(SystemInterceptor.class);

	public void destroy() {
	}

	public void init() {
	}

	public String intercept(ActionInvocation arg0) throws Exception {
		String result = null;
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		try {
			long startTime = System.currentTimeMillis();
			result = arg0.invoke();
			String requestUri = request.getRequestURI().replace(request.getContextPath() + "/", "");
			logger.debug("【" + arg0.getAction().getClass().getName() + ";uri=" + requestUri + "】耗时："
					+ (System.currentTimeMillis() - startTime) + "毫秒  ");
			// if (!( arg0.getAction() instanceof IFileAction)) {
			// response.getWriter().print("{'success':true}");
			// }
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(500);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("success", false);
			resultMap.put("titleMsg", "错误");
			resultMap.put("msg",
					"异常：" + e.getClass().getName() + "：" + e.getMessage() + "<br/>" + ExceptionUtil.getMessage(e));
			IUser userObject = (IUser) ActionContext.getContext().getSession().get("loginuser");
			String userid = "";
			String orgid = "";
			if (userObject != null) {
				userid = userObject.getId();
				orgid = userObject.getJobId();
			}
			// 线程池
			ThreadUtil.getThreadPool().execute(new SaveErrorThread(e, userid, orgid));
			response.getWriter().print(new Gson().toJson(resultMap));
		}
		return result;
	}

}
