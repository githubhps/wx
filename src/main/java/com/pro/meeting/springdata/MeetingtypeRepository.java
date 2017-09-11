package com.pro.meeting.springdata;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pro.meeting.bean.Meetingtype;

public interface MeetingtypeRepository extends JpaRepository<Meetingtype,String> {
	//课程列表数据
    //SELECT * FROM meetingTYPE WHERE STATUS=1 ORDER BY ORDERNUM;
	public List<Meetingtype> getByStatusOrderByOrdernum(Integer status);
	
}
