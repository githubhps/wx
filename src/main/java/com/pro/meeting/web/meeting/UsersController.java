package com.pro.meeting.web.meeting;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pro.meeting.bean.UserInfo;
import com.pro.meeting.bean.Users;
import com.pro.meeting.service.UsersService;
import com.pro.meeting.service.impl.UserInfoServiceImpl;



@Controller
@RequestMapping("users")
public class UsersController {
	@Autowired
	UsersService  usersService;
	@Autowired
	UserInfoServiceImpl userInfoServiceImpl;
	
	@ResponseBody
	@RequestMapping("login")
	public String login(@RequestParam("email")final String email,@RequestParam("openid")final String openid){
		UserInfo userInfo=userInfoServiceImpl.getByOpenid(openid);
		if (userInfo==null) {
			return "0";
		}
		Users users = usersService.getByEmail(email);
		if(users==null){
			return "2";
		}
		if(users.getWid()!=null && !"".equals(users.getWid())){
			return "3";
		}
		usersService.updateUsersWidByEmail(email, userInfo.getId());
		return "1";
	}
	@ResponseBody
	@RequestMapping("lists")
	public List<Users> list(){
		List<Users> list=usersService.getBy();
		for(Users a:list){
			System.out.println(a);
		}
		return list;
	}
	
	@RequestMapping(value="add",method = RequestMethod.POST)
	public String addUsers(Users users){
		users.setCurrDate(new Date());
		usersService.add(users);
		return "redirect:/pages/admin/member-list.jsp";
	}

	
	//更新
	@ResponseBody
	@RequestMapping("update")
	public String update(Users u){ 
			usersService.update(u);
			return "1";
		}
	//删除
	@ResponseBody
	@RequestMapping("delect")
	public String delectUser(String uid){
		usersService.delect(uid);
		return "1";
	}
	
	

}
