package com.pro.weixin.api.userinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pro.meeting.bean.UserInfo;
import com.pro.meeting.service.impl.UserInfoServiceImpl;
import com.pro.weixin.api.accessToken.AccessTokenThread;
import com.pro.weixin.util.WeixinUtil;

import net.sf.json.JSONObject;

@Service
public class UserInfoApi {
	@Autowired
	UserInfoServiceImpl userInfoService;

	// 操作类
	public void userInfoOper(String openid) {

		JSONObject jsonObject = getUserInfo(openid);
		System.out.println(openid);
		System.out.println(jsonObject);
		// 根据OPENID，进行查询，如果存在，就更新，不存在就插入。
		UserInfo userInfo1 = userInfoService.getByOpenid(openid);
		if (userInfo1 != null) {
			// 更新操作
			UserInfo userInfo = new UserInfo();
			userInfo.setOpenid(jsonObject.getString("openid"));
			userInfo.setNickname(jsonObject.getString("nickname"));
			userInfo.setSex(jsonObject.getString("sex"));
			userInfo.setCity(jsonObject.getString("city"));
			userInfo.setCountry(jsonObject.getString("country"));
			userInfo.setProvince(jsonObject.getString("province"));
			userInfo.setLanguage(jsonObject.getString("language"));
			userInfo.setHeadimgurl(jsonObject.getString("headimgurl"));
			userInfo.setSubscribe_time(jsonObject.getString("subscribe_time"));
			userInfo.setSubscribe(jsonObject.getString("subscribe"));
			// 插入数据库
			userInfoService.updateUserInfo(userInfo);
		} else {
			// 将JSONObject解析成UserInfo
			UserInfo userInfo = new UserInfo();
			userInfo.setOpenid(jsonObject.getString("openid"));
			userInfo.setNickname(jsonObject.getString("nickname"));
			userInfo.setSex(jsonObject.getString("sex"));
			userInfo.setCity(jsonObject.getString("city"));
			userInfo.setCountry(jsonObject.getString("country"));
			userInfo.setProvince(jsonObject.getString("province"));
			userInfo.setLanguage(jsonObject.getString("language"));
			userInfo.setHeadimgurl(jsonObject.getString("headimgurl"));
			userInfo.setSubscribe_time(jsonObject.getString("subscribe_time"));
			userInfo.setSubscribe(jsonObject.getString("subscribe"));
			// 插入数据库
			userInfoService.addUserInfo(userInfo);
		}

	}


	// 调用微信API接口返回JSONObject
	public  JSONObject getUserInfo(String openid) {
		String USER_INFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="+AccessTokenThread.accessToken_str+
				"&openid="+openid+"&lang=zh_CN";
		
		JSONObject jsonObject = WeixinUtil.httpRequest(USER_INFO_URL, "GET", null);
		return jsonObject;
	}
}
