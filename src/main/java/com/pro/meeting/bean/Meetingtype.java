package com.pro.meeting.bean;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
public class Meetingtype {
	
	@Id
	private String tid;
	private String tname;
	private Integer status;
	private Date currDate;
	private int ordernum;
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getTname() {
		return tname;
	}
	public void setTname(String tname) {
		this.tname = tname;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getDatetime() {
		return currDate;
	}
	public void setDatetime(Date datetime) {
		this.currDate = datetime;
	}
	public int getOrdernum() {
		return ordernum;
	}
	public void setOrdernum(int ordernum) {
		this.ordernum = ordernum;
	}
	@Override
	public String toString() {
		return "Meetingtype [tid=" + tid + ", tname=" + tname + ", status=" + status + ", currDate=" + currDate
				+ ", ordernum=" + ordernum + "]";
	}
	
}
