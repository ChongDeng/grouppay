package com.amaker.servlet;



import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amaker.dao.FriendsDao;
import com.amaker.dao.UserDao;
import com.amaker.dao.impl.FriendsDaoImpl;
import com.amaker.dao.impl.UserDaoImpl;
import com.amaker.entity.Menu;
import com.amaker.entity.User;


public class DonateServlet extends HttpServlet {
	// ���췽��
	public DonateServlet() {
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
				
		// ��ÿͻ����������
	    String username = request.getParameter("username");		
	    String groupname = request.getParameter("groupname");
	    String money = request.getParameter("money");
	    
	    UserDao dao = new UserDaoImpl();
	    boolean result = dao.donate(groupname, username, money);
	    
	    if(result){			
			out.print("0");			
			System.out.println("donate success");
		}else{
			// ��Ӧ�ͻ������ݣ���¼ʧ��
			out.print("-1");
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
