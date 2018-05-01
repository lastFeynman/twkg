<%@page import="twkg.util.ConfigUtil"%>
<%@page import="twkg.entity.User"%>
<%@page import="twkg.entity.CoverSong"%>
<%@page import="twkg.entity.Song"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/searchResult.css" type="text/css" rel="stylesheet" />
<title>搜索结果</title>
</head>
<%
	int maxPage = (int)request.getAttribute("maxPage");
	int currentPage = (int)request.getAttribute("currentPage");
	User currentUser = (User)session.getAttribute("currentUser");
	String searchType = request.getParameter("searchType");
	if(searchType==null)
		searchType = "song";
	String searchContent = (String)request.getAttribute("searchContent");
	List<CoverSong> coverSongs = null;
	List<Song> songs = null;
	List<User> users = null;
	if(searchType.equals("song")){
		songs = (List<Song>)request.getAttribute("songs");
	}else{
		coverSongs = (List<CoverSong>)request.getAttribute("coverSongs");
		songs = (List<Song>)request.getAttribute("songs");
		users = (List<User>)request.getAttribute("users");
	}
%>
<script type="text/javascript">
	function playSong(songId){
		songPlayer = window.open("/twkg/player.jsp?currentSong="+songId,"songPlayer");
		songPlayer.focus;
	}
	function playCoverSong(coverSongId){
		coverSongPlayer = window.open("/twkg/player.jsp?playerType=cover&currentSong="+coverSongId,"coverSongPlayer");
		coverSongPlayer.focus;
	}
</script>
<body>
	<div class="container" id="container">
			
			<!-- 头部 -->
			<div class="header">
				<a href="index.jsp"><img id="logo" style="float: left;"/></a>
				<div id="siteName">贪玩K歌</div>
				<ul id="naviMenu">
					<a href="index.jsp"><li>首页</li></a>
					<a href="personal.jsp"><li>个人中心</li></a>
				</ul>
				<%if(currentUser!=null){ %>
					<a id="login" style="position: relative;float: left;">欢迎您，</a>
					<a href="personal.jsp" id="register" style="position: relative;float: left;"><%=currentUser.getUserName() %></a>
				<%}else{ %>
					<a href="login.jsp" id="login" style="position: relative;float: left;">登录</a>
					<a href="register.jsp" id="register" style="position: relative;float: left;">注册</a>
				<%} %>
				<!-- 搜索框 -->
				<form action="searchResult.jsp" method="post" id="searchForm">
					<input type="text" id="searchContent" name="searchContent"/>
					<img src="images/searchIcon.png" id="searchIcon" />
					<img src="images/searchIconP.png" id="searchIconP"/>
					<img src="images/searchIconM.png" id="searchIconM"/>
				</form>
			</div>
			
			<div style="text-align: center; position: relative;top: 50px;">
				共有<%=maxPage %>页结果，当前第<%=currentPage %>页&emsp;&emsp;
				<input type="radio" id="song" name="searchType"/>搜索原唱
				<input type="radio" id="cover" name="searchType"/>搜索翻唱
			</div>
			<!-- 结果 -->
			<div class="resultContainer" id="resultContainer">
				<div class="result">
					<ul>
						<li style="width: 400px;">歌名</li>
						<li style="width: 200px;">歌手</li>
						<li style="width: 200px;">翻唱者</li>
						<li style="width: 100px;">播放</li>
						<li style="width: 100px;">翻唱</li>
					</ul>
				</div>
				<%if(songs.size()==0){ %>
					<h3>抱歉，没有搜索到结果!</h3>
				<%}else{ %>
					<%for(int i=0;i<songs.size();i++){ %>
						<div class="result">
							<ul>
								<li style="width: 400px;"><%=songs.get(i).getSongName() %></li>
								<%if(songs.get(i).getSingerName()==null || songs.get(i).getSingerName().equals("")) {%>
								<li style="width: 200px;">&emsp;</li>
								<%}else{ %>
								<li style="width: 200px;"><%=songs.get(i).getSingerName() %></li>
								<%} %>
								<%if(searchType.equals("cover")){ %>
								<li style="width: 200px;"><%=users.get(i).getUserName() %></li>
								<%}else { %>
								<li style="width: 200px;">&emsp;</li>
								<%} %>
								
								<%if(searchType.equals("song")) {%>
								<li style="width: 100px;">
									<a onclick="playSong('<%=songs.get(i).getSongId()%>')"><button style="cursor: pointer;">播放</button></a>
								</li>
								<li style="width: 100px;">
									<a href="record.jsp?recordSong=<%=songs.get(i).getSongId()%>" target="_blank"><button style="cursor: pointer;">翻唱</button></a>
								</li>
								<%}else{ %>
								<li style="width: 100px;">
									<a onclick="playCoverSong('<%=coverSongs.get(i).getCoverSongId()%>')"><button style="cursor: pointer;">播放</button></a>
								</li>
								<li style="width: 100px;">&emsp;</li>
								<%} %>
							</ul>
						</div>
					<%} %>
				<%} %>
			</div>
			<div id="controls">
				<a id="lastPage" style="cursor:pointer;">上一页</a>&emsp;
				<a id="nextPage" style="cursor:pointer;">下一页</a>&emsp;
				<form action="searchResult.jsp" method="post" id="jumpForm">
					<input type="text" name="searchType" value="<%=searchType%>" style="position:absolute;visibility:hidden;"/>
					<input type="text" name="searchContent" value="<%=searchContent%>" style="position:absolute;visibility:hidden;"/>
					<input type="text" name="currentPage" style="width: 75px;" id="jumpPage" onKeyUp="value=value.replace(/[^\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"/>
					<input type="text" name="maxPage" value="<%=maxPage%>" style="position:absolute;visibility:hidden;"/>
					<input type="button" value="转到" id="jumpTo" style="cursor:pointer;"/>
				</form>
			</div>
			
			<!-- footer -->
			<div class="footer">
				<div style="position: relative;top:50px;width: 100%;text-align: center;">
					<a href="">贪玩K歌</a>&emsp;|&emsp;<a href="">服务条款</a>&emsp;|&emsp;<a href="">关于我们</a><br>
					Copyright (C) 2018-9999 All rights reserved.
				</div>
			</div>
		</div>
