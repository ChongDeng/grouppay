package com.amaker.dao;

import java.util.List;

public interface FriendsDao {
	public List getFriends(String username);
	public int addFriend(String username, String friendname);
	public int inviteFriends2Group(String group_name, String friend_list);
}
