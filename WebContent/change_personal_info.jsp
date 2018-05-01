<%@page import="twkg.entity.User"%>
<%@page import="java.sql.Timestamp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改个人信息</title>
</head>
<body>
		<%
			User currentUser = (User) session.getAttribute("currentUser");
			int id = currentUser.getUserId();
		%>
		<form action="/twkg/Change_Personal_InfoServlet" method="post" id="Change_Personal_InfoForm">
		<table >
		
		<tr>
			<td>用户名</td><td><input type="text" id="uname" name="uname" value=${currentUser.userName}></td>
			<td><span id="namespan" class="span"></span></td>
		</tr>
		<tr>
			<td>邮箱</td><td><input type="text" id="uemail"  name="uemail" value=${currentUser.userEmail}></td>
			<td><span id="emailspan" class="span"></span></td>
		</tr>
		<tr>
			<td>生日</td><td><input type="text" id="ubirth" name="ubirth" value=${currentUser.userBirth}></td>
			<td><span id="birthspan" class="span"></span></td>
		</tr>
		<tr>
			<td>性别</td><td><input type="text" id="ugender" name="ugender" value=${currentUser.userGender}></td>
			<td><span id="genderspan" class="span"></span></td>
		</tr>
		<tr>
			<td>真实姓名</td><td><input type="text" id="urealname"  name="urealname" value=${currentUser.userRealName}></td>
			<td><span id="realnamespan" class="span"></span></td>
		</tr>
		<tr>
			<td>签名</td><td><input type="text" id="ubio" name="ubio" value=${currentUser.userBio}></td>
			<td><span id="biospan" class="span"></span></td>
		</tr>		
		
		</table>
		<input type="submit" id="tijiao" value="提交" /> 
		</form>
		<button style="cursor:pointer;" onclick="back()">返回</button>
</body>
<script type="text/javascript">

	
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

	var uemail = document.getElementById("uemail");
	var emailspan = document.getElementById("emailspan");
	uemail.oninput = function() {
		var regex = new RegExp(
				"^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$");
		var useremail = document.getElementById("uemail").value;

		if (useremail == "") {
			document.getElementById("emailspan").style.color = "red";
			document.getElementById("emailspan").innerHTML = "输入邮箱不能为空！";
		} else if (regex.test(useremail)) {
			document.getElementById("emailspan").style.color = "green";
			document.getElementById("emailspan").innerHTML = "邮箱格式正确！";
		} else {
			document.getElementById("emailspan").style.color = "red";
			document.getElementById("emailspan").innerHTML = "邮箱格式不正确！";
		}
	}
	
	function back(){
		window.history.back();
	}

</script>
</html>