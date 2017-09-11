package com.pro.meeting.web.wechat;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pro.meeting.bean.Users;
import com.pro.meeting.service.UsersService;
import com.pro.weixin.main.MenuManager;
import com.pro.weixin.util.WeixinUtil;

import net.sf.json.JSONObject;

@RequestMapping("wx")
@Controller
public class WeixinController {
	
	@Autowired
	UsersService usersService;
	
	@RequestMapping("oauth2")
	public void getAppid(HttpServletResponse response){
		String pathUrl=MenuManager.REAL_URL+"wx/login";
		try {
			pathUrl=URLEncoder.encode(pathUrl, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String url="https://open.weixin.qq.com/connect/oauth2/authorize?"
				+"appid="+MenuManager.appId
				+"&redirect_uri="+pathUrl
				+"&response_type=code"
				+"&scope=snsapi_base"
				+"&state=STATE#wechat_redirect";
		try {
			response.sendRedirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	@RequestMapping("login")
	public String login(HttpServletRequest request){
		String code=request.getParameter("code");
		String url= "https://api.weixin.qq.com/sns/oauth2/access_token?"
				+"appid="+MenuManager.appId
				+"&secret="+MenuManager.appSecret
				+"&code="+code
				+"&grant_type=authorization_code";
		JSONObject jsonObject=WeixinUtil.httpRequest(url, "POST", null);
		String openid=jsonObject.getString("openid");
		request.setAttribute("openid", openid);
		Users u = usersService.getUsersByWidByOpenid(openid);
		if (u!=null) {
			request.setAttribute("users", u);
			return "/pages/weixin/users/userInfo.jsp";
		}
		return  "/pages/weixin/users/login.jsp";
	}
	
	// TODO  OAuth2.0授权 微信菜单--》会议抢单
		@RequestMapping("isMeetingGrab")
		public void isMeetingGrab(HttpServletResponse response) throws IOException{
			/*
			 * Session  Cookie...
			 * 验证当前微信号OPENID，是否进行绑定，
			 * 如果没有绑定，跳转到login.jsp页面完成绑定
			 * 如果绑定，跳转到 个人中心页面
			 */
			/*
			 * OAuth2.0授权流程
			 * 第一步：用户同意授权，获取code
			 */
			String pathUrl=MenuManager.REAL_URL+"wx/isMeetingGrab/invoke";
			try {
				pathUrl=URLEncoder.encode(pathUrl,"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			String url="https://open.weixin.qq.com/connect/oauth2/authorize?"
					+"appid="+MenuManager.appId
					+"&redirect_uri="+pathUrl     
					+"&response_type=code"
					+"&scope=snsapi_base&state=java1701#wechat_redirect";
			//发送请求
			response.sendRedirect(url);
		}
		
		@RequestMapping("isMeetingGrab/invoke")
		public String isMeetingGrabInvoke(HttpServletRequest request){
			/*
			第二步：通过code换取网页授权access_token
			*/
			String code=request.getParameter("code");
			//获取code后，进行请求
			String url="https://api.weixin.qq.com/sns/oauth2/access_token?"
					+"appid="+MenuManager.appId
					+"&secret="+MenuManager.appSecret
					+"&code="+code
					+"&grant_type=authorization_code";
			
			JSONObject json=WeixinUtil.httpRequest(url, "POST", null);
			//得到openid
			String openid=json.getString("openid");
			request.setAttribute("openid",openid);
			/*
			 * 判断是否登录，如果未登录，跳到login.jsp
			 * 如果登录，跳到抢单页面
			 */
			Users u=usersService.getUsersByWidByOpenid(openid);
			if(u!=null){
				//跳到抢单页面
				//return "";
				return "/pages/weixin/meetingGrab/meetingGrab.jsp?roleId="+u.getRolesName()+"&uid="+u.getUid();
			}
			
			return "/pages/weixin/users/login.jsp";

		}
	
}
