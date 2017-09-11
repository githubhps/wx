package com.pro.meeting.springdata;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.pro.meeting.bean.MeetingGrap;

public interface MeetingGrapRepository extends JpaRepository<MeetingGrap, String> {
     
	//select * from meetinggrap where uid=13
	public List<MeetingGrap> getByUid(String uid);
	
	//就选你
	/*
	    -- 就选你 通过 PID UID
		-- 匹配成功 的： 通过PID和UID进行修改该状态为 2
		-- 其它的 通过PID查询，且UID!=UID 将其状态改为1
		-- 同学思路好：
		-- 第一步：根据PID，将状态全部改 1   update meetinggrap set gstatus=1 where pid=?
		-- 第二步，根据PID和UID，将其状态改为2
	 */
	@Modifying
	@Query(value=" update meetinggrap set gstatus=1,gtime=?1  where pid=?2",nativeQuery=true)
	public int updateStatusByPid(Date gtime,String pid);
	
	@Modifying
	@Query(value="update meetinggrap set gstatus=2  where pid=?1 and uid=?2",nativeQuery=true)
    public int updateStatusByPidAndUid(String pid,String uid);
	
	
	//查询每日日报
	//昨天的发单数量
	@Query(value=" select count(pid) from meetingpub  where LEFT(currDate,10)=?1",nativeQuery=true)
	public int getYestPubCount(String time); 
	//--  今日可抢单数量
		@Query(value="  select  count(pid) from (  select DISTINCT pub.* from meetingpub pub LEFT JOIN  meetinggrap grap on pub.pid=grap.pid "
				+ "where grap.gstatus=0  or grap.gstatus is null  ) a1 ",nativeQuery=true)
		public int getTodPubCount(); 
		// -- 根据类别，统计每个类别的可抢单数量
		@Query(value="  select  ptype,count(pid) from (  select DISTINCT pub.* from meetingpub pub LEFT JOIN  meetinggrap grap on pub.pid=grap.pid "
				+ "where grap.gstatus=0  or grap.gstatus is null  ) a1  GROUP BY ptype ",nativeQuery=true)
		public List<Object[]> getTypeCount();
		// -- 昨天成功匹配多少场
		@Query(value=" select count(gid) from meetinggrap where LEFT(gtime,10)=?1 and gstatus=2 ",nativeQuery=true)
		public int getYestSuccCount(String time); 
		
		//-- 昨天召开多少场
		@Query(value=" select count(pub.pid) from meetingpub pub LEFT JOIN meetinggrap grap on pub.pid=grap.pid  "
				+ "where LEFT(ptime,10)=?1 and grap.gstatus=2",nativeQuery=true)
		public int getYestSuccPubCount(String time); 
	
}
