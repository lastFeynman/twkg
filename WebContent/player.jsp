<%@page import="twkg.entity.CoverSong"%>
<%@page import="twkg.entity.Song"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>贪玩K歌播放器</title>
<link rel="stylesheet" href="./css/player/smusic.min.css">
<script src="./js/player/smusic.min.js"></script>
</head>

<body>
	<center><div id="musicPlayer"></div></center>
	<%
		String playList = (String)session.getAttribute("playListStr");
		String playerType = request.getParameter("playerType");
		String currentSongStr = request.getParameter("currentSong");
		int currentSongId = 0;
		if(currentSongStr!=null)
			currentSongId = Integer.parseInt(currentSongStr);
		List<?> songList = null;
		
		if(playerType==null || playerType.equals("song")){
			songList = (List<?>)session.getAttribute("songList");
		}else{
			songList = (List<?>)session.getAttribute("coverSongList");
		}
		int currentIndex = 0;
		for(int i=0;currentSongId!=0&&i<songList.size();i++){
			if(playerType==null || playerType.equals("song")){
				if(((Song)(songList.get(i))).getSongId()==currentSongId){
					currentIndex = i;
					break;
				}
			}else{
				if(((CoverSong)(songList.get(i))).getCoverSongId()==currentSongId){
					currentIndex = i;
					break;
				}
			}
		}
	%>
	<script>
		var playList = <%=playList%>;
		var smusic = SMusic(playList, {
			container : document.getElementById('musicPlayer')
		});
		smusic.init()
		
		for(var i=0;i< <%=currentIndex%>;i++){
			smusic.next();
		}
	</script>
</body>
</html>