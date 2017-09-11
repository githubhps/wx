package com.pro.meeting.service;


import java.util.List;

import com.pro.meeting.bean.MeetingRank;
import com.pro.meeting.bean.Meetingpub;

public interface MeetingpubService {

	public void save(Meetingpub m);
	
	public List<Meetingpub> findAll(String uid);
	
	//查询排行榜
	public List<MeetingRank> getPubRank();
	
	//根据id 查询 会议详情
	public Meetingpub getById(String pid);
	
	//查询所有可抢单 会议列表，根据类型
	public List<Meetingpub> getAllGrapByPtype(String ptype,String uid);
	
	//我的抢单
	public List<Meetingpub> getMeetingpubGrap(String uid);
		
	// TODO 选择讲者 列表数据查询
	public List<MeetingRank> getGrapByPid(String pid);
	
	
}
