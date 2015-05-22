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
	// ���췽��
	public GroupServlet() {
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
	    String username = request.getParameter("username");		
		List list = dao.getGroups(username);
		
		// ƴXML��ʽ����
		out.println("<?xml version='1.0' encoding='UTF-8'?>");
		// ���ڵ�
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
	// ��ӦPost����
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
	}
	// ��ʼ������
	public void init() throws ServletException {
	}
}
