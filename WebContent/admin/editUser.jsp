<%@page import="twkg.dao.IUserDao"%>
<%@page import="twkg.dao.factory.DaoFactory"%>
<%@page import="twkg.entity.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
	String editUserIdStr = request.getParameter("editUserId");
	int editUserId = 0;
	if (editUserIdStr != null) {
		try {
			editUserId = Integer.parseInt(editUserIdStr);
		} catch (Exception e) {
			request.setAttribute("errMsg", "请求参数错误：editUserId");
			request.getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
			return;
		}
	}
	DaoFactory daoFactory = DaoFactory.getDaoFactory();
	IUserDao userDao = daoFactory.getUserDao();
	User user = userDao.findUserByUserId(editUserId);
	request.setAttribute("user", user);
%>
<title>
	<%
		if (editUserIdStr != null) {
	%> 编辑 <%
		} else {
	%> 添加 <%
		}
	%>
</title>
</head>
<body style="background-color: #FFEB3B">
	<center><h2>欢迎来到用户管理界面</h2></center>
	<form action="EditUserServlet" id="fu" method="post">
		<table width="100%">
			<tr>
				<td align="right">用户名:</td>
				<td><input type="text" id="usern" name="usern" /></td>
				<span id="namespan"></span>
			</tr>
			<tr>
				<td align="right">密码:</td>
				<td><input type="password" id="pas" name="pas" /></td>
				<span id="psdspan"></span>
			</tr>
			<tr>
				<td align="right">重复密码:</td>
				<td><input type="password" id="repas" name="repas" /></td>
				<span id="repsdspan"></span>
			</tr>
			<tr>
				<td align="right">姓名:</td>
				<td><input type="text" id="name" name="name" /></td>
				<span id="realnamespan"></span>
			</tr>
			<tr>
				<td align="right">管理员:</td>
				<td><input type="radio" name="adm" id="isad" />是 <input
					type="radio" name="adm" id="notad" checked />否</td>
			</tr>
			<tr>
				<td align="right">性别:</td>
				<td><input type="radio" name="sex" id="man" />男 <input
					type="radio" name="sex" id="woman" />女</td>
			</tr>
			<tr>
				<td align="right">邮箱:</td>
				<td><input type="text" id="mail" name="mail" /></td>
				<span id="emailspan"></span>
			</tr>
			<tr>
				<td align="right"></td>
				<td>
				<%if(editUserId==0) {%>
				<input type="button" value="&nbsp;&nbsp;&nbsp;添&nbsp;&nbsp;加&nbsp;&nbsp;&nbsp;" id="modifyu">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<%}else{ %>
				<input type="button" value="&nbsp;&nbsp;&nbsp;修&nbsp;&nbsp;改&nbsp;&nbsp;&nbsp;" id="modifyu">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<%} %>	
					<input type="button"
					value="&nbsp;&nbsp;&nbsp;重&nbsp;&nbsp;置&nbsp;&nbsp;&nbsp;"
					id="reflashu"></td>
			</tr>
		</table>
	</form>
	<button style="cursor:pointer;" onclick="back()">返回</button>
</body>
<script type="text/javascript">
var usern=document.getElementById("usern");
var pas=document.getElementById("pas");
var repas=document.getElementById("repas");
var rname=document.getElementById("name");
var mail=document.getElementById("mail");
var modifyu=document.getElementById("modifyu");
var reflashu=document.getElementById("reflashu");
var isad=document.getElementById("isad");
var notad=document.getElementById("notad");
var man=document.getElementById("man");
var woman=document.getElementById("woman");
var editForm = document.getElementById("fu");

var namespan = document.getElementById("namespan");
var psdspan = document.getElementById("psdspan");
var repsdspan = document.getElementById("repsdspan");
var realnamespan = document.getElementById("realnamespan");
var emailspan = document.getElementById("emailspan");

