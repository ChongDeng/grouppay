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
	// 构造方法
	public DonateServlet() {
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
				
		// 获得客户端请求参数
	    String username = request.getParameter("username");		
	    String groupname = request.getParameter("groupname");
	    String money = request.getParameter("money");
	    
	    UserDao dao = new UserDaoImpl();
	    boolean result = dao.donate(groupname, username, money);
	    
	    if(result){			
			out.print("0");			
			System.out.println("donate success");
		}else{
			// 响应客户端内容，登录失败
			out.print("-1");
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
