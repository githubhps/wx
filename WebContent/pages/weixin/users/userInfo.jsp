<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">

<title>个人信息页面</title>
<link rel="stylesheet" href="http://code.jquery.com/mobile/1.3.2/jquery.mobile-1.3.2.min.css">
<link href="https://cdn.bootcss.com/jquery-mobile/1.4.5/jquery.mobile.theme.css" rel="stylesheet">
<script src="http://code.jquery.com/jquery-1.8.3.min.js"></script>
<script src="http://code.jquery.com/mobile/1.3.2/jquery.mobile-1.3.2.min.js"></script>
<script src="https://cdn.bootcss.com/jquery-validate/1.16.0/jquery.validate.js"></script>
<script src="https://cdn.bootcss.com/jquery.form/4.2.1/jquery.form.js"></script>
		
<style type="text/css">
	body {
		font-family:微软雅黑,arial;
		font-size:16px;
		padding:0px;
		margin:0 auto;
	}
	
	.font-blue{
		color:#4E90C7!important;
	}
	
	.input-lightblue{
		background-color: #E5F2FD!important;
	}
	
	.font-label{
		color: black!important;
		font-size: 19px!important;
	}
	
	/* 验证错误提示信息*/ 
	form label.error{color: Red;padding:0px;margin:0px;line-height:0px;white-space: nowrap;} 
</style>
<script type="text/javascript">
	function close(){
		WeixinJSBridge.call('closeWindow');
	}
	function updateUsers(){
		
		var msg = "您真的确定要更新吗？\n\n请确认！"; 
		 if (confirm(msg)==true){ 
		 	//此处执行AJAX
			 ajaxUpdate();
		 }else{ 
		  return false; 
		 } 
		
	
	}
	
	function ajaxUpdate(){
		//先加非空验证
		var uid=$("#uid").val();
		var city=$("#city").val();
		var telPhone=$("#cellphone").val();
		var uname=$("#name").val();
		$.ajax({
			type:"post",
			url:"users/update",
			data:{"uid":uid,"city":city,"telphone":telPhone,"uname":uname},
			success:function(msg1){
				
				var msg = "数据更新成功\n\n请点击确认关闭！"; 
				 if (confirm(msg)==true){ 
				 	//此处执行AJAX
				 	close();
				 }else{ 
				 
				 } 
			}
			
		});
	}
	
</script>
</head>
<body>
<div data-role="page" id="pageMain">
	<div data-role="header" data-theme="b"  data-position="fixed">
		<a href="javascript:close()" >返回</a>
		<h1>会员信息</h1>
	</div>
<div style="padding-top:20px;padding-bottom: 20px">
	<form id="form"  method="post">
		<input type="hidden" id="openid" name="openid" value='${openid}'>
		<input type="hidden" id="uid" value="${users.uid}">
		<!-- <div>基本信息</div> -->
		<div>
		     <div style="padding-right:20px;padding-left:20px">
		     	<label for="name"  class="font-label">会员名称(<font color="red">必填</font>)</label>
				<input type="text" name="name" id="name" value="${users.uname}" placeholder="请输入您的真实姓名" class="required font-blue input-lightblue">
		     </div>
			
			<div class="ui-grid-a" style="padding-right:20px;padding-left:20px">
				<div class="ui-block-b" style="padding-left:10px">
					<label for="city"  class="font-label">城市</label>
		    		<input type="text" name="city" id="city" value="${users.city }" placeholder="请输入您所在城市" class="font-blue input-lightblue">
				</div>
			</div>

			
			<div style="padding-right:20px;padding-left:20px">
				<label for="cellphone"  class="font-label">手机号(<font color="red">必填</font>)</label>
		    	<input type="text" name="cellphone" id="cellphone" value="${users.telphone}" placeholder="请输入您的手机号" class="required isPhone font-blue input-lightblue" >
			
		    	<label for="section"  class="font-label">专属邀请码(不可更改)</label>
		    	<input type="text" id="email" value="${users.email}"  class="font-blue input-lightblue" readonly="readonly">
			</div>
					
			
	    </div>
	    
	    <div style="padding-right:20px;padding-left:20px;padding-top:20px;">
	    	<input id="updateBtn" type="button" value="更新" onclick="updateUsers()"  style="padding:10px;background: #e57330;text-shadow: none;opacity:100;color:white;font-size:20px;text-indent:0px;font-family:微软雅黑;" >
	    </div>
	</form>
</div>
</div>
</body>
</html>