package com.amaker.servlet;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amaker.dao.FriendsDao;

import com.amaker.dao.impl.FriendsDaoImpl;

import com.amaker.entity.Menu;
import com.amaker.entity.User;


public class FriendServlet extends HttpServlet {
	// 构造方法
	public FriendServlet() {
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
		FriendsDao dao = new FriendsDaoImpl();	
		
		// 获得客户端请求参数
	    String username = request.getParameter("username");		
		List list = dao.getFriends(username);
		
		// 拼XML格式数据
		out.println("<?xml version='1.0' encoding='UTF-8'?>");
		// 根节点
		out.println("<friend>");
			for (int i = 0; i <list.size(); i++) {
				User u = (User)list.get(i);
				    out.print("<name>");					
						out.print(u.getUsername());					
					out.println("</name>");				
			}
		out.println("</friend>");
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
