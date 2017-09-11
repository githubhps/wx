package com.pro.meeting.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pro.meeting.bean.Meetingpub;
import com.pro.meeting.bean.MeetingGrap;
import com.pro.meeting.springdata.MeetingGrapRepository;
import com.pro.meeting.springdata.MeetingpubRepository;

@Service
public class MeetingGrapServiceImpl {
     @Autowired
	MeetingGrapRepository meetingGrapRepository;
     @Autowired
     MeetingpubRepository meetingpubRepository;
	//添加方法
	public void add(MeetingGrap grap){
		 grap.setGid(UUID.randomUUID().toString());
		 grap.setCurrdate(new Date());
		 grap.setGstatus(0);//未审核
		 
		 Meetingpub meetingpub=meetingpubRepository.findOne(grap.getPid());
		 grap.setMeetingpub(meetingpub);//而不是grap.setPid("");
		 meetingGrapRepository.save(grap);
	}

   //抢单列表
	public MeetingGrap getAllMeetingGrap(){
		MeetingGrap m= meetingGrapRepository.findOne("111d36b8-f801-420b-a0a4-99241363c3c4");
		System.out.println(m.getMeetingpub());
		return m;
	}
	//我的抢单
	public List<MeetingGrap> getByUid(String uid){
		return meetingGrapRepository.getByUid(uid);
	}
	//就选你功能
	@Transactional
	public int updateGrapChoose(String pid,String uid) {
	    //第一步:
		int num1=meetingGrapRepository.updateStatusByPid(new Date(), pid);
		if(num1<1){
			//回滚事务
			System.out.println(1/0);
		}
		//第二步：
		int num2=meetingGrapRepository.updateStatusByPidAndUid(pid, uid);
		if(num2<1){
			//回滚事务
			System.out.println(1/0);
			throw new RuntimeException("第二步失败");
		}
		return 1;
	}
	
	// TODO 每日日报
		//--  昨天发单数量
			public int getYestPubCount(String time){
				return meetingGrapRepository.getYestPubCount(time);
			}
			
			//--  今日可抢单数量
			public int getTodPubCount(){
				return meetingGrapRepository.getTodPubCount();
			}
			// -- 根据类别，统计每个类别的可抢单数量
			public List<Object[]> getTypeCount(){
				return meetingGrapRepository.getTypeCount();
			}
			// -- 昨天成功匹配多少场
			public int getYestSuccCount(String time){
				return meetingGrapRepository.getYestSuccCount(time);
			}
			
			//-- 昨天召开多少场
			public int getYestSuccPubCount(String time){
				return meetingGrapRepository.getYestSuccPubCount(time);
			}
	
}