</body>
<script src="js/searchResult.js"></script>
<script type="text/javascript">
	var song = document.getElementById("song");
	var cover = document.getElementById("cover");
	<%if(searchType.equals("song")){%>
		song.checked = true;
	<%}else{%>
		cover.checked = true;
	<%}%>
	
	var lastPage = document.getElementById("lastPage");
	var nextPage = document.getElementById("nextPage");
	var jumpTo = document.getElementById("jumpTo");
	var jumpPage = document.getElementById("jumpPage");
	var jumpForm = document.getElementById("jumpForm");
	
	lastPage.onclick = function(){
		if(<%=currentPage-1%> <= 0 || <%=currentPage-1%> > <%=maxPage%>){
			alert("页码错误");
		}else{
			lastPage.href = "searchResult.jsp?searchType=<%=searchType%>&searchContent=<%=searchContent%>&currentPage=<%=currentPage-1%>&maxPage=<%=maxPage%>";
			lastPage.click();
		}
	}
	
	nextPage.onclick = function(){
		if(<%=currentPage+1%> < 0 || <%=currentPage+1%> > <%=maxPage%>){
			alert("页码错误");
		}else{
			nextPage.href = "searchResult.jsp?searchType=<%=searchType%>&searchContent=<%=searchContent%>&currentPage=<%=currentPage+1%>&maxPage=<%=maxPage%>";
			nextPage.click();
		}
	}
	
	jumpTo.onclick = function(){
		if(jumpPage.value == '' || jumpPage.value > <%=maxPage%> || jumpPage.value <= 0){
			alert("页码错误");
		}else{
			jumpForm.submit();
		}
	}
</script>
</html>