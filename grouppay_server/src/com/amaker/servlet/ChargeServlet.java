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

public class ChargeServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		UserDao dao = new UserDaoImpl();
		// 获得客户端请求参数
		String username = request.getParameter("username");
		String money = request.getParameter("money");
		
		Boolean result = dao.charge(username, money);
		
		if(result){			
			out.print(0);			
			System.out.println("yes");
		}else{
			// 响应客户端内容，登录失败
			out.print("-1");
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
	
	public ChargeServlet() {
		super();
	}

	public void destroy() {
		super.destroy();
	}

}

