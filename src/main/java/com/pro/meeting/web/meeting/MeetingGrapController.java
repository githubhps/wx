package com.pro.meeting.web.meeting;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pro.meeting.bean.MeetingGrap;
import com.pro.meeting.bean.Meetingpub;
import com.pro.meeting.bean.Users;
import com.pro.meeting.service.MeetingpubService;
import com.pro.meeting.service.UsersService;
import com.pro.meeting.service.impl.MeetingGrapServiceImpl;

@Controller
@RequestMapping("grap")
public class MeetingGrapController {
     //会议发单  对象
	@Autowired
	MeetingpubService meetingPubService;
	//用户 对象操作业务
	@Autowired
	UsersService usersService;
	@Autowired
	MeetingGrapServiceImpl meetingGrapServiceImpl;
	
//###################### TODO	
	//点击抢单按钮，进入 抢单添加页面
	@RequestMapping("grapAdd")
	public String grapAdd(@RequestParam("pid")String pid,@RequestParam("uid")String uid,Map<String,Object> map){
		//pid 发单信息主键ID
		Meetingpub meetingPub=meetingPubService.getById(pid);
		map.put("meetingPub", meetingPub);
		//发单作者的   用户信息
		Users users=usersService.getUid(meetingPub.getUid());
		
		map.put("users",users);
		//uid 作者
		map.put("uid",uid);
		return "/pages/weixin/meetingGrab/meetingGrabAdd.jsp";
	}
	//抢单添加功能
	@RequestMapping("grapAddTo")
	@ResponseBody
	public String grapAddTo(MeetingGrap grap){
		meetingGrapServiceImpl.add(grap);
		//return "/wx/isMeetingGrab";
		return "1";
	}

   //抢单列表数据
	@ResponseBody
	@RequestMapping("test")
	public MeetingGrap getAllMeetingGrap(){
		
		return meetingGrapServiceImpl.getAllMeetingGrap();
	}
	
	@ResponseBody
	@RequestMapping("getByUid")
	public List<MeetingGrap> getByUid(@RequestParam("uid")String uid){
		 List<MeetingGrap> list=meetingGrapServiceImpl.getByUid(uid);
		 return list;
	}
	
	//就选你
	@ResponseBody
	@RequestMapping("updateGrapChoose")
   public String updateGrapChoose(@RequestParam("pid")final String pid,@RequestParam("uid") final String uid){
	 int num=0;  
	  try{
		num=meetingGrapServiceImpl.updateGrapChoose(pid, uid);
	  }catch(Exception e){
		 System.out.println(e.getMessage());
	  }
	 return num+"";
   }
}
