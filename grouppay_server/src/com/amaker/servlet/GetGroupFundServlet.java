package com.amaker.servlet;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amaker.dao.GroupsDao;
import com.amaker.dao.UserDao;
import com.amaker.dao.impl.GroupsDaoImpl;
import com.amaker.dao.impl.UserDaoImpl;
import com.amaker.entity.User;

public class GetGroupFundServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		GroupsDao dao = new GroupsDaoImpl();
		// 获得客户端请求参数
		String groupname = request.getParameter("groupname");
		
		String result  = dao.GetGroupFund(groupname);
		System.out.println("fund: " + result);
		out.print(result);	
		
		out.flush();
		out.close();
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
	}
	public void init() throws ServletException {
	}
	
	public GetGroupFundServlet() {
		super();
	}

	public void destroy() {
		super.destroy();
	}

}
