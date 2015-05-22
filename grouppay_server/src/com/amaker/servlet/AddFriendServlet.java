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



public class AddFriendServlet extends HttpServlet {
	// ���췽��
	public AddFriendServlet() {
		super();
	}
	
	// ���ٷ���
	public void destroy() {
		super.destroy();
	}
	// ��ӦGet����
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//response.setContentType("text/xml");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		// ʵ����dao
		FriendsDao dao = new FriendsDaoImpl();	
		
		// ��ÿͻ����������
	    String username = request.getParameter("username");	
	    String friendname = request.getParameter("friendname");	
	    
		int result = dao.addFriend(username, friendname);
		//if(result != 0)
		//	out.print("1");
		if(result != 0)
		{
			    String username2 = request.getParameter("username");	
			    String friendname2 = request.getParameter("friendname");	
			    
				result = dao.addFriend(friendname2, username2);
				if(result!=0)
					out.print("1");
				else
					out.print("0");
		}
		else
			out.print("0");		
		out.flush();
		out.close();

		/*
		System.out.println("AAAresult: " + result);
		out.println("<?xml version='1.0' encoding='UTF-8'?>");
		// ���ڵ�
		out.println("<friend>");
			
				    out.print("<name>");		
				    if(result != 0)
						out.print("1");	
				    else
				    	out.print("0");
					out.println("</name>");				
			
		out.println("</friend>");
		out.flush();
		out.close();
		*/
		
		/*
		if(result != 0){
			
			out.println(1);		
			System.out.println("***1: " + result);
			
		}else{
			// ��Ӧ�ͻ������ݣ���¼ʧ��
			out.println("0");
			System.out.println("***0");
		}
		*/
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
