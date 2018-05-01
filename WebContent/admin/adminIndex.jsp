<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理员首页</title>
</head>
<style>
	body{
		background-attachment: fixed;
		background-repeat: no-repeat;
		background-size: cover;
	}
</style>
<body background="/twkg/images/1.jpg">


<center><a href="/twkg/login.jsp" style="color:white;">切换用户</a></center>
<center><a href="/twkg/index.jsp" style="color:white;">用户主页</a></center><br>
	<fieldset style="width:600px;position:relative;left:300px;">
		<legend><h1 style="color:white;" align="center">欢迎您，${currentUser.userName}</h1></legend>
		<form method="post" style="float:left;">
			<a target="_blank" href="editUser.jsp"><input type="button"
				value="添加用户" id="addUser" /></a><br /> <br /> <a target="_blank"
				href="editSong.jsp"><input type="button" value="添加歌曲"
				id="addSong" /></a>
		</form>

	
		<form target="_blank" action="adminSearch.jsp" method="post" style="position:relative;left:200px;">
			搜索用户 <input type="text" id="seUser" name="searchContent" /> <input
				type="text" name="searchType" value="user"
				style="position: absolute; visibility: hidden;"> <input
				type="submit" value="搜索" />
		</form>
		<form target="_blank" action="adminSearch.jsp" method="post" style="position:relative;left:200px;">
			搜索原唱<input type="text" id="seSing" name="searchContent"> <input
				type="text" name="searchType" value="song"
				style="position: absolute; visibility: hidden;"> <input
				type="submit" value="搜索" />
		</form>
		<form target="_blank" action="adminSearch.jsp" method="post" style="position:relative;left:200px;">
			搜索翻唱<input type="text" id="seRe" name="searchContent" /> <input
				type="text" name="searchType" value="cover"
				style="position: absolute; visibility: hidden;"> <input
				type="submit" value="搜索" />

		</form>
	</fieldset>
	

	
	<table cellspacing="0" border="1px black solid" style="position:relative;left:500px;top:400px;">
	<tr><th colspan="2">管理员个人信息</th></tr>
		<tr><th>用户Id</th><td>${currentUser.userId}</td></tr>
		<tr><th>用户名</th><td>${currentUser.userName}</td></tr>
		<tr><th>用户邮箱</th><td>${currentUser.userEmail}</td></tr>
		<tr><th>用户生日</th><td>${currentUser.userBirth}</td></tr>
		<tr><th>用户性别</th><td>${currentUser.userGender}</td></tr>
		<tr><th>用户姓名</th><td>${currentUser.userRealName}</td></tr>
	</table>
    	
    
</body>
</html>