<%@page import="twkg.entity.Song"%>
<%@page import="twkg.entity.CoverSong"%>
<%@page import="twkg.entity.User"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理员搜索结果</title>
</head>
<style>
a {
	text-decoration: underline;
	cursor: pointer;
}

a:visited {
	color: black;
}

table {
	table-layout: fixed;
	width: 0px;
	white-space: nowrap;
}

table td {
	overflow: hidden;
	text-overflow: ellipsis;
	text-align: center;
}
</style>
<%
	String searchType = (String) request.getAttribute("searchType");
	List<User> users = (List<User>)request.getAttribute("users");
	List<Song> songs = (List<Song>)request.getAttribute("songs");
	List<CoverSong> coverSongs = (List<CoverSong>)request.getAttribute("coverSongs");
%>
<body>
	<center>
		<fieldset style="width: 270px;">
			<legend>搜索</legend>
			<form action="adminSearch.jsp">
				<input type="text" name="searchType" value="user"
					style="visibility: hidden; width: 5px;" /> <input type="text"
					name="searchContent" /> <input type="submit" value="搜索用户" />
			</form>
			<form action="adminSearch.jsp">
				<input type="text" name="searchType" value="song"
					style="visibility: hidden; width: 5px;" /> <input type="text"
					name="searchContent" /> <input type="submit" value="搜索原唱" />
			</form>
			<form action="adminSearch.jsp">
				<input type="text" name="searchType" value="cover"
					style="visibility: hidden; width: 5px;" /> <input type="text"
					name="searchContent" /> <input type="submit" value="搜索翻唱" />
			</form>
		</fieldset>
	</center>
	<br />
	<br />
	<br />
	<center>
		<span>共有${maxPage}页，当前第${currentPage}页</span>
		<table cellspacing="0" border="1px black solid">
			<%
				if (searchType.equals("user")) {
			%>
			<tr>
				<th width="100">id</th>
				<th width="100">用户名</th>
				<th width="100">是否管理员</th>
				<th width="150">邮箱</th>
				<th width="100">生日</th>
				<th width="50">性别</th>
				<th width="100">姓名</th>
				<th width="200">自述</th>
				<th width="50">人气</th>
				<th width="150">注册时间</th>
				<th width="50">修改</th>
				<th width="50">删除</th>
			</tr>
			<c:forEach var="user" items="${users}">
				<tr>
					<td>${user.userId}</td>
					<td title="${user.userName}">${user.userName}</td>
					<td>${user.isAdmin()}</td>
					<td title="${user.userEmail}">${user.userEmail}</td>
					<td>${user.userBirth}</td>
					<td>${user.userGender}</td>
					<td title="${user.userRealName}">${user.userRealName}</td>
					<td title="${user.userBio}">${user.userBio}</td>
					<td>${user.userPopularity}</td>
					<td>${user.registerTime}</td>
					<td><a href="editUser.jsp?editUserId=${user.userId}" name="edit"><input type="button" value="修改" /></a></td>
					<td><a id="${user.userId}" name="delete"><input type="button" value="删除" /></a></td>
				</tr>
			</c:forEach>
			<tr>
				<td colspan="12">
			<%
				} else if (searchType.equals("song")) {
			%>
			<tr>
				<th width="100">id</th>
				<th width="200">歌名</th>
				<th width="100">歌手</th>
				<th width="100">创作时间</th>
				<th width="100">播放次数</th>
				<th width="100">翻唱次数</th>
				<th width="50">修改</th>
				<th width="50">删除</th>
			</tr>
			<c:forEach var="song" items="${songs}">
				<tr>
					<td>${song.songId}</td>
					<td title="${song.songName}">${song.songName}</td>
					<td title="${song.singerName}">${song.singerName}</td>
					<td>${song.createTime}</td>
					<td>${song.playCount}</td>
					<td>${song.coverCount}</td>
					<td><a href="editSong.jsp?editSongId=${song.songId}" name="edit"><input type="button" value="修改" /></a></td>
					<td><a id="${song.songId}" name="delete"><input type="button" value="删除" /></a></td>
				</tr>
			</c:forEach>
			<tr>
			
				<td colspan="8">
			<%
				} else {
			%>
				<tr>
					<th width="100">id</th>
					<th width="100">翻唱者id</th>
					<th width="100">原唱id</th>
					<th width="150">翻唱时间</th>
					<th width="100">播放次数</th>
					<th width="50">修改</th>
					<th width="50">删除</th>
				</tr>
				<c:forEach var="coverSong" items="${coverSongs}">
				<tr>
					<td>${coverSong.coverSongId}</td>
					<td>${coverSong.userId}</td>
					<td>${coverSong.songId}</td>
					<td>${coverSong.singTime}</td>
					<td>${coverSong.coverPlayCount}</td>
					<td><a href="editCoverSong.jsp?editCoverSongId=${coverSong.coverSongId}" name="edit"><input type="button" value="修改" /></a></td>
					<td><a id="${coverSong.coverSongId}" name="delete"><input type="button" value="删除" /></a></td>
				</tr>
				</c:forEach>
				<tr>
					<td colspan="7">
			<%
				}
			%>
			<a id="lastPage">上一页</a> <a id="nextPage">下一页</a>
					<form id="jumpForm" action="adminSearch.jsp" method="post">
						<input type="text" name="searchType" value="${searchType}"
							style="position: absolute; visibility: hidden;" /> <input
							type="text" name="searchContent" value="${searchContent}"
							style="position: absolute; visibility: hidden;" /> <input
							type="text" name="maxPage" value="${maxPage}"
							style="position: absolute; visibility: hidden;" /> <input
							type="text" name="currentPage" id="jumpPage"
							onKeyUp="value=value.replace(/[^\d]/g,'')"
							onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" />
						<input type="button" value="转到" id="jumpTo" />
					</form>
				</td>
			</tr>
		</table>
	</center>
