package com.pro.meeting.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pro.meeting.bean.Meetingtype;
import com.pro.meeting.service.MeetingtypeService;
import com.pro.meeting.springdata.MeetingtypeRepository;
@Service
public class MeetingtypeServiceImpl implements MeetingtypeService {
    @Autowired
	MeetingtypeRepository meetingtypeRepository;
	
    @Override
	public List<Meetingtype> getListByStatus(Integer status) {
		// TODO Auto-generated method stub
		return meetingtypeRepository.getByStatusOrderByOrdernum(1);
	}

}
