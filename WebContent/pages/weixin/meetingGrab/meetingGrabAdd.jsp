<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>       
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
				
		<title></title>
		<link rel="stylesheet" href="http://code.jquery.com/mobile/1.3.2/jquery.mobile-1.3.2.min.css">
		<link href="https://cdn.bootcss.com/jquery-mobile/1.4.5/jquery.mobile.theme.css" rel="stylesheet">
		
		<script src="http://code.jquery.com/jquery-1.8.3.min.js"></script>
		<script src="http://code.jquery.com/mobile/1.3.2/jquery.mobile-1.3.2.min.js"></script>
	
	<script type="text/javascript">
		function addMeetingGrab(){
			var gremark=$("#gremark").val();
		    if(gremark.length<1){
		    	alert("请出价，只有一次竞拍机会");
		    	return ;
		    }
		    
		    $.ajax({  
				type:"post",
				url:"grap/grapAddTo",
				data:{"pid":$("#ppid").val(),"uid":$("#uuid").val(),"gremark":$("#gremark").val()},
				success:function(msg){
					if(msg == "1"){
						location.href='wx/isMeetingGrab'
					}
				}    
		   
		    });
		   //document.grabFormName.submit();
		}
	
	</script>
			
</head>
	
	
	<body>
		
<div data-role="page" id="pageDetail" style="background-color: white;">
	<div data-role="header" data-theme="c" style="background-color: #4E90C7;" data-position="fixed">
		<a href="javascript:back()"  data-role="button" >返回</a>
	    <h1>拍卖宝贝</h1>
	</div>
	<div id="one" class="ui-body-d ui-content" style="padding:1em 0 1em 0;">
		 
		 <div style="padding-right:15px;padding-left:15px">
			<label for="date_index" class="font-label">宝贝编号</label>
			<input type="text" id="date_index" value="${meetingPub.pcode}" class="font-blue input-lightblue" readonly="readonly">
		</div>
		<div style="padding-right:15px;padding-left:15px">
			<label for="meeting_date" class="font-label">成交日期</label>
			<input type="text" name="meeting_date" id="meeting_date" value="${meetingPub.ptime}"  class="required font-blue input-lightblue" readonly="readonly">
		</div>

	    
		<div class="ui-grid-a" style="padding-right:15px;padding-left:15px;">
			<div class="ui-block-a" style="padding-right:10px">
					<label for="m_name" class="font-label">拍卖者姓名</label>
		    		<input type="text" value="${users.uname }"  id="m_name" class="font-blue input-lightblue" readonly="readonly" />
		    </div>
			<div class="ui-block-b" style="padding-left:10px">
				<label for="m_phone" class="font-label">联系电话 </label>
	    		<input type="text" value="${users.telphone }"  id="m_phone" class="font-blue input-lightblue" readonly="readonly" />
			</div>	
		</div>
<form name="grabFormName" action="grap/grapAddTo" method="post">		
      <input type="hidden" name="pid" value="${meetingPub.pid}" id="ppid" />
      <input type="hidden" name="uid" value="${uid}" id="uuid" />
		<div style="padding-right:15px;padding-left:15px">
				<label for="gremark" class="font-label">请报价：(仅有一次机会)</label>
				<textarea name="gremark" id="gremark" placeholder="请输入你的报价" maxlength="100" class="font-blue input-lightblue" style="box-shadow: none;"></textarea>						
	    </div>
	</form>	
			    
		<div style="padding-right:15px;padding-left:15px">
			    <input type="button" value="确认竞价" onclick="addMeetingGrab()"   />
		</div>
	</div>
</div>		
		
	</body>
</html>
