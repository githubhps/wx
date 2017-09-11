package com.pro.meeting.service;

import java.util.List;

import com.pro.meeting.bean.Meetingtype;

public interface MeetingtypeService {
	//保存
	public List<Meetingtype> getListByStatus(Integer status);
}
