package com.amaker.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;






import com.amaker.dao.GroupsDao;
import com.amaker.entity.Group;
import com.amaker.entity.User;
import com.amaker.util.DBUtil;


public class GroupsDaoImpl implements GroupsDao {
	// 获得菜单列表
	public List getGroups(String username){
		// 查询SQL语句
		//String sql = " select friendname from friendtbl where username =?";
		String sql = " select grouptbl.groupname, description,  ownername, treasurername, fund from grouptbl, groupusertbl"
				+ " where username = ?  and grouptbl.groupname  = groupusertbl.groupname";		

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
				String groupname = rs.getString(1);
				String description = rs.getString(2);
				String ownername = rs.getString(3);
				String treasurername = rs.getString(4);
				double fund = rs.getDouble(5);	
				
				Group g = new Group();
				g.setName(groupname);
				g.setDescription(description);
				g.setOwnerName(ownername);
				g.setTreasurerName(treasurername);
				g.setFund(fund);
				
				list.add(g);
			}		
			return list;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			util.closeConn(conn);
		}
		return null;
	}
	
	public List getGroupMembers(String groupname)
	{
		// 查询SQL语句				
		        //select username from groupusertbl where groupname = 'sjsu'
				String sql = "select username from groupusertbl where groupname =?"; 	

				// 数据库连接工具类
				DBUtil util = new DBUtil();
				// 获得连接
				Connection conn = util.openConnection();
				try {
					
					PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, groupname);			
					ResultSet rs = pstmt.executeQuery();
					
					List list = new ArrayList();
					while (rs.next()) {
						String username = rs.getString(1);
						
						User u = new User();
						u.setUsername(username);						
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
	
	public boolean createGroup(Group g)
	{		
		String sql = "insert into grouptbl(groupname, ownername, description, treasurername, fund)" +
				" values(?,?,?,?,0)";
		String sql2 = "insert into groupusertbl(groupname, username)" +
				" values(?,?)";
		//insert into grouptbl(groupname, ownername, description, treasurername) values('tg', 'to', 'td', 'tt')
		//insert into groupusertbl(groupname, username) values('tg', 'tu')
		
		DBUtil util = new DBUtil();
		Connection conn = util.openConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, g.getName());
			pstmt.setString(2, g.getOwnerName());
			pstmt.setString(3, g.getDescription());
			pstmt.setString(4, g.getTreasurerName());			
			
			if (pstmt.executeUpdate() <= 0)
				return false;
			else
			{
				PreparedStatement pstmt2 = conn.prepareStatement(sql2);
				String MemberList = g.getMemberList();
				String MemberName[] = MemberList.split(";");
				for(String name : MemberName)
				{
					pstmt2.setString(1, g.getName());
					pstmt2.setString(2, name);	
					if (pstmt2.executeUpdate() <= 0)
						return false;
				}				
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			util.closeConn(conn);
		}
		return true;
	}
	
	public String GetGroupFund(String groupname)
	{
		String result = "0";
		
		//select fund from grouptbl where groupname = '14'
		String sql = " select fund from grouptbl where groupname = ?";
		DBUtil util = new DBUtil();
		Connection conn = util.openConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, groupname);
		
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
