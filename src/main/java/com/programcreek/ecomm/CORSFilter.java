package com.programcreek.ecomm;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CORSFilter  implements Filter  {


	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
//		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletRequest request = new RequestWrapper((HttpServletRequest) servletRequest);
		Exception ex = new Exception();

		System.out.println("Request" + servletRequest.getParameter("data"));
		System.out.println("Request" + request);
//		HttpServletResponse resp = (HttpServletResponse) servletResponse;
		ResponseWrapper resp = new ResponseWrapper((HttpServletResponse) servletResponse);
		int status = HttpServletResponse.SC_OK;
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Access-Control-Allow-Credentials", "false");
		resp.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
		resp.setHeader("Access-Control-Allow-Headers",
				"Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Accept, Access-Control-Request-Method, Access-Control-Request-Headers,Authorization, async,method,processData,mimeType,contentType,headers,crossDomain,processing,content,xhrFields,user-type,X-User-Id");
		resp.setHeader("Access-Control-Expose-Headers",
				"Origin, Access-Control-Request-Method, Access-Control-Allow-Origin, Access-Control-Allow-Credentials");
		resp.setHeader("Access-Control-Max-Age", "4000");
		// Just ACCEPT and REPLY OK if OPTIONS

	
		long before = System.currentTimeMillis();

	
//		int statusCode = verifyToken(request);
//		System.out.println("statusCode12 : " + statusCode);
//		if (statusCode != HttpServletResponse.SC_OK) {
//			resp.setStatus(statusCode);
//			return;
//		}
		try {
			chain.doFilter(request, resp);
		} catch (Exception e) {
			status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
			ex = e;
		}
		
		if (status == HttpServletResponse.SC_INTERNAL_SERVER_ERROR) {
			throw new ServletException(ex.getMessage(), ex.getCause());

		}
	}
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
