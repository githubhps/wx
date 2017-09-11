package com.pro.meeting.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pro.meeting.bean.UserInfo;
import com.pro.meeting.bean.Users;
import com.pro.meeting.service.UsersService;
import com.pro.meeting.springdata.UsersRepository;

@Service
public class UsersServiceImpl  implements UsersService{
	@Autowired
	UsersRepository  usersRepository;
	
	@Override
	public Users getUsersByWidByOpenid(String openid) {
		
		return usersRepository.getUsersByWidByOpenid(openid);
	}

	
	@Override
	@Transactional
	public void updateUsersWidByEmail(String email, String wid) {
		usersRepository.updateUsersWidByEmail(email, wid);		
	}


	@Override
	public Users getByEmail(String email) {
		// TODO Auto-generated method stub
		return usersRepository.getByEmail(email);
	}


	@Override
	public List<Users> getBy() {
		// TODO Auto-generated method stub
		return usersRepository.getBy();
	}


	@Override
	public void add(Users users) {
		// TODO Auto-generated method stub
		usersRepository.save(users);
	}


	// 更新
		@Transactional
		public int update(Users u) {
			int num = usersRepository.update(u.getUname(), u.getCity(), u.getTelphone(), u.getUid());
			return num;
		}
		//根据ID查询数据
		public Users getUid(String uid){
				return usersRepository.findOne(uid);
			}


		@Override
		public void delect(String uid) {
				// TODO Auto-generated method stub
			usersRepository.delete(uid);
		}

}
