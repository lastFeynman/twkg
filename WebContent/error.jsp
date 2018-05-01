<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>发生错误</title>
</head>
<body>
	<h1>发生错误</h1>
	${errMsg}<br>
	<span id="jumpTime"></span>
</body>
<script>
var jumpTime=document.getElementById("jumpTime");
function jump(sec,url){
	if(--sec>0){
		jumpTime.innerHTML="将在"+sec+"秒后跳转";
		setTimeout("jump("+sec+",'"+url+"')",1000);
	}
	else location.href=url;
}
var url = "index.jsp";
if("${errMsg}"=="信息修改失败")
	var url="personal.jsp";

jump(4,url);
</script>
</html>