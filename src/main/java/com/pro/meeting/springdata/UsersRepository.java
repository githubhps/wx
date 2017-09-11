package com.pro.meeting.springdata;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pro.meeting.bean.Users;

public interface UsersRepository extends JpaRepository<Users,String>  {
	/*
	 * 根据OPENID，查询Users中是否存在数据
	   -- 根据openid 得到 wei_userinfo wid
	   -- 根据wid 到Users 查询是否存在
	  使用子查询：
	  from Users where wid=(select wid from UserInfo where openid=?1)
	 */
	@Query("from Users where wid=(select id from UserInfo where openid=?1) ")
	public Users getUsersByWidByOpenid(String openid);
	
	// 根据email修改 wid
	@Modifying
	@Query(" update Users set wid=:wid where email=:email ")
	public void updateUsersWidByEmail(@Param("email")String email,@Param("wid")String wid);
	
	//根据邮箱来得到Users
	public Users getByEmail(String email);
	
	//查询所有用户
	public List<Users> getBy();
	
	@Modifying
	@Query("update Users set uname=?1 ,city=?2 ,telphone=?3 where uid=?4")
	public int update(String uname,String city,String telphone,String uid);
	
	
	
}
