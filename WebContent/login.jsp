<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录</title>
</head>
<link rel="stylesheet" type="text/css" href="/twkg/css/login.css" />

<body>
	<div id="container">
		<div id="header">
			<div id="">
				<img src="/twkg/images/logo.jpg" /> 
				<span id="title"> 贪玩K歌 </span> 
				<a href="/twkg/index.jsp">首页</a>
			</div>
		</div>
		<div id="login">
			<img src="/twkg/images/welcome_logo.jpg" />
			<div id="form">

				<form action="/twkg/LoginServlet" method="post">
					<div class="input">
						用户名：<input type="text" name="username" value placeholder="输入用户名" />
					</div>
					<div class="input">
						密码：<input type="password" name="password" id="psd" value placeholder="输入密码" />
					</div>
					<div id="autologin">
						<input type="checkbox" name="autologin" id="" value="1" />记住我
						<span id="">
								不是自己的电脑不要勾选此项
						</span>
					</div>
					<input type="submit" id="denglu" value="登录" />
				</form>

				<input type="button" name="" id="zhuce" value="注册" onclick="location.href='register.jsp'" /> 
				<input type="button" name="" id="zhaohui" value="找回密码" />

			</div>
		</div>
		<div id="footer">
			<span id=""> <a href="">贪玩K歌</a> | <a href="">服务条款</a> | <a href="">关于我们 </a></span><br /> 
			<span id="">Copyright (C) 2018-9999 All rights reserved. </span>
		</div>
	</div>
</body>
<script type="text/javascript">
var err='<%=request.getParameter("error")%>';
	if (err == '1') {
		alert("登录失败！账号或密码错误！")
	} else if (err == '3') {
		alert("注册成功！")
	}
</script>
</html>