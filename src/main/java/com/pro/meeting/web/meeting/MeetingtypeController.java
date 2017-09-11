package com.pro.meeting.web.meeting;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pro.meeting.bean.Meetingtype;
import com.pro.meeting.service.MeetingtypeService;

@Controller
@RequestMapping("meetingType")
public class MeetingtypeController {
	@Autowired
   MeetingtypeService meetingtypeService;
	//获得列表
	@ResponseBody
	@RequestMapping("list")
	public List<Meetingtype> getList(){
		return meetingtypeService.getListByStatus(1);		
	}
}
