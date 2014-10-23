package com.hh.usersystem.servlet;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JavaScriptServlet implements Filter {

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)arg0;
		HttpServletResponse response = (HttpServletResponse)arg1;
		String fileUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		if (!contextPath.endsWith("/")) {
			contextPath = contextPath + "/";
		}
		// 注意：此处未考虑并发访问异常
		BufferedReader in = new BufferedReader(new InputStreamReader(getClass()
				.getClassLoader().getResourceAsStream(fileUri)));
		PrintWriter out = response.getWriter();
		response.setContentType("text/joavascript; charset=UTF-8");
		String line = null;
		while ((line = in.readLine()) != null) {
			out.println(line);
		}

		in.close();
		out.close();
		
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	
}
