package com.amaker.dao;

import java.util.List;
import java.util.List;

import com.amaker.entity.Group;

public interface GroupsDao {
	public List getGroups(String username);
	public List getGroupMembers(String groupname);
	public boolean createGroup(Group g);
	public String GetGroupFund(String groupname);
}
