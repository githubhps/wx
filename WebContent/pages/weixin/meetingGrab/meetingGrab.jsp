<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String roleId=request.getParameter("roleId");
String uId=request.getParameter("uid");
%>       
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">

		<title></title>
		<link rel="stylesheet" href="http://code.jquery.com/mobile/1.3.2/jquery.mobile-1.3.2.min.css">
		<link href="https://cdn.bootcss.com/jquery-mobile/1.4.5/jquery.mobile.theme.css" rel="stylesheet">

		<script src="http://code.jquery.com/jquery-1.8.3.min.js"></script>
		<script src="http://code.jquery.com/mobile/1.3.2/jquery.mobile-1.3.2.min.js"></script>
		<script type="text/javascript">
		  $(function(){
			//加载下拉框数据
			  loadAjaxSelect();
            //加载可抢单列表
			  getGrapAll("可竞价");
		  });
		  //下拉框选择事件
		  function showAllGrabs(ptype){
			  getGrapAll(ptype);
		  }
		  //首次加载，加载所有可抢单列表数据
		  function getGrapAll(ptype){
			var roleId=$("#roleId").val();
			var uId=$("#uId").val();
			  $.ajax({
				  type:"post",
				  url:"meetingPub/getAllGrap?ptype="+ptype+"&uid="+uId,
				  success:function(msg){
					  $("#grabNum").text(msg.length); //显示可抢单的列表
					  $("#grabDiv").empty();
					  var appendHtml=""; 
					  for(var i=0;i<msg.length;i++){
						
			        		var ptitle=msg[i].ptitle;
			        		var premark=msg[i].premark;
			        		var ptype=msg[i].ptype;
			        		
			        		appendHtml += "<div style='width: 100%;background-color: white;margin-top: -3px;padding:10px 10px 10px 15px;display: inline-block;'>" +
							"<div style='width: 70%;float: left;' onclick='showMeetingInfo(\"" + msg[i].pid + "\");'>" +
							"<div style='white-space: nowrap;overflow: hidden;text-overflow:ellipsis;display: block;font-size:18px;'>" +
							ptitle + "</div>" +
							"<div style='white-space: nowrap;overflow: hidden;text-overflow:ellipsis;display: block;color: #777777;font-size:16px;padding-top:1px'>" +
							ptype + " / " + premark + "</div></div>" +
							"<div style='width: 30%;float: right;'>" ;
							
						  	//如果是发单组的话，该按钮不可见
						  	//如果是抢单组的话，可见按钮，且能点击操作
						  	if("2"==roleId){
						  		appendHtml+="<button class='able-btn'  onclick='grabInfo(\""+msg[i].pid+"\",\""+uId+"\")'>点击竞价</button>";
						  	}
						  	if("3"==roleId){
						  		appendHtml+="<button class='able-btn'  onclick='grabInfo(\""+msg[i].pid+"\",\""+uId+"\")'>点击竞价</button>";
						  	}
							appendHtml+="</div></div>";
					  }
					  
			        	$("#grabDiv").append(appendHtml);
					  
				  }		  
				  
			  });
		  }
		  //抢单页面
		  function grabInfo(pid,uid){
			  document.fromGrabName.action="grap/grapAdd?pid="+pid+"&uid="+uid;
			  document.fromGrabName.submit();
		  }
		  
		   function loadAjaxSelect(){
		    	 //ajax ptitle下拉框赋值
		    	 $.ajax({
		    		 type:"post",
		    		 url:"meetingType/list",
		    		 success:function(msg){		    			 
					   var appendHtml="";
		    			 for(var i=0;i<msg.length;i++){
		    				 //<option value="数据库">数据库</option>
		    			   appendHtml+="<option value="+msg[i].tname+">"+msg[i].tname+"</option>";
		    			 }
		    			 $("#selectStatus").append(appendHtml);
		    		 }
		    	 });
		     }
		  
		  
		  
			function showPubDiv() {
				$("#two_line").css("border-top", "5px solid #4E90C7");
				$("#one_line").css("border-top", "5px solid white");
				$("#two").css("display", "none");
				$("#one").css("display", "block");
			}

			function showMyMeetings() {

				$("#two_line").css("border-top", "5px solid white");
				$("#one_line").css("border-top", "5px solid #4E90C7");
			
					$("#one").css("display", "none");
					$("#two").css("display", "block");

					$("#two").empty();
					var uId=$("#uId").val();
					//显示加载中..
			    	//$.mobile.loading('show', {text: '加载中...',textVisible: true});	
					
					$.ajax({  
						type:"post",
						url:"grap/getByUid",
						data:{"uid":uId},
						success:function(msg){
							
							var appendHtml = "<font style='padding: 10px 10px 10px 15px;display: block;color: #777777;'>您共抢了"+msg.length+"种宝物</font>";
							
							var ptitle="";
							var premark="";
							var ptype="";
							var gstatus="";
							for(var i=0;i<msg.length;i++){
								
								 ptitle=msg[i].meetingpub.ptitle;
				        		 premark=msg[i].meetingpub.premark;
				        		 ptype=msg[i].meetingpub.ptype;
				        		 gstatus=msg[i].gstatus;
				        		
								appendHtml += "<div style='width: 100%;background-color: white;margin-top: -3px;padding:10px 10px 10px 15px;display: inline-block;'>" +
								"<div style='width: 70%;float: left;' onclick='showMeetingInfo(\"" + 1 + "\");'>" +
								"<div style='white-space: nowrap;overflow: hidden;text-overflow:ellipsis;display: block;font-size:18px;'>" +
								ptitle + "</div>" +
								"<div style='white-space: nowrap;overflow: hidden;text-overflow:ellipsis;display: block;color: #777777;font-size:16px;padding-top:1px'>" +
								ptype + " / " + premark + "</div></div>" +
								"<div style='width: 30%;float: right;'>";
								
								if(gstatus=="0"){	
									appendHtml+="<button class='able-btn' >匹配中</button>"
								}else if(gstatus=="1"){
									appendHtml+="<button class='able-btn' >竞价失败</button>"
								}else if(gstatus=="2"){
									appendHtml+="<button class='able-btn' >竞价成功</button>"
								}	
								appendHtml+="</div></div>";
							}
							$("#two").append(appendHtml);
						//	$.mobile.loading('hide'); 
						}
						
					});

			
			}
		</script>	
	</head>

	<body>
	<!-- 进入抢单页面 -->
	<form name="fromGrabName" action="" method="post">
		<input type="hidden" id="roleId" value="<%=roleId%>">
		<input type="hidden" id="uId" value="<%=uId%>">
	</form>
	
	
	<div data-role="page" id="pageDetail">
			<div style="padding:0px;background-color: #4E90C7;width: 100%;height:40px;line-height:40px;font-size:18px;text-align: center;cursor: pointer;" data-role="none">
				<div style="width: 50%;float: left;color: white;" onclick="showPubDiv();" id="one_tab">
					参与的竞价
					<div style="border-right: 1px solid white;float: right;margin-top: 10px;height: 20px;"></div>
				</div>
				<div style="width: 50%;float: left;color: white;" onclick="showMyMeetings();" id="two_tab">我的竞价</div>
				<div style="border-top:5px solid white;width: 50%;float: left;" id="one_line"></div>
				<div style="border-top:5px solid #4E90C7;width: 50%;float: left;" id="two_line"></div>
			</div>
	<div id="one" class="ui-body-d ui-content" style="padding:0;display: block;width: 100%;">
		<div style="padding:0 0 0 15px;display: block;width: 55%;float: left;line-height: 60px;color: #777777;">
			可参与<font id="grabNum"></font>场拍卖会竞价
		</div>
		<div style="width: calc(45% - 15px);float: right;">
			<select id="selectStatus" onchange="showAllGrabs(this.value)">
				<option selected='selected' value='可竞价' >可竞价</option>
			</select>
		</div>
		<div id="grabDiv" style="width: 100%;float: left;">
		</div>
	</div>
			
	<div id="two" class="ui-body-d ui-content" style="padding: 0;display: none;width: 100%;">
		
	</div>
	</body>

</html>