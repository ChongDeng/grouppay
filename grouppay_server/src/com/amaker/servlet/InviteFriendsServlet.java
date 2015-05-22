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
	// ���췽��
	public InviteFriendsServlet() {
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
	
		String group_name = request.getParameter("group_name");	
		String fiendlist = request.getParameter("fiendlist");	
		
		// ʵ����dao
		FriendsDao dao = new FriendsDaoImpl();	
		int result = dao.inviteFriends2Group(group_name, fiendlist);
		
		if(result != -1){
			// ��Ӧ�ͻ������ݣ���¼�ɹ�
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
	
	// ��ӦPost����
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
	}
	// ��ʼ������
	public void init() throws ServletException {
	}
}
