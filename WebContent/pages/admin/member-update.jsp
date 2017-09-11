<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>" />
<meta  charset="UTF-8">
<link rel="Bookmark" href="/favicon.ico" >
<link rel="Shortcut Icon" href="/favicon.ico" />

<link rel="stylesheet" type="text/css" href="pages/admin/static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="pages/admin/static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="pages/admin/lib/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css" href="pages/admin/static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="pages/admin/static/h-ui.admin/css/style.css" />


<title>更新用户 - 赫氏拍卖行</title>
<meta name="keywords" content="赫氏拍卖行,赫氏网站后台模版,后台模版下载,后台管理系统模版,HTML后台模版下载">
<meta name="description" content="H-ui.admin v3.0，是一款由国人开发的轻量级扁平化网站后台模板，完全免费开源的网站后台管理系统模版，适合中小型CMS后台系统。">
</head>
<body>
<article class="page-container">
    <form action="users/update" method="post" class="form form-horizontal" id="form-member-add">
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>用户名：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" class="input-text" value="" placeholder="" id="uname" name="uname">
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>用户ID：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" class="input-text" value="" placeholder="" id="uid" name="uid">
            </div>
        </div>

         <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>地区：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" class="input-text" value="" placeholder="" id="city" name="city">
            </div>
        </div>

         <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>手机：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" class="input-text" value="" placeholder="" id="telphone" name="telphone">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>暗号：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" class="input-text" placeholder="天王盖地虎" name="email" id="email">
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>角色信息：</label>
            <div class="formControls col-xs-8 col-sm-9 skin-minimal">
                <div class="radio-box">
                    <input name="rolesName" type="radio" id="rolesName-1" checked>
                    <label for="rolesName-1">卖家</label>
                </div>
                <div class="radio-box">
                    <input type="radio" id="rolesName-2" name="rolesName">
                    <label for="rolesName-2">买家</label>
                </div>
            </div>
        </div>
        
        <div class="row cl">
            <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
                <input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
            </div>
        </div>
    </form>
</article>

<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="pages/admin/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="pages/admin/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="pages/admin/static/h-ui/js/H-ui.min.js"></script> 
<script type="text/javascript" src="pages/admin/static/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本--> 
<script type="text/javascript" src="pages/admin/lib/My97DatePicker/4.8/WdatePicker.js"></script>
<script type="text/javascript" src="pages/admin/lib/jquery.validation/1.14.0/jquery.validate.js"></script> 
<script type="text/javascript" src="pages/admin/lib/jquery.validation/1.14.0/validate-methods.js"></script> 
<script type="text/javascript" src="pages/admin/lib/jquery.validation/1.14.0/messages_zh.js"></script>
<script type="text/javascript">
$(function(){
    $('.skin-minimal input').iCheck({
        checkboxClass: 'icheckbox-blue',
        radioClass: 'iradio-blue',
        increaseArea: '20%'
    });
    
    $("#form-member-add").validate({
        rules:{
            uname:{
                required:true,
                minlength:2,
                maxlength:16
            },
            uid:{
                required:true,
            },
            city:{
                required:true,
            },
            telphone:{
                required:true,
            },
            email:{
                required:true,
            },         
        },
        onkeyup:false,
        focusCleanup:true,
        success:"valid",
        submitHandler:function(form){
            $(form).ajaxSubmit();
           // var index = parent.layer.getFrameIndex(window.name);
           // parent.$('.btn-refresh').click();
            //parent.layer.close(index);
           parent.location.href= "pages/admin/member-list.jsp";
        }
    });
});
</script> 
</body>
</html>