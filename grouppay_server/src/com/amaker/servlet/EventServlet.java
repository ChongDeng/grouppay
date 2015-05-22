package com.amaker.servlet;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amaker.dao.EventsDao;
import com.amaker.dao.impl.EventsDaoImpl;
import com.amaker.entity.Event;



public class EventServlet extends HttpServlet {
	// ���췽��
	public EventServlet() {
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
		EventsDao dao = new EventsDaoImpl();	
		
		// ��ÿͻ����������
	    String username = request.getParameter("username");		
		List list = dao.getEvents(username);
		
		// ƴXML��ʽ����
		out.println("<?xml version='1.0' encoding='UTF-8'?>");
		// ���ڵ�
		out.println("<info>");
			for (int i = 0; i <list.size(); i++) {
				out.println("<Event>");
				Event e = (Event)list.get(i);
				    out.println("<name>");					
						out.print(e.getName());					
					out.println("</name>");	
					
					out.println("<description>");					
						out.print(e.getDescription());					
					out.println("</description>");	
					
					out.println("<cost>");					
					    out.print(e.getCost());					
				    out.println("</cost>");	
				    
				    out.println("<group_name>");					
				    	out.print(e.getGroupName());					
				    out.println("</group_name>");
				    
				    out.println("<expense_strategy>");					
			    	    out.print(e.getExpenseStrategy());					
			        out.println("</expense_strategy>");
			        
			        out.println("<pending_state>");					
		    	    	out.print(e.getPendingState());					
		            out.println("</pending_state>");
		            
		            out.println("<sponsor_name>");					
	    	    		out.print(e.getSponsorName());					
	                out.println("</sponsor_name>");
	            out.println("</Event>");
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
