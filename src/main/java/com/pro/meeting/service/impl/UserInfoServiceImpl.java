package com.pro.meeting.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pro.meeting.bean.UserInfo;
import com.pro.meeting.springdata.UserInfoRepository;

@Service
public class UserInfoServiceImpl {
	@Autowired
	UserInfoRepository userInfoRepository;
	//添加
	public void addUserInfo(UserInfo u){
		u.setId(UUID.randomUUID().toString());
		userInfoRepository.save(u);
	}
    //根据OPENID得到对应实体对象 
	public UserInfo getByOpenid(String openid){
		return userInfoRepository.getByOpenid(openid);
	}
	
	//用户如果存在 跟新用户表
	@Transactional
	public void updateUserInfo(UserInfo uInfo){
		userInfoRepository.updateUserInfo(uInfo.getNickname(), uInfo.getSex(), uInfo.getCity(), uInfo.getCountry(), uInfo.getProvince(), uInfo.getLanguage(), uInfo.getHeadimgurl(), uInfo.getSubscribe_time(), uInfo.getSubscribe(),uInfo.getOpenid());
	}
	
}
