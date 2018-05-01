<%@page import="twkg.dao.ISongDao"%>
<%@page import="twkg.entity.Song"%>
<%@page import="twkg.dao.factory.DaoFactory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	String editSongIdStr = request.getParameter("editSongId");
	int editSongId = 0;
	if (editSongIdStr != null) {
		try {
			editSongId = Integer.parseInt(editSongIdStr);
		} catch (Exception e) {
			request.setAttribute("errMsg", "请求参数错误：editSongId");
			request.getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
			return;
		}
	}
	DaoFactory daoFactory = DaoFactory.getDaoFactory();
	ISongDao songDao = daoFactory.getSongDao();
	Song song = songDao.findSongBySongId(editSongId);
	request.setAttribute("song", song);
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>
	<%
	if (editSongIdStr != null) {
	%> 编辑 <%
		} else {
	%> 添加 <%
		}
	%>
</title>
  <link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
  <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
  <script src="http://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
  <link rel="stylesheet" href="http://jqueryui.com/resources/demos/style.css">
  <script>
  $(function() {
    $( "#datepicker" ).datepicker({
      changeMonth: true,
      changeYear: true
    });
  });
  </script>
</head>
<body style="background-color: #ffc80070;">
<h2>欢迎来到歌曲管理界面</h2>
		<form action="EditSongServlet" id="fm" method="post" enctype="multipart/form-data">
        <table width="100%">
        <tr>
            <td align="right">歌名:</td>
            <td><input type="text" id="Song" name="songName"/> </td>
        </tr>
        <tr>
            <td align="right">歌手:</td>
            <td><input type="text" id="Singer" name="Singer"/></td>
        </tr>
        <tr>
            <td align="right">日期:</td>
            <td><input type="button" id="datepicker" value="请选择创作时间" name="Createtime"></td>
        </tr>
        <tr>
            <td align="right">歌曲文件:</td>
            <td><input type="file" id="songFile" name="songFile"></td>
        </tr>
        <tr>
            <td align="right">专辑封面:</td>
            <td><input type="file" id="thumbnailFile" name="thumbnailFile"></td>
        </tr>
        <tr>
            <td align="right">歌词文件:</td>
            <td><input type="file" id="lyricFile" name="lyricFile"></td>
        </tr>
         <tr>
         	<td align="right"></td>
         	<td>
         	<%if(editSongId==0) {%>
         	<input type="button" value="&nbsp;&nbsp;&nbsp;添&nbsp;&nbsp;加&nbsp;&nbsp;&nbsp;" id="modify">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
         	<%}else{ %>
         	<input type="button" value="&nbsp;&nbsp;&nbsp;修&nbsp;&nbsp;改&nbsp;&nbsp;&nbsp;" id="modify">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
         	<%} %>
         	<input type="button" value="&nbsp;&nbsp;&nbsp;重&nbsp;&nbsp;置&nbsp;&nbsp;&nbsp;" id="reflash"></td>     	
         </tr>
        </table>
    </form>
    <button style="cursor:pointer;" onclick="back()">返回</button>
	</body>
	<script type="text/javascript">
		var mf=document.getElementById("modify");
		var rf=document.getElementById("reflash");
		var so=document.getElementById("Song");
		var si=document.getElementById("Singer");
		var ct=document.getElementById("datepicker");
		
		var songFile = document.getElementById("songFile");
		var thumbnailFile = document.getElementById("thumbnailFile");
		var lyricFile = document.getElementById("lyricFile");
		
		<%if(editSongIdStr==null){%>
		rf.onclick=function(){
			so.value="";
			si.value="";
			ct.value="";
			songFile.value = "";
			thumbnailFile.value = "";
			lyricFile.value = "";
		}
		<%}else{%>
		so.value="${song.songName}";
		si.value="${song.singerName}";
		ct.value="${song.createTime}";
		
		rf.onclick=function(){
			so.value="${song.songName}";
			si.value="${song.singerName}";
			ct.value="${song.createTime}";
			songFile.value = "";
			thumbnailFile.value = "";
			lyricFile.value = "";
		}
		<%}%>
		

		

		mf.onclick=function(){
			if(so.value==""){
				alert("歌曲名不能为空");
			<%if(editSongIdStr==null) {%>
			}else if(songFile.value==""){
				alert("未选择歌曲文件");
			<%}%>
			}else if(songFile.value!="" && !songFile.value.endsWith(".mp3")){
				alert("歌曲文件扩展名不是mp3");
			}else if(thumbnailFile.value!="" && !thumbnailFile.value.endsWith(".jpg")){
				alert("专辑封面文件扩展名不是jpg");
			}else if(lyricFile.value!="" && !lyricFile.value.endsWith("lrc")){
				alert("歌词文件扩展名不是lrc");
			}else{			
				var date=document.createElement("input");
			    date.name="Createtime";
			    date.value=ct.value;
			    date.style.visibility="hidden";
			    fm.appendChild(date);
			    <%if(editSongIdStr!=null){%>
			    	var editSongId = document.createElement("input");
			    	editSongId.name = "editSongId";
			    	editSongId.value = "${song.songId}";
			    	editSongId.type = "hidden";
			    	fm.appendChild(editSongId);
			    <%}%>
			    fm.submit();}
		}
		
		function back(){
			window.history.back();
		}
		
	</script>
</body>
</html>