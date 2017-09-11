<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String pid=request.getParameter("pid");
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
		<meta http-equiv="Cache-Control" content="no-cache,must-revalidate">
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta name="format-detection" content="telephone=no, address=no">
		<meta content="yes" name="apple-mobile-web-app-capable">
		<meta content="black-translucent" name="apple-mobile-web-app-status-bar-style">
		<title></title>
		<link rel="stylesheet" href="http://code.jquery.com/mobile/1.3.2/jquery.mobile-1.3.2.min.css">
		<link href="https://cdn.bootcss.com/jquery-mobile/1.4.5/jquery.mobile.theme.css" rel="stylesheet">
		<script src="http://code.jquery.com/jquery-1.8.3.min.js"></script>
		<script src="http://code.jquery.com/mobile/1.3.2/jquery.mobile-1.3.2.min.js"></script>


  	
	<script type="text/javascript">
	    
	      $(function(){
	    	  showMyGrapList();
	      });
	    
			
	     
	     
			
			function showMyGrapList() {
					$("#two").empty();
					var pid=$("#pid").val();
					$.ajax({
						type:"post",
						url:"meetingPub/getGrapByPid",
						data:{"pid":pid},
						success:function(msg){
							if(msg.length=="0"){
								var appendHtml = "<font style='padding: 10px 10px 10px 15px;display: block;color: #777777;'>这是宝贝吗？都没人抢</font>";
							}else{
							var appendHtml = "<font style='padding: 10px 10px 10px 15px;display: block;color: #777777;'>共有"+msg.length+"人抢此宝物</font>";
							}
							var uname="";
				        	var province="";
				        	var gremark="";
				        	var uid="";
				        	var gstatus="";
				        	
					        for(var i=0;i<msg.length;i++){
							       uname=msg[i].field2;
							       province=msg[i].field3;
							       gremark=msg[i].field4;
							       uid=msg[i].field1;
							       gstatus=msg[i].field6; 
									appendHtml += "<div style='width: 100%;background-color: white;margin-top: -3px;padding:10px 10px 10px 15px;display: inline-block;'>" +
									"<div style='width: 70%;float: left;'>" +
									"<div style='white-space: nowrap;overflow: hidden;text-overflow:ellipsis;display: block;font-size:18px;'>" +
									uname + "</div>" +
									"<div style='white-space: nowrap;overflow: hidden;text-overflow:ellipsis;display: block;color: #777777;font-size:16px;padding-top:1px'>" +
									province + " / " +gremark + "</div></div>" +
									"<div style='width: 30%;float: right;'>";
									
									if("0"==gstatus){
									 	appendHtml+="<button class='able-btn' onclick='chooseGrap(\""+pid+"\",\""+uid+"\")' >就选你</button>";
									}else if("2"==gstatus){
									 	appendHtml+="竞拍成功";
									}else{
										appendHtml+="未选择";
									}
									
									appendHtml+="</div></div>";
					       		 }
					        
			        		$("#two").append(appendHtml);	
						}
						
					});
			}
		
			function chooseGrap(pid,uid){
				
				if(confirm("确定和此用户交易吗？")){
					$.ajax({
						type:"post",
						url:"grap/updateGrapChoose",
						data:{"pid":pid,"uid":uid},
						success:function(msg){
							if("1"==msg){
								alert("交易者选择成功");
								 showMyGrapList();
							}
						}
						
					});	
				 }
			}
			
		</script>
</head>
<body>
<input type="hidden" id="pid" value="<%=pid%>">
<div data-role="page" id="pageDetail">
	<div style="padding:0px;background-color: #4E90C7;width: 100%;height:40px;line-height:40px;font-size:18px;text-align: center;cursor: pointer;" data-role="none">
		<div style="color: white;" onclick="showMyMeetings();" id="two_tab">竞拍者列表</div>
	</div>
	<div id="two" class="ui-body-d ui-content" style="padding: 0;width: 100%;">
		
	</div>
	</body>

</html>