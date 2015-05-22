package com.servlet.login;
import java.io.*;
import javax.servlet.http.*;
import javax.servlet.*;

public class LoginAction extends HttpServlet {
     private static final long serialVersionUID = 1L;

     public LoginAction()
     {
    	 super();
     } 

     protected void doGet(HttpServletRequest req, HttpServletResponse res)
             throws ServletException, IOException {

        res.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("utf-8");
        res.setCharacterEncoding("utf-8");

        PrintWriter out = res.getWriter(); 
        out.println("Hello, Brave new World!"); 
        out.close();
     }

     protected void doPost(HttpServletRequest req, HttpServletResponse res)
             throws ServletException, IOException {

         if (null == req)
         {
             return;
         }

         res.setContentType("text/html;charset=utf-8");
         req.setCharacterEncoding("utf-8");
         res.setCharacterEncoding("utf-8");        

         PrintWriter out = res.getWriter();
         String username = req.getParameter("user_name");
         String password = req.getParameter("password");
         if (username.equals("admin"))
         {
             if (password.equals("123"))
             {
                 out.println("0");        ///< 正确
             }
             else
             {
                 out.println("2");        ///< 密码错误
             }
         }
         else
         {
             out.println("1");            ///< 用户名错误
         }
         out.flush();
         out.close();
     }
 }
