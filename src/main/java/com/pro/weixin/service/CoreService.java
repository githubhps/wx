package com.pro.weixin.service;

import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pro.weixin.main.MenuManager;
import com.pro.weixin.pojo.AccessToken;
import com.pro.weixin.util.DateUtils;
import com.pro.weixin.util.MessageUtil;
import com.pro.weixin.util.WeixinUtil;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

import net.sf.json.JSONObject;

import com.pro.meeting.bean.Users;
import com.pro.meeting.service.UsersService;
import com.pro.meeting.service.impl.MeetingGrapServiceImpl;
import com.pro.weixin.api.userinfo.UserInfoApi;
import com.pro.weixin.bean.resp.Article;
import com.pro.weixin.bean.resp.MusicMessage;
import com.pro.weixin.bean.resp.NewsMessage;
import com.pro.weixin.bean.resp.TextMessage;

@Service
public class CoreService {
	@Autowired
	UserInfoApi userInfoApi;
	@Autowired
	UsersService usersService;
	@Autowired
	MeetingGrapServiceImpl meetingGrapServiceImpl;
	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return
	 */
	public String processRequest(HttpServletRequest request) {
		String respMessage = null;
		try {
			// 默认返回的文本消息内容
			String respContent = "请求处理异常，请稍候尝试！";

			// xml请求解析 调用消息工具类MessageUtil解析微信发来的xml格式的消息，解析的结果放在HashMap里；
			Map<String, String> requestMap = MessageUtil.parseXml(request);

			// 发送方帐号（open_id） 下面三行代码是： 从HashMap中取出消息中的字段；
			String fromUserName = requestMap.get("FromUserName");
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");

			// 回复文本消息 组装要返回的文本消息对象；
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setFuncFlag(0);
			// 由于href属性值必须用双引号引起，这与字符串本身的双引号冲突，所以要转义
			// textMessage.setContent("欢迎访问<a
			// href=\"http://www.baidu.com/index.php?tn=site888_pg\">百度</a>!");
			// 将文本消息对象转换成xml字符串
			respMessage = MessageUtil.textMessageToXml(textMessage);
			/**
			 * 演示了如何接收微信发送的各类型的消息，根据MsgType判断属于哪种类型的消息；
			 */

			// 接收用户发送的文本消息内容
			String content = requestMap.get("Content");
			// System.out.println("用户消息:"+content);

			// 创建图文消息
			NewsMessage newsMessage = new NewsMessage();
			newsMessage.setToUserName(fromUserName);
			newsMessage.setFromUserName(toUserName);
			newsMessage.setCreateTime(new Date().getTime());
			newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
			newsMessage.setFuncFlag(0);

			// 文本消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				respContent = "您发送的是文本消息！";
			}
			// 图片消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				respContent = "您发送的是图片消息！";
			}
			// 地理位置消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
				respContent = "您发送的是地理位置消息！";
			}
			// 链接消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				respContent = "您发送的是链接消息！";
			}
			// 音频消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
				respContent = "您发送的是音频消息！";
			}
			// 事件推送
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get("Event");
				// 订阅
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					
					userInfoApi.userInfoOper(fromUserName);
					respContent = "欢迎关注赫氏拍卖会，在这里你可以根据权限完成拍卖发布、参加拍卖会等功能";
				}
				// 取消订阅
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					// TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
				}
				
				// 自定义菜单点击事件
				else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
					// 事件KEY值，与创建自定义菜单时指定的KEY值对应
					String eventKey = requestMap.get("EventKey");

					if (eventKey.equals("11")) {
						respContent = "天气预报菜单项被点击！";

					}
					if(eventKey.equals("MEETING_PUB")){
						List<Article> articleList = new ArrayList<Article>();	
							//1验证用户是否绑定（认证） 根据OPEND查询 得到wid，根据wid查Users
						Users users=usersService.getUsersByWidByOpenid(fromUserName);
						// 单图文消息
						Article article = new Article();
						if(users==null){ //未登录
							article.setTitle("您还未登录");
							article.setDescription("您好，您还没有进行会员认证，暂无权限操作");
							article.setPicUrl(
									"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1503326038&di=8693ad9d38825df8380985bf3e0a965d&imgtype=jpg&er=1&src=http%3A%2F%2Fi0.sinaimg.cn%2Fgm%2F2011%2F0413%2FU4511P115DT20110413155128.jpg");
							article.setUrl(MenuManager.REAL_URL+"pages/weixin/users/login.jsp");
						}else{//已登录
							//抢单组
							if("2".equals(users.getRolesName())){
								article.setTitle("您是会员，无权限发布卖品");
								article.setDescription("您好，您是我们的会员用户，还未达到发布卖品的权限，再接再厉");
								article.setPicUrl(
										"https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3665669239,391887934&fm=26&gp=0.jpg");
								article.setUrl("https://www.baidu.com/");
							//发单组	
							}else if("1".equals(users.getRolesName())){
								article.setTitle("欢迎您，尊敬的"+users.getUname()+"会员");
								article.setDescription("您好，你有发布拍卖品的权限，这里可以进行..点此进入发布卖品操作..");
								article.setPicUrl(
										"https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3665669239,391887934&fm=26&gp=0.jpg");
								article.setUrl(MenuManager.REAL_URL+"pages/weixin/meetingPub/meetingPub.jsp?uid="+users.getUid());	
							}else if("3".equals(users.getRolesName())){
								article.setTitle("欢迎您，尊敬的"+users.getUname()+"会员");
								article.setDescription("您好，你是我们的超级管理员，这里可以进行..点此进入发布卖品操作..");
								article.setPicUrl(
										"https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3665669239,391887934&fm=26&gp=0.jpg");
								article.setUrl(MenuManager.REAL_URL+"pages/weixin/meetingPub/meetingPub.jsp?uid="+users.getUid());	
							}
						}
						
							
							articleList.add(article);
							// 设置图文消息个数
							newsMessage.setArticleCount(articleList.size());
							// 设置图文消息
							newsMessage.setArticles(articleList);
							// 将图文消息对象转换为XML字符串
							respMessage = MessageUtil.newsMessageToXml(newsMessage);
							return respMessage;
							
							
						}
					//版本信息
					else if(eventKey.equals("12")){
						List<Article> articleList = new ArrayList<Article>();
						// 根据openid查询Users表
						
						// 单图文消息
						Article article = new Article();
						article.setTitle("拍卖会简介");
						article.setDescription("赫氏拍卖会是全世界最大的拍卖会，在这里你可以发布任何你想拍卖的藏品");
						article.setPicUrl(
								"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1503028590&di=68416eefc3bb1a750fe7c3fd67ddd717&imgtype=jpg&er=1&src=http%3A%2F%2Fsrc.house.sina.com.cn%2Fimp%2Fimp%2Fdeal%2Fb5%2F38%2F5%2F55fe964193eb59da8b812a8ba38_p1_mk1_wm35.jpg");
						article.setUrl("https://www.baidu.com/");

						articleList.add(article);
						
						// 设置图文消息个数
						newsMessage.setArticleCount(articleList.size());
						// 设置图文消息
						newsMessage.setArticles(articleList);
						// 将图文消息对象转换为XML字符串
						respMessage = MessageUtil.newsMessageToXml(newsMessage);
						return respMessage;
					}
					//联系我们
					else if(eventKey.equals("21")){
						//根据OPENID查询，该用户是否登录
						//1验证用户是否绑定（认证） 根据OPEND查询 得到wid，根据wid查Users
						Users users=usersService.getUsersByWidByOpenid(fromUserName);
						if(users==null){
							respContent = "您还未登录，无法操作此功能！";	
						}else{
							String time=DateUtils.getTodayYearsAndMonthAndDay();//今日
							String ytime=DateUtils.getYeTodayYearsAndMonthAndDay();//昨日
							Integer count1=meetingGrapServiceImpl.getYestPubCount(ytime);
							Integer count2=meetingGrapServiceImpl.getTodPubCount();
							
							
							
							StringBuffer sb=new StringBuffer();
							sb.append("每日日报（今日"+DateUtils.getTodayMonthAndDay()+"日）\n");
							sb.append("1、昨天（"+DateUtils.getYeTodayMonthAndDay()+"）"
									+ "发单数量"+count1+" 场 \n");
							sb.append("2、今日可抢单数量"+count2+"场 \n");
							//根据查询数据，可变类别
							List<Object[]>list=meetingGrapServiceImpl.getTypeCount();
							for(Object[] obj :list){
								sb.append(obj[0]+"可抢单："+obj[1]+"场 \n");								
							}
							sb.append("3、总结：\n");
							Integer count3=meetingGrapServiceImpl.getYestSuccCount(ytime);
							Integer count4=meetingGrapServiceImpl.getYestSuccPubCount(ytime);
							sb.append("  昨天成功匹配 "+count3+"场 \n");
							sb.append("  昨天成功执行 "+count4+"场");
							
							respContent=sb.toString();
						}
						
					}
					
					
					//会员介绍
					else if(eventKey.equals("32")){
						respContent = "钻石会员7折\n"
								+"铂金会员8折\n"
								+"黄金会员9折\t";
					}
					// 使用规则 70
					/*
					 * 根据openid查询 Users表
					 * 如果Users表为空，==》返回一个图文消息(请点击图文进行页面）=》跳到登录页面
					 */
					else if (eventKey.equals("70")) {

						List<Article> articleList = new ArrayList<Article>();
						// 根据openid查询Users表
						
						// 单图文消息
						Article article = new Article();
						article.setTitle("单图文标题");
						article.setDescription("内容显示数据...要遵守法律法规则 、图文标题的描述信息....");
						article.setPicUrl(
								"http://pic.58pic.com/58pic/14/39/44/70Z58PICVpE_1024.jpg");
						article.setUrl("https://www.baidu.com/");

						articleList.add(article);
						
						// 设置图文消息个数
						newsMessage.setArticleCount(articleList.size());
						// 设置图文消息
						newsMessage.setArticles(articleList);
						// 将图文消息对象转换为XML字符串
						respMessage = MessageUtil.newsMessageToXml(newsMessage);
						return respMessage;
					} else if (eventKey.equals("34")) {
						List<Article> articleList = new ArrayList<Article>();
						// 单图文消息
						Article article = new Article();
						article.setTitle("最大的拍卖会，最全的交易商");
						article.setDescription("这个世界上，你还需要什么？你还缺什么？珠宝，钻石，美人鱼？精灵?这里都能满足你的需求");
						article.setPicUrl(
								"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1502433870202&di=90d6a73c0132666511d464653a7edb96&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F15%2F82%2F21%2F26R58PICdYt_1024.jpg");
						article.setUrl("https://www.baidu.com/");

						articleList.add(article);
						// 设置图文消息个数
						newsMessage.setArticleCount(articleList.size());
						// 设置图文消息
						newsMessage.setArticles(articleList);
						// 将图文消息对象转换为XML字符串
						respMessage = MessageUtil.newsMessageToXml(newsMessage);
						return respMessage;
					}
					// 返回多图文，其中第二个是跳转到一个登录页面
					else if (eventKey.equals("60")) {
						List<Article> articleList = new ArrayList<Article>();
						// 单图文消息
						Article article = new Article();
						article.setTitle("JAVA1702图文标题");
						article.setDescription("java1702图文标题的描述信息");
						article.setPicUrl(
								"https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png");
						article.setUrl("https://www.baidu.com/");

						// 第二个图文消息
						Article a1 = new Article();
						a1.setTitle("用户登录");
						a1.setDescription("本平台，用户登录人员都需经过邮箱正规验证，否则无法登录..");
						a1.setPicUrl(MenuManager.REAL_URL + "images/car.jpg");
						a1.setUrl(MenuManager.REAL_URL + "pages/users/login.jsp");// 跳登录页面

						articleList.add(article);
						articleList.add(a1);
						// 设置图文消息个数
						newsMessage.setArticleCount(articleList.size());
						// 设置图文消息
						newsMessage.setArticles(articleList);
						// 将图文消息对象转换为XML字符串
						respMessage = MessageUtil.newsMessageToXml(newsMessage);
						return respMessage;

					} else if (eventKey.equals("21")) {
						respContent = "每天十辐图功能正在研发中...请稍侯";
					} else if (eventKey.equals("22")) {
						respContent = "四六级功能正在研发中...请稍侯";
					}
				}

			}

			// 组装要返回的文本消息对象；
			textMessage.setContent(respContent);
			// 调用消息工具类MessageUtil将要返回的文本消息对象TextMessage转化成xml格式的字符串；
			respMessage = MessageUtil.textMessageToXml(textMessage);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return respMessage;
	}

}
