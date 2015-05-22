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
	// ���췽��
	public FriendServlet() {
		super();
	}
	// ���ٷ���
	public void destroy() {
		super.destroy();
	}
	// ��ӦGet����
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		
		// ʵ����dao
		FriendsDao dao = new FriendsDaoImpl();	
		
		// ��ÿͻ����������
	    String username = request.getParameter("username");		
		List list = dao.getFriends(username);
		
		// ƴXML��ʽ����
		out.println("<?xml version='1.0' encoding='UTF-8'?>");
		// ���ڵ�
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
	// ��ӦPost����
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
	}
	// ��ʼ������
	public void init() throws ServletException {
	}
}
