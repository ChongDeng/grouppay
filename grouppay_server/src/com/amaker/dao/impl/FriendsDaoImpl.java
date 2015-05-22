package com.amaker.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.amaker.dao.FriendsDao;
import com.amaker.entity.User;
import com.amaker.util.DBUtil;


public class FriendsDaoImpl implements FriendsDao {
	// 获得菜单列表
	public List getFriends(String username){
		// 查询SQL语句
		String sql = " select friendname from friendtbl where username =?";
		//select friendname from friendtbl where username = 'dc'
		// 数据库连接工具类
		DBUtil util = new DBUtil();
		// 获得连接
		Connection conn = util.openConnection();
		try {
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);			
			ResultSet rs = pstmt.executeQuery();
			
			List list = new ArrayList();
			while (rs.next()) {
				String name = rs.getString(1);
				User u = new User();
				u.setUsername(name);
				list.add(u);
			}		
			return list;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			util.closeConn(conn);
		}
		return null;
	}
	
	public int addFriend(String username, String friendname)
	{
		// 查询SQL语句
				//String sql = " select friendname from friendtbl where username =?";
				/*
				select * from usertbl where username ='dc'
				insert into friendtbl(username, friendname) values('dc', 'lilei')
				*/
		        int rowCount = 0;
		        String sql = " select username from usertbl where username =?";
		        String sql2 = "insert into friendtbl(username, friendname) values(?, ?)";				  
				// 数据库连接工具类
				DBUtil util = new DBUtil();
				// 获得连接
				Connection conn = util.openConnection();
				try {
					
					PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, friendname);			
					ResultSet rs = pstmt.executeQuery();
					rs.last();   
					rowCount = rs.getRow(); 
					if(rowCount != 0)
					{
						PreparedStatement pstmt2 = conn.prepareStatement(sql2);
						pstmt2.setString(1, username);	
						pstmt2.setString(2, friendname);	
						if (pstmt2.executeUpdate() > 0)
							System.out.println("yes");
						else
							System.out.println("no");
						
					}		
                  
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					util.closeConn(conn);
				}
				System.out.println("rowCount: " + rowCount); 
	           return rowCount;
	}
	
	public int inviteFriends2Group(String group_name, String friend_list)
	{
				
		int rowCount = 0;
		String sql = " select * from groupusertbl where groupname =? and username =?";
		String sql2 = "insert into groupusertbl(groupname, username)  values(?, ?)";
		//select * from groupusertbl where groupname ='sjsu' and username = 'dc'
		//insert into groupusertbl(groupname, username)  values('sjsu', 'dh')
		String FriendName[] = friend_list.split(";");
		System.out.println("size: " + FriendName.length);
		// 数据库连接工具类
		DBUtil util = new DBUtil();
		// 获得连接
		Connection conn = util.openConnection();
		try {
			
			
			for(String name: FriendName)
			{
				
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, group_name);
				pstmt.setString(2, name);		
				ResultSet rs = pstmt.executeQuery();
				rs.last();   
				rowCount = rs.getRow(); 
				if(rowCount == 0)
				{				
					PreparedStatement pstmt2 = conn.prepareStatement(sql2);
					pstmt2.setString(1, group_name);
					pstmt2.setString(2, name);		
					if (pstmt2.executeUpdate() <= 0)
						return -1;				
				}	
			}
			
          
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			util.closeConn(conn);
		}	
		
		return 0;
	}

}
