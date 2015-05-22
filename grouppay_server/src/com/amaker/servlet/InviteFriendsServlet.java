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


public class InviteFriendsServlet extends HttpServlet {
	// 构造方法
	public InviteFriendsServlet() {
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
	
		String group_name = request.getParameter("group_name");	
		String fiendlist = request.getParameter("fiendlist");	
		
		// 实例化dao
		FriendsDao dao = new FriendsDaoImpl();	
		int result = dao.inviteFriends2Group(group_name, fiendlist);
		
		if(result != -1){
			// 响应客户端内容，登录成功
			//out.print("0");
			out.print("0");		
			System.out.println("yes");
		}else{
			out.print("-1");		
			System.out.println("no");
		}
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
