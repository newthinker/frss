<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <title>故障受理服务系统</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="">
	<meta http-equiv="description" content="">
</head>
<style>
#content {
	width:800px;
	height:600px;
	position:absolute;
	top:50%;
	margin-top:-300px;
	left:50%;
	margin-left:-400px;
}
TD {
	font-size: 9pt;
	/*color: #CAFFFF;*/
}
.input7 {
	BACKGROUND: url(img/inputend.gif) repeat-x left center; 
	BORDER-BOTTOM-WIDTH: 0px; 
	WIDTH: 100px; 
	COLOR: #ff0000; 
	HEIGHT: 18px; 
	BORDER-RIGHT-WIDTH: 0px;
}
.text {
	text-align: center;
	font-size: 14px;
	font-weight: bold;
	color: #003068;
	font-family: "微软雅黑";
}
.button {
	width: 50px;
	height: 24px;
	font-size: 14px;
	font-weight: bold;
	background-image: url(img/buttonbg.gif);
	background-repeat: repeat-x;
	border: 1px solid #CAFFFF;
	color: #FFF;
	font-family: "微软雅黑";
}
</style>
<script language="JavaScript">
	/***********************************************************************
	* 判断一个字符串中是否含有下列非法字符
	*/
	voidChar = " '\"><";
	function isValidString(szStr){
		for(var i = 0 ; i < voidChar.length; i ++){
			if(szStr.indexOf(voidChar.charAt(i)) > -1){
				return false;
			}
		}
		return true;
	}
	function checkLogin()
	{
		if (document.form1.name.value == "")
		{
			alert("请输入用户名!");
			document.form1.name.focus();
			return false;
		}
		else if (!isValidString(document.form1.name.value))
		{
			alert("用户名出现无效字符!");
			document.form1.name.focus();
			return false;
		}
		if (document.form1.pwd.value == "" )  
		{
			alert("请输入密码!");
			document.form1.pwd.focus();
			return false;
		}
	}
</script>
<body style="overflow:auto;">
	<%
	Integer error = (Integer)request.getSession().getAttribute("error");
	if(error != null && error != 0)
	{
		%><script>alert('用户名或密码错误!')</script><%
	}
	%>
<div id="content" align="center">
	<form name="form1" action="LoginManagerServlet" method="post" onSubmit="return checkLogin()">
		<!-- <table border="0" width="866px" style="background-color: rgb(0, 128, 192); top: 315px; height: 165px;" cellspacing="0" cellpadding="0"> -->
		<table width="800px" height="600px" border="0" cellspacing="0" cellpadding="0">
			<tr height="515px">
				<td background="img/login.png">&#160;</td>
			</tr>
			<tr>
				<td>
					<table height="30px" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="210px">&#160;</td>
							<td class="text" width="60">用户：</td>
							<td width="100"><input class="input7" type="text" name="name" size="18"/></td>
							<td class="text" width="60">密码：</td>
							<td width="100"><input class="input7" type="password" name="pwd" size="20"/></td>
							<td width="60" align="center"><input type="submit" class="button" value="登录"/></td>
							<td width="210px">&#160;</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr height="40px">
				<td align="center"><font color="003068" style="font-size:14px">【推荐IE8.0以上版本 Flash10.1以上版本 浏览分辨率:1280×800】</font></td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>