package com.pro.meeting.web.meeting;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pro.meeting.bean.MeetingRank;
import com.pro.meeting.bean.Meetingpub;
import com.pro.meeting.bean.Users;
import com.pro.meeting.service.MeetingpubService;
import com.pro.meeting.service.UsersService;

@Controller
@RequestMapping("meetingPub")
public class MeetingpubController {
	@Autowired
	MeetingpubService meetingService;
	@Autowired
	UsersService usersService;
	@ResponseBody
	@RequestMapping("add")
	public String add(Meetingpub m){
		 Users u=usersService.getUid( m.getUid());
		 if(u==null){
			 return "2";//您还示登录，跳到LOGIN.JSP
		 }
		
		meetingService.save(m);
		return "1";
	}
	
	//查询我自己所有的
	@ResponseBody
	@RequestMapping(value="findAllByUid/{uid}")
	public List<Meetingpub> findAll(@PathVariable String uid){
		List<Meetingpub> list=meetingService.findAll(uid);
		return list;
	}

	//查询发单排行榜
	@RequestMapping("findMeetRank")
	public String findMeet(Map<String, Object> map){
		List<MeetingRank> list=meetingService.getPubRank();
		map.put("list", list);
		return "/pages/weixin/meetingPub/mettingRank.jsp";
	}
	
	//通过id 来查询 会议的详情信息
	@RequestMapping("getByPid")
	public String getMeetByPid(@RequestParam("pid") String pid,Map<String, Object> map){
		Meetingpub meetingpub = meetingService.getById(pid);
		map.put("meetingPub", meetingpub);
		return "/pages/weixin/meetingPub/meetingDetails.jsp";
	}
	
	//会议列表  可以抢单的
	@ResponseBody
	@RequestMapping("getAllGrap")
	public List<Meetingpub> getAllGrap(@RequestParam("ptype") String ptype,@RequestParam("uid") final String uid){
		return meetingService.getAllGrapByPtype(ptype, uid);
	}
	

	//查询参与拍卖的 竞价者
	@ResponseBody
	@RequestMapping("getGrapByPid")
	public List<MeetingRank> getGrapByPid(@RequestParam("pid")String pid){
		List<MeetingRank> list = meetingService.getGrapByPid(pid);
		System.out.println(list);
		return list;
	}


}
