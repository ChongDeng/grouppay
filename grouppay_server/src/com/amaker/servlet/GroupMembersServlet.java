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
	// ���췽��
	public GroupMembersServlet() {
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
		GroupsDao dao = new GroupsDaoImpl();	
		
		// ��ÿͻ����������
	    String groupname = request.getParameter("groupname");		
		List list = dao.getGroupMembers(groupname);
		
		// ƴXML��ʽ����
				out.println("<?xml version='1.0' encoding='UTF-8'?>");
				// ���ڵ�
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
	// ��ӦPost����
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
	}
	// ��ʼ������
	public void init() throws ServletException {
	}
}
