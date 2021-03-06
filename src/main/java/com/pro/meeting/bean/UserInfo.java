package com.pro.meeting.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="wechat_users")
public class UserInfo {
	
	@Id
	@Column(name="wid")
	private String id;
	private String openid;
	private String nickname;
	private String sex;
	private String city;
	private String country;
	private String province;
	private String language;
	private String headimgurl;
	private String subscribe_time;
	private String subscribe;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getHeadimgurl() {
		return headimgurl;
	}
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	public String getSubscribe_time() {
		return subscribe_time;
	}
	public void setSubscribe_time(String subscribe_time) {
		this.subscribe_time = subscribe_time;
	}
	public String getSubscribe() {
		return subscribe;
	}
	public void setSubscribe(String subscribe) {
		this.subscribe = subscribe;
	}
	@Override
	public String toString() {
		return "UserInfo [id=" + id + ", openid=" + openid + ", nickname=" + nickname + ", sex=" + sex + ", city="
				+ city + ", country=" + country + ", province=" + province + ", language=" + language + ", headimgurl="
				+ headimgurl + ", subscribe_time=" + subscribe_time + ", subscribe=" + subscribe + "]";
	}
	
	
}
