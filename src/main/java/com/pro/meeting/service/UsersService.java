package com.pro.meeting.service;

import java.util.List;

import javax.print.DocFlavor.STRING;

import com.pro.meeting.bean.Users;

public interface UsersService {

	public Users getUsersByWidByOpenid(String openid);
	public void updateUsersWidByEmail(String email,String wid);
	//根据邮箱来得到Users
	public Users getByEmail(String email);
	public List<Users> getBy();
	
	//添加用户
	public void add(Users users);
	
	 //更新
	public int update(Users u);
		
		//根据ID查询数据
	public Users getUid(String uid);
	
	//删除数据
	public void delect(String uid);
}
