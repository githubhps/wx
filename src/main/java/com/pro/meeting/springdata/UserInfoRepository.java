package com.pro.meeting.springdata;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.pro.meeting.bean.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo,String>{
	//根据OPENID得到对应实体对象 
		public UserInfo getByOpenid(String openid);
		
		@Modifying
		@Query("update Users set nickname=?1, sex=?2, city=?3, country=?4, province=?5, language=?6, headimgurl=?7, subscribe_time=?8, subscribe=?9 where openid=?10")
		public void updateUserInfo(String nickname,String sex,String city,String country,String province,String language,String headimgurl,String subscribe_time,String subscribe,String openid);
}
