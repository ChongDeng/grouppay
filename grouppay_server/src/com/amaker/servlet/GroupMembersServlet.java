package com.amaker.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import com.amaker.dao.GroupsDao;


import com.amaker.dao.impl.GroupsDaoImpl;
import com.amaker.entity.Group;
import com.amaker.entity.User;



public class GroupMembersServlet extends HttpServlet {
	// 构造方法
	public GroupMembersServlet() {
		super();
	}
	// 销毁方法
	public void destroy() {
		super.destroy();
	}
	// 响应Get请求
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		
		// 实例化dao
		GroupsDao dao = new GroupsDaoImpl();	
		
		// 获得客户端请求参数
	    String groupname = request.getParameter("groupname");		
		List list = dao.getGroupMembers(groupname);
		
		// 拼XML格式数据
				out.println("<?xml version='1.0' encoding='UTF-8'?>");
				// 根节点
				out.println("<member>");
					for (int i = 0; i <list.size(); i++) {
						User u = (User)list.get(i);
						    out.print("<name>");					
								out.print(u.getUsername());					
							out.println("</name>");				
					}
				out.println("</member>");
				out.flush();
				out.close();
	}
	// 响应Post请求
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
	}
	// 初始化方法
	public void init() throws ServletException {
	}
}
