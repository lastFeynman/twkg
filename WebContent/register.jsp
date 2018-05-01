<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>注册</title>
</head>
<link rel="stylesheet" type="text/css" href="/twkg/css/register.css" />

<body>
	<div id="container">
		<div id="header">
			<div id="">
				<img src="/twkg/images/logo.jpg" /> 
				<span id="title"> 贪玩K歌 </span> 
				<a href="/twkg/index.jsp">首页</a>
			</div>
		</div>
		<div id="register">
			<img src="/twkg/images/welcome_logo.jpg" />
			<div id="form">
				<form action="/twkg/RegisterServlet" method="post" id="registerForm">
					<div class="input">
						用户名：<input type="text" name="username" id="uname" value placeholder="取个昵称，长度不超过8位" /><br />
						<span id="namespan" class="span"></span>
					</div>
					<div class="input">
						邮箱：<input type="text" name="email" id="email"  value placeholder="输入您的邮箱" /><br />
						<span id="emailspan" class="span"></span>
					</div>
					<div class="input">
						密码：<input type="password" name="password" id="psd" value placeholder="设置密码，6~18位字母和数字" /><br />
						<span id="psdspan" class="span"></span>
					</div>
					<div class="input">
						重复密码：<input type="password" name="repsd" id="repsd" value placeholder="再次输入密码" /><br />
						<span id="repsdspan" class="span"></span>
					</div>
					<input type="button" name="" id="zhuce" value="注册" /> 
					<input type="button" name="" id="reset" value="重置" onclick="window.location.reload()"/>
				</form>
			</div>
		</div>
		<div id="footer">
			<span><a href="">贪玩K歌</a> | <a href="">服务条款</a> | <a href="">关于我们 </a></span><br /> 
			<span> Copyright (C) 2018-9999 All rights reserved. </span>
		</div>
	</div>
</body>
<script type="text/javascript">
var err='<%=request.getParameter("error")%>';
	if(err == '1'){
		alert("注册失败！邮箱已被使用！")
	}
	if (err == '2') {
		alert("注册失败！该用户已存在！")
	}

	
	var uname = document.getElementById("uname");
	var namespan = document.getElementById("namespan");
	uname.oninput = function() {
		var regex = new RegExp("^[A-Za-z\u4e00-\u9fa5]{1,8}$");
		var username = document.getElementById("uname").value;
		if (username == "") {
			document.getElementById("namespan").style.color = "red";
			document.getElementById("namespan").innerHTML = "输入昵称不能为空！";
		}else  if (regex.test(username)) {
			document.getElementById("namespan").style.color = "green";
			document.getElementById("namespan").innerHTML = "昵称格式正确！";
		} else {
			document.getElementById("namespan").style.color = "red";
			document.getElementById("namespan").innerHTML = "昵称必须为汉字或字母，长度最大为8！";
		}
	}

	var email = document.getElementById("email");
	var emailspan = document.getElementById("emailspan");
	email.oninput = function() {
		var regex = new RegExp(
				"^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$");
		var address = document.getElementById("email").value;

		if (address == "") {
			document.getElementById("emailspan").style.color = "red";
			document.getElementById("emailspan").innerHTML = "输入邮箱不能为空！";
		} else if (regex.test(address)) {
			document.getElementById("emailspan").style.color = "green";
			document.getElementById("emailspan").innerHTML = "邮箱格式正确！";
		} else {
			document.getElementById("emailspan").style.color = "red";
			document.getElementById("emailspan").innerHTML = "邮箱格式不正确！";
		}
	}

	var psd = document.getElementById("psd");
	var psdspan = document.getElementById("psdspan");
	psd.oninput = function() {
		var regex = new RegExp("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z_]{6,18}$");
		var password = document.getElementById("psd").value;
		if (password == "") {
			document.getElementById("psdspan").style.color = "red";
			document.getElementById("psdspan").innerHTML = "输入密码不能为空！";
		} else if (regex.test(password)) {
			document.getElementById("psdspan").style.color = "green";
			document.getElementById("psdspan").innerHTML = "密码格式正确！";
		} else {
			document.getElementById("psdspan").style.color = "red";
			document.getElementById("psdspan").innerHTML = "密码为6~18位字母和数字！";
		}
	}

	var repsd = document.getElementById("repsd");
	var repsdspan = document.getElementById("repsdspan");
	repsd.oninput = function() {
		var repassword = document.getElementById("repsd").value;
		var password = document.getElementById("psd").value;
		if (repassword == password) {
			document.getElementById("repsdspan").style.color = "green";
			document.getElementById("repsdspan").innerHTML = "密码一致！";
		} else {
			document.getElementById("repsdspan").style.color = "red";
			document.getElementById("repsdspan").innerHTML = "两次输入的密码不一致！";
		}
	}
	
	var zhuce = document.getElementById("zhuce");
	var registerForm = document.getElementById("registerForm");
	zhuce.onclick = function(){
		if(namespan.innerHTML!="昵称格式正确！"){
			uname.focus();
		}else if(emailspan.innerHTML!="邮箱格式正确！"){
			email.focus();
		}else if(psdspan.innerHTML!="密码格式正确！"){
			psd.focus();
		}else if(repsdspan.innerHTML!="密码一致！"){
			repsd.focus();
		}else{
			registerForm.submit();
		}
	}
</script>
</html>