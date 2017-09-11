<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>" />
<meta  charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">

		<title></title>
		<link rel="stylesheet" href="http://code.jquery.com/mobile/1.3.2/jquery.mobile-1.3.2.min.css">
		<link href="https://cdn.bootcss.com/jquery-mobile/1.4.5/jquery.mobile.theme.css" rel="stylesheet">
		<script src="http://code.jquery.com/jquery-1.8.3.min.js"></script>
		<script src="http://code.jquery.com/mobile/1.3.2/jquery.mobile-1.3.2.min.js"></script>
		<script src="https://cdn.bootcss.com/jquery-validate/1.16.0/jquery.validate.js"></script>
		<script src="https://cdn.bootcss.com/jquery.form/4.2.1/jquery.form.js"></script>

<style type="text/css">
	body {
		font-family:"微软雅黑,Arial";
		font-size:18px;
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
</style>
</head>
<body>
<div data-role="page" id="pageMain" style="">
	<div data-role="header" data-theme="c" style="background-color: #4E90C7;" data-position="fixed">
		<a href="javascript:WeixinJSBridge.call('closeWindow');" class="ui-btn-left ui-btn ui-btn-inline ui-mini ui-corner-all ui-btn-icon-left ui-icon-back" style="background-color: #3778ae;">返回</a>
		<h1>拍卖发布排行榜</h1>
	</div>
	<div data-role="content" style="padding: 0;text-align:center;width: 100%;">
		<c:forEach items="${list}" var="rank" varStatus="status">
			<div style="padding-top:0.7em;text-align:center;width: 100%;line-height:50px;border-bottom: 1px solid #86D0F5;height: 60px;">
				<div style="width: 40px;float: left;font-size:30px;padding-left: 10px;">
				<c:if test="${ status.index == 0}">
					<img style="height:40px;margin-left: 5px;"src="images/ic_1.png">
				</c:if>
				<c:if test="${ status.index == 1}">
					<img style="height:40px;margin-left: 5px;"src="images/ic_2.png">
				</c:if>
				<c:if test="${ status.index == 2}">
					<img style="height:40px;margin-left: 5px;"src="images/ic_3.png">
				</c:if>
				<c:if test="${ status.index > 2}">
					${ status.index+1}
				</c:if>
				</div>
				<div style="border:0px solid blue;width:50px;height:50px;float: left;padding-left: 10px;">
		  			<img style="width:50px;height:50px;border-radius:25px;"src="${rank.headimgurl}">
				</div>
				<div style="float: left;color: black;font-size:20px;width:auto;max-width:40%;padding-left: 15px;white-space: nowrap;overflow:hidden;text-overflow:ellipsis;text-algin:left;">
					${rank.nicknamr}
				</div>
				<div style="text-align: right;padding-right:10px;white-space: nowrap;">
					<font style="font-size:28px;color:#E57330">${rank.count}</font> 场
				</div>
			</div>
		</c:forEach>
	</div>
</div>


	</body>
</html>
