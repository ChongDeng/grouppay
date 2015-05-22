package com.amaker.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amaker.dao.UserDao;
import com.amaker.dao.impl.UserDaoImpl;
import com.amaker.entity.User;

public class LoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		UserDao dao = new UserDaoImpl();
		// ��ÿͻ����������
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		User u = dao.login(username, password);
		if(u!=null){
			// ��Ӧ�ͻ������ݣ���¼�ɹ�
			//out.print("1");
			out.print(username);			
			System.out.println("yes");
		}else{
			// ��Ӧ�ͻ������ݣ���¼ʧ��
			out.print("0");
		}
		out.flush();
		out.close();
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
	}
	public void init() throws ServletException {
	}
	
	public LoginServlet() {
		super();
	}

	public void destroy() {
		super.destroy();
	}

}
