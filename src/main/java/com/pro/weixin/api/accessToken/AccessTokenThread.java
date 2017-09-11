package com.pro.weixin.api.accessToken;

import com.pro.weixin.main.MenuManager;
import com.pro.weixin.pojo.AccessToken;
import com.pro.weixin.util.WeixinUtil;

import net.sf.json.JSONObject;

/*
 * 定义：access_token是公众号的全局唯一接口调用凭据，
                  公众号调用各接口时都需使用access_token。
	 
	 1、access_token的有效期目前为2个小时，重复获取将导致上次获取的access_token失效。
     2、每天只能调用不超过200次
 */
public class AccessTokenThread extends Thread {
	//让外部直接来访问此属性
	public static String accessToken_str;
	@Override
	public void run() {
		while (true) {
			
			AccessToken accessTokenObj=getAccessToken(MenuManager.appId,MenuManager.appSecret);
			accessToken_str=accessTokenObj.getToken();
			//单位：毫秒  1000毫秒=1秒
			/*
			 * 3600*1000
			 */
			try {
				Thread.sleep(3600*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	// 获取access_token的接口地址（GET） 限200（次/天）
	public final static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	
	/**
	 * 获取access_token
	 * 
	 * @param appid 凭证
	 * @param appsecret 密钥
	 * @return
	 */
	public static AccessToken getAccessToken(String appid, String appsecret) {
		AccessToken accessToken = null;

		String requestUrl = ACCESS_TOKEN_URL.replace("APPID", appid).replace("APPSECRET", appsecret);
		JSONObject jsonObject = WeixinUtil.httpRequest(requestUrl, "GET", null);
		System.out.println("获取凭证："+jsonObject.toString());
		// 如果请求成功
		if (null != jsonObject) {
			try {
				accessToken = new AccessToken();
				accessToken.setToken(jsonObject.getString("access_token"));
				accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
				
			//	System.out.println(accessToken.getExpiresIn());
			} catch (Exception e) {
				accessToken = null;
				// 获取token失败
				e.printStackTrace();
			}
		}
		return accessToken;
	}
	
	
}
