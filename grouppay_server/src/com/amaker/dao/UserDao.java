package com.amaker.dao;

import com.amaker.entity.User;

public interface UserDao {
	public User login(String username,String password);
	public boolean register(String email, String username,String password);	
	public boolean donate(String groupname, String username,String money);
	public boolean charge(String username, String money);
	public String getBalance(String username);
}
