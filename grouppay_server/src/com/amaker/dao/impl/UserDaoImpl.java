package com.amaker.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.amaker.entity.User;
import com.amaker.dao.UserDao;
import com.amaker.util.DBUtil;

public class UserDaoImpl implements UserDao {
	
	/**
	 * 通过用户名称和密码登录，登录成功返回User对象，登录失败返回null
	 */
	public User login(String username, String password) {

		String sql = " select username,password from UserTbl where username=? and password=? ";
		DBUtil util = new DBUtil();
		Connection conn = util.openConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, username);
			pstmt.setString(2, password);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				//int id = rs.getInt(1);
				User u = new User();
				//u.setId(id);
				u.setPassword(password);
				u.setUsername(username);
				return u;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			util.closeConn(conn);
		}
		return null;
	}
	
	public boolean register(String email, String username,String password)
	{
		    //insert into usertbl(email, username, password) values('y2gmail.com', 'y', '0')
	        String sql = "insert into usertbl(email, username, password, balance)" +
			" values(?,?,?, 0)";
	     
	        DBUtil util = new DBUtil();
			Connection conn = util.openConnection();
			try {
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, email);
				pstmt.setString(2, username);
				pstmt.setString(3, password);			
				
				if (pstmt.executeUpdate() <= 0)
					return false;			
					
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				util.closeConn(conn);
			}
			return true;
	}
	
	public boolean donate(String groupname, String username,String money)
	{
		
		
		String sql = "select * from usertbl where balance >= ? and username = ?";
		String sql2 = "update usertbl set balance = balance - ? where username = ?";
		String sql3 = "update grouptbl set fund = fund + ?  where groupname = ?"; 
		
		System.out.println("name: " +  groupname + username +  money);
		DBUtil util = new DBUtil();
		Connection conn = util.openConnection();
		try {
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, money);
			pstmt.setString(2, username);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				PreparedStatement pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, money);
				pstmt2.setString(2, username);
				if (pstmt2.executeUpdate() <= 0)
					return false;
				else
				{
					PreparedStatement pstmt3 = conn.prepareStatement(sql3);
					pstmt3.setString(1, money);
					pstmt3.setString(2, groupname);
					if (pstmt3.executeUpdate() <= 0)
						return false;				
				}
				
			}
			else 
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			util.closeConn(conn);
		}
		return true;
	}
	
	public boolean charge(String username, String money)
	{
		// update usertbl set balance = balance +100 where username = 'dc'
		String sql = "update usertbl set balance = balance + ? where username = ?";
		
		DBUtil util = new DBUtil();
		Connection conn = util.openConnection();
		try {
					
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, money);
				pstmt.setString(2, username);
				if (pstmt.executeUpdate() <= 0)
					return false;			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			util.closeConn(conn);
		}
		return true;
		
	}
	public String getBalance(String username)
	{
        String result = "0";
		
		//select balance from usertbl where username = 'dc'
		String sql = " select balance from usertbl where username = ?";
		DBUtil util = new DBUtil();
		Connection conn = util.openConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
		
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				result = String.valueOf(rs.getDouble(1));				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			util.closeConn(conn);
		}	
		
		
		return result;		
		
	}
	
	

}
