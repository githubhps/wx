package com.pro.meeting.bean;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
public class Meetingpub {
	@Id
	private String pid;
	private String pcode;
	private String ptitle;
	private String ptime;
	private String ptype;
	private String premark;
	private String pcontent;
	private Date currDate;
	private String uid;
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getPcode() {
		return pcode;
	}
	public void setPcode(String pcode) {
		this.pcode = pcode;
	}
	public String getPtype() {
		return ptype;
	}
	public void setPtype(String ptype) {
		this.ptype = ptype;
	}
	public String getPtitle() {
		return ptitle;
	}
	public void setPtitle(String ptitle) {
		this.ptitle = ptitle;
	}
	
	public String getPtime() {
		return ptime;
	}
	public void setPtime(String ptime) {
		this.ptime = ptime;
	}
	public String getPremark() {
		return premark;
	}
	public void setPremark(String premark) {
		this.premark = premark;
	}
	public String getPcontent() {
		return pcontent;
	}
	public void setPcontent(String pcontent) {
		this.pcontent = pcontent;
	}
	public Date getCurrDate() {
		return currDate;
	}
	public void setCurrDate(Date currDate) {
		this.currDate = currDate;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	@Override
	public String toString() {
		return "Meetingpub [pid=" + pid + ", pcode=" + pcode + ", ptitle=" + ptitle + ", ptime=" + ptime + ", ptype="
				+ ptype + ", premark=" + premark + ", pcontent=" + pcontent + ", currDate=" + currDate + ", uid=" + uid
				+ "]";
	}
	
}
