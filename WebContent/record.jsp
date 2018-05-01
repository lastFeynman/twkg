<%@page import="twkg.entity.User"%>
<%@page import="twkg.util.ConfigUtil"%>
<%@page import="twkg.entity.Song"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>贪玩K歌录音</title>
<script type="text/javascript" src="js/recorder/HZRecorder.js"></script>
</head>
<%
	Song song = (Song)session.getAttribute("recordSong");
	User currentUser = (User)session.getAttribute("currentUser");
%>
<body>
	<audio id="srcAudio"
		src="<%=ConfigUtil.SONG_PATH%><%=song.getSongId()%><%=".mp3"%>"></audio>
	<audio id="recordAudio" controls controlsList="nodownload"></audio>
	<h5 id="result"></h5>
	<br>
	<input type="button" value="录音" onclick="startRecord()" />
	<input type="button" value="停止" onclick="stopRecord()" />
	<input type="button" value="播放" onclick="playRecord()" />
	<input type="button" value="上传" onclick="uploadRecord()" />
</body>
<script>
	var result = document.getElementById("result");
	var recorder;
	var recordAudio = document.getElementById("recordAudio");
	var srcAudio = document.getElementById("srcAudio");
	function startRecord(){
		HZRecorder.get(function(rec){
			recorder = rec;
			srcAudio.play();
			recorder.start();
		});
	}
	function stopRecord(){
		recorder.stop();
		srcAudio.load();
	}
	function playRecord(){
		recorder.play(recordAudio);
		recordAudio.play();
	}
	function uploadRecord(){
		upload("/twkg/UploadServlet?userId=<%=currentUser.getUserId()%>&songId=<%=song.getSongId()%>",function(state,e){
			switch (state) {
            case 'uploading':
                break;
            case 'ok':
                result.innerHTML = '上传成功';
                break;
            case 'error':
            	result.innerHTML = '上传失败';
                break;
            case 'cancel':
            	result.innerHTML = '上传被取消';
                break;
        }
		});
	}
	
	function upload(url, callback) {
        var fd = new FormData();
        fd.append("audioData", recorder.getBlob());
        var xhr = new XMLHttpRequest();
        if (callback) {
            xhr.upload.addEventListener("progress", function (e) {
                callback('uploading', e);
            }, false);
            xhr.addEventListener("load", function (e) {
                callback('ok', e);
            }, false);
            xhr.addEventListener("error", function (e) {
                callback('error', e);
            }, false);
            xhr.addEventListener("abort", function (e) {
                callback('cancel', e);
            }, false);
        }
        xhr.open("POST", url);
        xhr.send(fd);
    }
</script>
</html>