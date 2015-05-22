package com.amaker.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amaker.dao.FriendsDao;
import com.amaker.dao.GroupsDao;



import com.amaker.dao.impl.FriendsDaoImpl;
import com.amaker.dao.impl.GroupsDaoImpl;

import com.amaker.entity.Group;
import com.amaker.entity.User;




public class CreateGroupServlet extends HttpServlet {
	// ���췽��
	public CreateGroupServlet() {
		super();
	}
	
	// ���ٷ���
	public void destroy() {
		super.destroy();
	}
	// ��ӦGet����
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
				
		// ����������
		String group_name = request.getParameter("group_name");
		String owner_name = request.getParameter("owner_name");
		String description = request.getParameter("description");
		String treasurerName = request.getParameter("treasurerName");
		String memberlist = request.getParameter("memberlist");

	
		// ��װ��ʵ������
		Group g = new Group();
		g.setName(group_name);
		g.setOwnerName(owner_name);
		g.setDescription(description);
		g.setTreasurerName(treasurerName);
		g.setMemberList(memberlist);


		GroupsDao dao = new GroupsDaoImpl();
		if(dao.createGroup(g))
		{
			out.print("0");	
		}
		else
			out.print("-1");	

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
