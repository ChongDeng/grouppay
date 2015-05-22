package com.amaker.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.amaker.dao.EventsDao;
import com.amaker.entity.Event;
import com.amaker.util.DBUtil;


public class EventsDaoImpl implements EventsDao {

	public List getEvents(String username){
		// 查询SQL语句
		//String sql = " select friendname from friendtbl where username =?";
		String sql = " select  E.name, E.description, E.cost, E.group_name, E.expense_strategy, E.pending_state, E.sponsor_name "
				     + "from eventtbl as E, groupusertbl as GU "
				     + "where E.group_name = GU.groupname and GU.username = ?";
		/*select  E.name, E.description, E.cost, E.group_name, E.expense_strategy, E.pending_state, E.sponsor_name 
		from eventtbl as E, groupusertbl as GU
		where E.group_name = GU.groupname and GU.username = 'dc'
		*/
		
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
				String description = rs.getString(2);		
				double cost = rs.getDouble(3);
				String group_name = rs.getString(4);
				String expense_strategy = rs.getString(5);
				int pending_state = rs.getInt(6);
				String sponsor_name = rs.getString(7);
				
				Event e = new Event();
				e.setName(name);
				e.setDescription(description);
				e.setCost(cost);
				e.setGroupName(group_name);
				e.setExpenseStrategy(expense_strategy);
				e.setPendingState(pending_state);
				e.setSponsorName(sponsor_name);
				list.add(e);
			}		
			return list;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			util.closeConn(conn);
		}
		return null;
	}
	

}