</body>
<script>
	var lastPage = document.getElementById("lastPage");
	var nextPage = document.getElementById("nextPage");
	var jumpPage = document.getElementById("jumpPage");
	var jumpTo = document.getElementById("jumpTo");
	var jumpForm = document.getElementById("jumpForm");
	
	lastPage.onclick = function(){
		if(${currentPage-1} <= 0 || ${currentPage-1} > ${maxPage}){
			alert("页码错误");
		}else{
			lastPage.href = "adminSearch.jsp?searchType=${searchType}&searchContent=${searchContent}&currentPage=${currentPage-1}&maxPage=${maxPage}";
			lastPage.click();
		}
	}
	
	nextPage.onclick = function(){
		if(${currentPage+1} < 0 || ${currentPage+1} > ${maxPage}){
			alert("页码错误");
		}else{
			nextPage.href = "adminSearch.jsp?searchType=${searchType}&searchContent=${searchContent}&currentPage=${currentPage+1}&maxPage=${maxPage}";
			nextPage.click();
		}
	}
	
	jumpTo.onclick = function(){
		if(jumpPage.value == '' || jumpPage.value > ${maxPage} || jumpPage.value <= 0){
			alert("页码错误");
		}else{
			jumpForm.submit();
		}
	}
	
	
	var deletes = document.getElementsByName("delete");
	
	<%
	if(searchType.equals("user")){
		for(int i=0;i<users.size();i++){
	%>
	
		deletes[<%=i%>].onclick = function(){
			if(confirm("确定要删除？")==true){
				window.location.href = "DeleteServlet?deleteUserId="+deletes[<%=i%>].id;
			}
		}
	<%
		}
	}
	%>
	
	<%
	if(searchType.equals("song")){
		for(int i=0;i<songs.size();i++){
	%>
	
		deletes[<%=i%>].onclick = function(){
			if(confirm("确定要删除？")==true){
				window.location.href = "DeleteServlet?deleteSongId="+deletes[<%=i%>].id;
			}
		}
	<%
		}
	}
	%>
	
	<%
	if(searchType.equals("cover")){
		for(int i=0;i<coverSongs.size();i++){
	%>
	
		deletes[<%=i%>].onclick = function(){
			if(confirm("确定要删除？")==true){
				window.location.href = "DeleteServlet?deleteCoverSongId="+deletes[<%=i%>].id;
			}
		}
	<%
		}
	}
	%>
</script>
</html>