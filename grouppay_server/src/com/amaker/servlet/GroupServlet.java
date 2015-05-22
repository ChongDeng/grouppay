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



public class GroupServlet extends HttpServlet {
	// 构造方法
	public GroupServlet() {
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
	    String username = request.getParameter("username");		
		List list = dao.getGroups(username);
		
		// 拼XML格式数据
		out.println("<?xml version='1.0' encoding='UTF-8'?>");
		// 根节点
		out.println("<info>");
			for (int i = 0; i <list.size(); i++) {
				out.println("<group>");
				    Group g = (Group)list.get(i);
				    
				    out.print("<name>");					
						out.print(g.getName());					
					out.println("</name>");
					
					out.print("<description>");					
						out.print(g.getDescription());					
					out.println("</description>");
					
					out.print("<OwnerName>");					
					   out.print(g.getOwnerName());					
				    out.println("</OwnerName>");
				    
				    out.print("<TreasurerName>");					
					   out.print(g.getTreasurerName());					
				    out.println("</TreasurerName>");
				    
				    out.print("<Fund>");					
					   out.print(g.getFund());					
				    out.println("</Fund>");
				out.println("</group>");
			}
	    out.println("</info>");
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
