package com.pro.meeting.springdata;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pro.meeting.bean.MeetingRank;
import com.pro.meeting.bean.Meetingpub;

public interface MeetingpubRepository  extends JpaRepository<Meetingpub,String>{
	//根据截取的编号，查询当天是否有生成会议编号
	// select max(pcode) from meetingpub where LEFT(pcode,8)=?1;
	@Query(value=" select max(pcode) from meetingpub where LEFT(pcode,8)=?1 ",nativeQuery=true)
	public String getMaxPcode(String code);
	//根据id 查自己发布的会议
	public List<Meetingpub> getByUidOrderByPcodeDesc(String uid);
	//发单的排行榜
	@Query(value = "SELECT pub.uid as uid,count(*) as count, w.nickname, w.headimgurl "
			+ "FROM meetingpub pub,users u,wechat_users w "
			+ "WHERE pub.uid = u.uid and u.wid = w.wid "
			+ "GROUP BY pub.uid order by count desc",nativeQuery=true)
	public List<Object[]> getPubRank();
	
	//查询所有的可抢单  会议列表
	@Query(value="select DISTINCT  pub.*  from meetingpub pub LEFT JOIN meetinggrap grap"
				+ " on pub.pid=grap.pid where (grap.gstatus=0 "
				+ "or grap.gstatus is null)"
				+ " AND pub.pid NOT IN(SELECT g.pid FROM meetinggrap g WHERE g.uid=?1)  ",nativeQuery=true)
	public List<Meetingpub> getAllGrap(String uid);
	
	//查询所有可抢单 会议列表，根据类型
	@Query(value="select DISTINCT  pub.*  from meetingpub pub LEFT JOIN meetinggrap grap"
				+ " on pub.pid=grap.pid where ( grap.gstatus=0 "
				+ "or grap.gstatus is null ) and pub.ptype=?1 "
				+ " AND pub.pid NOT IN(SELECT g.pid FROM meetinggrap g WHERE g.uid=?2)",nativeQuery=true)
	public List<Meetingpub> getAllGrapByPtype(String ptype,String uid);
	
	
	//选择讲者 列表数据查询
	@Query(value="select u.uid, u.uname,u.city, grap.gremark,grap.pid,grap.gstatus  from meetinggrap grap LEFT JOIN users u on grap.uid=u.uid where pid=?1",nativeQuery=true)
	public List<Object[]> getGrapByPid(String pid);
	
}
