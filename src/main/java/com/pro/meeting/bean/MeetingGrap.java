package com.pro.meeting.bean;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
@Entity
public class MeetingGrap {
	@Id
	private String gid;
	private String gremark;
	private Date currdate;
	@Transient
	private String pid;
	private int gstatus;
	
	private Date gtime;
	private String uid;

	@ManyToOne
	@JoinColumn(name="pid")
	private Meetingpub meetingpub;

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getGremark() {
		return gremark;
	}

	public void setGremark(String gremark) {
		this.gremark = gremark;
	}

	public Date getCurrdate() {
		return currdate;
	}

	public void setCurrdate(Date currdate) {
		this.currdate = currdate;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public int getGstatus() {
		return gstatus;
	}

	public void setGstatus(int gstatus) {
		this.gstatus = gstatus;
	}

	public Date getGtime() {
		return gtime;
	}

	public void setGtime(Date gtime) {
		this.gtime = gtime;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Meetingpub getMeetingpub() {
		return meetingpub;
	}

	public void setMeetingpub(Meetingpub meetingpub) {
		this.meetingpub = meetingpub;
	}
	
}
