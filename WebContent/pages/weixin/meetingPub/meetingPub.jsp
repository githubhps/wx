<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String uid=request.getParameter("uid");
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
	    	//加载下拉框数据
	    	 loadAjaxSelect();
	     });
	    
	     function loadAjaxSelect(){
	    	 //ajax ptitle下拉框赋值 
	    	 $.ajax({
	    		 type:"post",
	    		 url:"meetingType/list",
	    		 success:function(msg){
				   var appendHtml="<option value='请选择' selected=true >请选择</option>";
	    			 for(var i=0;i<msg.length;i++){
	    				 //<option value="数据库">数据库</option>
	    			   appendHtml+="<option value="+msg[i].tname+">"+msg[i].tname+"</option>";
	    			 }
	    			 $("#ptype").append(appendHtml);
	    		 }
	    	 });
	     }
			
	     
	     
			function showPubDiv() {
				$("#two_line").css("border-top", "5px solid #4E90C7");
				//$("#two_tab").css("color","#777777");
				$("#one_line").css("border-top", "5px solid white");
				//$("#one_tab").css("color","white");
				$("#two").css("display", "none");
				$("#one").css("display", "block");
			}

			function showMyMeetings() {

				$("#two_line").css("border-top", "5px solid white");
				$("#one_line").css("border-top", "5px solid #4E90C7");
			
					$("#one").css("display", "none");
					$("#two").css("display", "block");
					
					value=("拍卖品发布成功");
					var uid=$("#uid").val();
					
					 $.ajax({
			    		 type:"post",
			    		 url:"meetingPub/findAllByUid/"+uid,
			    		 success:function(msg){
			    			 $("#two").empty();
			    			 var appendHtml="";
							 for(var i=0;i<msg.length;i++){
								 var mname=msg[i].ptitle;
								 var remark=msg[i].ptype;
								 var dateCurr=msg[i].premark;
								 appendHtml += "<div style='width: 100%;background-color: white;margin-top: -3px;padding:10px 10px 10px 15px;display: inline-block;'>" +
									"<div style='width: 70%;float: left;' onclick='showMeetingInfo(\"" + msg[i].pid + "\");'>" 
									+"<div style='white-space: nowrap;overflow: hidden;text-overflow:ellipsis;display: block;font-size:18px;'>" +
									mname + "</div>" +
									"<div style='white-space: nowrap;overflow: hidden;text-overflow:ellipsis;display: block;color: #777777;font-size:16px;padding-top:1px'>" +
									remark + " / " + dateCurr + "</div></div>" +
									"<div style='width: 30%;float: right;'>" +
									"<button class='able-btn' onclick='chooseGrap(\""+msg[i].pid+"\")' >选择买家</button></div></div>";
							 }
							 $("#two").append(appendHtml);
			    		 }
			    	 });
			
			}
			
			//会议详情
			function showMeetingInfo(pid){
				document.formMeetingInfo.action="meetingPub/getByPid?pid="+pid;
				document.formMeetingInfo.submit();
			}
			
			//选择一个讲者
			function chooseGrap(pid){
				document.formMeetingInfo.action="pages/weixin/meetingPub/meetingGrapList.jsp?pid="+pid;
				document.formMeetingInfo.submit();
				
			}
			
			//会议发布
			function subMeetingPub(){
                var uid=$("#uid").val(); //uid 用户作者
                if(uid.length<1){
                	alert("您还未登录，无法执行此操作");
                	return ;
                }
                //非空验证  会议名称
                var ptitle=$("#ptitle").val();//会议名称
                if(ptitle.length<1){
                	alert("拍卖名称不能为空，请输入...");
                	return ;
                }
                //非空验证，会议时间
                var ptime=$("#ptime").val();
                if(ptime.length<1){
                	alert("请填入完整的拍卖会召开时间...");
                	return ;
                }

                //非空验证，下拉列表
                var ptype=$("#ptype");
                if("请选择"==ptype.val()){
                	alert("请选择对应的拍卖类别");
                	return ;
                }
                var ptypeVal=ptype.val();
                //得到备注描述
                var premark=$("#premark").val();
                //ajax提交添加
                $.ajax({
                	type:"post",
                	url:"meetingPub/add",
                	//,"ptype":ptype,"premark":premark,"uid":uid
                	data:{"ptime":ptime,"ptitle":ptitle,"ptype":ptypeVal,"premark":premark,"uid":uid},
                	success:function(msg){
                		if(msg=="2"){
                			alert("您还未登录，无法执行此操作");
                			WeixinJSBridge.call('closeWindow',5000);
                		}else{
	                		alert("拍卖品发布成功");
	                		$("input[type=reset]").trigger("click");
                		} 
                	}
                });
			}
			
		</script>
</head>
<body>
<form name="formMeetingInfo" action="" method="post">
</form>	
	<div data-role="page" id="pageDetail">
			<div style="padding:0px;background-color: #4E90C7;width: 100%;height:40px;line-height:40px;font-size:18px;text-align: center;cursor: pointer;" data-role="none">
				<div style="width: 50%;float: left;color: white;" onclick="showPubDiv();" id="one_tab">
					宝物发布
					<div style="border-right: 1px solid white;float: right;margin-top: 10px;height: 20px;"></div>
				</div>
				<div style="width: 50%;float: left;color: white;" onclick="showMyMeetings();" id="two_tab">我的宝物</div>
				<div style="border-top:5px solid white;width: 50%;float: left;" id="one_line"></div>
				<div style="border-top:5px solid #4E90C7;width: 50%;float: left;" id="two_line"></div>
			</div>
			<div id="one" class="ui-body-d ui-content" style="padding:0;display: block;width: 100%;">
				<font style="padding:10px 10px 10px 15px;display: block;color: #777777;">请填写拍卖相关信息</font>
				<div style="width: 100%;background-color: white;padding:10px 0 10px 0;">
					<input type="hidden" id="uid" value="<%=uid%>">
					<form id="pubForm" method="post">
						<div style="padding-right:15px;padding-left:15px">
							<label for="ptitle" class="font-label">拍卖名称:</label>
							<input name="ptitle" id="ptitle" placeholder="请输入宝物名称" ></input>
						</div>						
						<div  style="padding-right:15px;padding-left:15px">			
								<label for="ptime" class="font-label">拍卖日期</label>
	      					   <input type="datetime-local" name="ptime" id="ptime" />							
						</div>
						<div style="padding-right:15px;padding-left:15px">
							<label for="ptype" class="font-label">类别：</label>
							<select name="ptype" id="ptype">
							
							</select>
						</div>
						<div style="padding-right:15px;padding-left:15px">
							<label for="premark" class="font-label">拍卖介绍(选填，100字以内)</label>
							<textarea name="premark" id="premark" placeholder="请输入宝贝备注" maxlength="100" class="font-blue input-lightblue" style="box-shadow: none;"></textarea>
						</div>
						<div style="padding-right:15px;padding-left:15px">
							<input type="button" value="发布宝贝"  onclick="subMeetingPub()" id="btnId" />
							<span hidden><input type="reset" /></span>
						</div>
					</form>
				</div>
			</div>
			

	
	
			
	<div id="two" class="ui-body-d ui-content" style="padding: 0;display: none;width: 100%;">
		
	</div>
	</body>

</html>