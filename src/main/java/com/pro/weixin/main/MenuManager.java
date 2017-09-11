package com.pro.weixin.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pro.weixin.pojo.AccessToken;
import com.pro.weixin.pojo.Button;
import com.pro.weixin.pojo.CommonButton;
import com.pro.weixin.pojo.ComplexButton;
import com.pro.weixin.pojo.Menu;
import com.pro.weixin.pojo.ViewButton;
import com.pro.weixin.util.WeixinUtil;

/**
 * 菜单管理器类
 * 
 */
public class MenuManager {
	private static Logger log = LoggerFactory.getLogger(MenuManager.class);
/***
 * 自定义菜单的创建步骤
	1、找到AppId和AppSecret。自定义菜单申请成功后，在“高级功能”-“开发模式”-“接口配置信息”的最后两项就是；
	2、根据AppId和AppSecret，以https get方式获取访问特殊接口所必须的凭证access_token；
	3、根据access_token，将json格式的菜单数据通过https post方式提交。

 */
	
	/**大连为生活
	 * AppId wx48aea262c7e90505
		AppSecret 7bdb19ae6060e73f84ffce36ebab3c54
		测试：
		wxe545a87dd58d472c
		8e7b1ee0710dbe4ba9fbcd93dc2321cf
	 */
	public final static String REAL_URL="http://1m79479c55.iask.in/maven_weixin/"; //个人花生壳
	//public final static String REAL_URL = "http://wxmobsa.yidatec.com/weixin/"; //正式号服务器	
	
	public final static String appId="wxc4de7b06ae974ab7";
	public final static String appSecret = "c0543af6bf296596b1b23ba62ab48a9d";
	
	public static void resultMenu(){
		// 调用接口获取access_token
		AccessToken at = WeixinUtil.getAccessToken(appId, appSecret);

		if (null != at) {
			// 调用接口创建菜单
			int result = WeixinUtil.createMenu(getMenu(), at.getToken());

			// 判断菜单创建结果
			if (0 == result)
				log.info("菜单创建成功！");
			else
				log.info("菜单创建失败，错误码：" + result);
		}
	}
	
	public static void main(String[] args) {
		// 第三方用户唯一凭证
		String appId = MenuManager.appId;
		// 第三方用户唯一凭证密钥
		String appSecret = MenuManager.appSecret;
		// 调用接口获取access_token
		AccessToken at = WeixinUtil.getAccessToken(appId, appSecret);

		if (null != at) {
			// 调用接口创建菜单
			int result = WeixinUtil.createMenu(getMenu(),at.getToken());

			// 判断菜单创建结果
			if (0 == result)
				log.info("菜单创建成功！");
			else
				log.info("菜单创建失败，错误码：" + result);
		}
	}

	/**
	 * 组装菜单数据
	 * 
	 * @return
	 */
	private static Menu getMenu() {
		
		ViewButton btn11 = new ViewButton();
		btn11.setName("参加拍卖");
		btn11.setType("view");
		btn11.setUrl(REAL_URL+"wx/isMeetingGrab");
		
		CommonButton btn13 = new CommonButton();
		btn13.setName("拍卖发布");
		btn13.setType("click");
		btn13.setKey("MEETING_PUB");
		
		CommonButton btn12 = new CommonButton();
		btn12.setName("拍卖会简介");
		btn12.setType("click");
		btn12.setKey("12");
//-------------------------------------------------------
		CommonButton btn21 = new CommonButton();
		btn21.setName("昨日纪文");
		btn21.setType("click");
		btn21.setKey("21");
		
		ViewButton btn22 = new ViewButton();
		btn22.setName("排行榜");
		btn22.setType("view");
		btn22.setUrl(REAL_URL+"meetingPub/findMeetRank");
//------------------------------------------------------------		
		ViewButton btn31 = new ViewButton();
		btn31.setName("会员中心");
		btn31.setType("view");
		btn31.setUrl(REAL_URL+"wx/oauth2");
		
		CommonButton btn32 = new CommonButton(); //返回图文消息
		btn32.setName("会员介绍");
		btn32.setType("click");
		btn32.setKey("32"); 
		
		ViewButton btn33 = new ViewButton();
		btn33.setName("法律声明");
		btn33.setType("view");
		btn33.setUrl("https://www.baidu.com/");
		
		CommonButton btn34 = new CommonButton(); //返回图文消息
		btn34.setName("关于我们");
		btn34.setType("click");
		btn34.setKey("34"); 
	
		//###############################################一级子菜单
		ComplexButton mainBtn1 = new ComplexButton();
		mainBtn1.setName("拍卖会");
		mainBtn1.setSub_button(new Button[] { btn11,btn13,btn12});

		ComplexButton mainBtn2 = new ComplexButton();
		mainBtn2.setName("公告板");  // 
		mainBtn2.setSub_button(new Button[] { btn21,btn22});

		ComplexButton mainBtn3 = new ComplexButton();
		mainBtn3.setName("会员专刊");// btn31, btn32, btn33,
		mainBtn3.setSub_button(new Button[] {btn31,btn32,btn33,btn34});

		/**
		 * 这是公众号目前的菜单结构，每个一级菜单都有二级菜单项<br>
		 */
		Menu menu = new Menu();
		menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });

		return menu;
	}
}