<%if (editUserIdStr == null) {%>
reflashu.onclick=function(){
	usern.value="";
	pas.value="";
	repas.value="";
	rname.value="";
	mail.value="";
	notad.checked=true;
	man.checked=false;
	woman.checked=false;
	
	namespan.innerHTML = "";
	psdspan.innerHTML = "";
	repsdspan.innerHTML = "";
	realnamespan.innerHTML = "";
	emailspan.innerHTML = "";
}
<%} else {%>
	usern.value="${user.userName}";
	pas.value="";
	repas.value="";
	rname.value="${user.userRealName}";
	mail.value="${user.userEmail}";
	isad.checked=${user.isAdmin()};
	notad.checked=${!user.isAdmin()};
	<%if (user.getUserGender() == null) {%>
	man.checked=false;
	woman.checked=false;
	<%} else if (user.getUserGender().equals("男")) {%>
	man.checked=true;
	<%} else {%>
	woman.checked=true;
	<%}%>
	
	reflashu.onclick=function(){
		usern.value="${user.userName}";
		pas.value="";
		repas.value="";
		rname.value="${user.userRealName}";
		mail.value="${user.userEmail}";
		isad.checked=${user.isAdmin()};
		notad.checked=${!user.isAdmin()};
		<%if (user.getUserGender() == null) {%>
		man.checked=false;
		woman.checked=false;
		<%} else if (user.getUserGender().equals("男")) {%>
		man.checked=true;
		<%} else {%>
		woman.checked=true;
		<%}%>
		
		namespan.innerHTML = "";
		psdspan.innerHTML = "";
		repsdspan.innerHTML = "";
		realnamespan.innerHTML = "";
		emailspan.innerHTML = "";
	}
<%}%>


usern.oninput = function() {
	var regex = new RegExp("^[A-Za-z\u4e00-\u9fa5]{1,8}$");
	var username = document.getElementById("usern").value;
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


pas.oninput = function() {
	var regex = new RegExp("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z_]{6,18}$|^$");
	var password = document.getElementById("pas").value;
	if (regex.test(password)) {
		document.getElementById("psdspan").style.color = "green";
		document.getElementById("psdspan").innerHTML = "密码格式正确！";
	} else {
		document.getElementById("psdspan").style.color = "red";
		document.getElementById("psdspan").innerHTML = "密码为6~18位字母和数字！";
	}
}


repas.oninput = function() {
	var repassword = document.getElementById("repas").value;
	var password = document.getElementById("pas").value;
	if (repassword == password) {
		document.getElementById("repsdspan").style.color = "green";
		document.getElementById("repsdspan").innerHTML = "密码一致！";
	} else {
		document.getElementById("repsdspan").style.color = "red";
		document.getElementById("repsdspan").innerHTML = "两次输入的密码不一致！";
	}
}


rname.oninput = function() {
	var regex = new RegExp("^[\u4e00-\u9fa5\·]{1,10}$|^[a-zA-Z·]{1,20}$|^$");
	var name = document.getElementById("name").value;

	if (regex.test(name)) {
		document.getElementById("realnamespan").style.color = "green";
		document.getElementById("realnamespan").innerHTML = "姓名格式正确！";
	} else {
		document.getElementById("realnamespan").style.color = "red";
		document.getElementById("realnamespan").innerHTML = "姓名格式不正确！";
	}
}


mail.oninput = function() {
	var regex = new RegExp(
			"^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$");
	var address = document.getElementById("mail").value;

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


modifyu.onclick=function(){
	<%if (editUserIdStr != null) {%>
		if(namespan.innerHTML!="昵称格式正确！" && namespan.innerHTML!=""){
			usern.focus();
		}else if(psdspan.innerHTML!="密码格式正确！" && psdspan.innerHTML!=""){
			pas.focus();
		}else if(repsdspan.innerHTML!="密码一致！" && repsdspan.innerHTML!=""){
			repas.focus();
		}else if(realnamespan.innerHTML!="姓名格式正确！" && realnamespan.innerHTML!=""){
			rname.focus();
		}else if(emailspan.innerHTML!="邮箱格式正确！" && emailspan.innerHTML!=""){
			mail.focus();
		}else{
			var editUserId = document.createElement("input");
			editUserId.name = "editUserId";
			editUserId.value = <%=editUserId%>;
			editUserId.type = "hidden";
			editForm.appendChild(editUserId);
	<%} else {%>
		if(namespan.innerHTML!="昵称格式正确！"){
			usern.focus();
		}else if(psdspan.innerHTML!="密码格式正确！" && pas!=""){
			pas.focus();
		}else if(repsdspan.innerHTML!="密码一致！"){
			repas.focus();
		}else if(realnamespan.innerHTML!="姓名格式正确！" && realnamespan.innerHTML!=""){
			rname.focus();
		}else if(emailspan.innerHTML!="邮箱格式正确！"){
			mail.focus();
		}else{
	<%}%>
			
			var gender = document.createElement("input");
			var isAdmin = document.createElement("input");
			gender.name="gender";
			gender.type="hidden";
			isAdmin.name="isAdmin";
			isAdmin.type="hidden";
			if(man.checked)
				gender.value="man";
			else if(woman.checked)
				gender.value="woman";
			else
				gender.value="";
			
			if(isad.checked)
				isAdmin.value="is";
			else
				isAdmin.value="not";
			
			editForm.appendChild(gender);
			editForm.appendChild(isAdmin);
			
			editForm.submit();
		}
}
	function back(){
		window.history.back();
	}
</script>
</html>