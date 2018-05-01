<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>贪玩K歌</title>
</head>
<body>
	${statusMsg}<br>
	${uploadMsg}<br>
	<span id="jumpTime"></span>
</body>
<script type="text/javascript">
var jumpTime=document.getElementById("jumpTime");
function jump(sec,url){
	if(--sec>0){
		jumpTime.innerHTML="将在"+sec+"秒后跳转";
		setTimeout("jump("+sec+",'"+url+"')",1000);
	}
	else location.href=url;
}
var url = "adminIndex.jsp";
if("${statusMsg}"=="用户修改成功" || "${statusMsg}"=="用户修改失败" || "${statusMsg}"=="用户删除成功" || "${statusMsg}"=="用户删除失败")
	var url="adminSearch.jsp?searchType=user";

if("${statusMsg}"=="歌曲修改失败" || "${statusMsg}"=="歌曲修改成功" || "${statusMsg}"=="歌曲删除失败" || "${statusMsg}"=="歌曲删除成功")
	var url="adminSearch.jsp?searchType=song";
	
if("${statusMsg}"=="翻唱修改失败" || "${statusMsg}"=="翻唱修改成功" || "${statusMsg}"=="翻唱删除失败" || "${statusMsg}"=="翻唱删除成功")
	var url="adminSearch.jsp?searchType=cover";

jump(4,url);
</script>
</html>