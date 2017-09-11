package com.pro.meeting.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pro.meeting.bean.MeetingRank;
import com.pro.meeting.bean.Meetingpub;
import com.pro.meeting.service.MeetingpubService;
import com.pro.meeting.springdata.MeetingpubRepository;

@Service
public class MeetingpubServiceImpl implements MeetingpubService {
	@Autowired
	MeetingpubRepository meetingpubRepository;
	
	@Override
	public void save(Meetingpub m) {
		// TODO Auto-generated method stub
		m.setPid(UUID.randomUUID().toString());
		m.setCurrDate(new Date());
	
		//根据会议召开时间 ，规则，生成会议编号
		System.out.println(m);
		String genCode=genrMeetingpubPcode(m.getPtime());
		m.setPcode(genCode);
		meetingpubRepository.save(m);
	}

	
	//会议编号
	/*	1、	会议编号是由后台生成。生成规律：
		根据用户填入的会议召开时间，进行生成，
		例如格式：
		召开时间为：2017-10-01T11:22   
		编号为：20171001001(第一单，依次生成为002...003....)
		ptim:2017-10-01T11:22
      完成业务逻辑步骤：
       1字符串截取 2017-10-01
    */
	public String genrMeetingpubPcode(String ptime){
		String resultStr="";
		//1字符串截取  2017-10-01
		String strSub=ptime.substring(0, 10);
		//2把 - 去掉
		strSub=strSub.replaceAll("-",""); //20171001
		//3数据库中查询，查询当天发单号，是否存在，如果不存在，  +001  如果存在，把最大的取出来+1
		String result=meetingpubRepository.getMaxPcode(strSub);		
		//4进行判断，如果存在，就对其+1，不存在，对其+001
		if(!"".equals(result)&& result!=null){
			resultStr=(Long.parseLong(result)+1)+"";
		}else{
			resultStr=strSub+"001";
		}
		return resultStr;
	}


	@Override
	public List<Meetingpub> findAll(String uid) {
		// TODO Auto-generated method stub
		return meetingpubRepository.getByUidOrderByPcodeDesc(uid);
	}


	@Override
	public List<MeetingRank> getPubRank() {
		List<Object[]> listObj = meetingpubRepository.getPubRank();
		List<MeetingRank> list = new ArrayList<MeetingRank>();
		for(int i=0;i<listObj.size();i++){
			Object[] objArr=listObj.get(i);
			MeetingRank pojo = new MeetingRank();
			pojo.setUid(objArr[0]+"");
			pojo.setCount(objArr[1]+"");
			pojo.setNicknamr(objArr[2]+"");
			pojo.setHeadimgurl(objArr[3]+"");
			list.add(pojo);
		}
		return list;
	}

	//根据id查询 会议的详细信息
	@Override
	public Meetingpub getById(String pid) {
		// TODO Auto-generated method stub
		return meetingpubRepository.findOne(pid);
	}


	//会议抢单列表中，根据条件查询
		public List<Meetingpub> getAllGrapByPtype(String ptype,String uid){
			if("可竞价".equals(ptype)){
				return meetingpubRepository.getAllGrap(uid);
			}else{
				return meetingpubRepository.getAllGrapByPtype(ptype,uid);
			}
		
		}

		//我的抢单
		@Override
		public List<Meetingpub> getMeetingpubGrap(String uid) {
			// TODO Auto-generated method stub
			return null;
		}

		// TODO 选择讲者 列表数据查询
		@Override
		public List<MeetingRank> getGrapByPid(String pid) {
			// TODO Auto-generated method stub
			System.out.println(pid);
			List<Object[]> listObj = meetingpubRepository.getGrapByPid(pid);
			List<MeetingRank> list = new ArrayList<MeetingRank>();
			System.out.println(listObj);
			for(int i=0;i<listObj.size();i++){
				Object[] objArr=listObj.get(i);
				
				MeetingRank pojo = new MeetingRank();
				pojo.setField1(objArr[0].toString());
				pojo.setField2(objArr[1].toString());
				pojo.setField3(objArr[2].toString());
				pojo.setField4(objArr[3].toString());
				pojo.setField5(objArr[4].toString());
				pojo.setField6(objArr[5].toString());
				list.add(pojo);
			}
			System.out.println(list);
			return list;
		}
		//根据 主键ID，得到实体对象
		public Meetingpub getId(String pid){
			return meetingpubRepository.findOne(pid);
		}
}
