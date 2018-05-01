var container = document.getElementById("container");
var scrollPic = document.getElementById("scrollPic");
var logo = document.getElementById("logo");
var siteName = document.getElementById("siteName");
var naviMenu = document.getElementById("naviMenu");
var login = document.getElementById("login");
var register = document.getElementById("register");
var changeUser = document.getElementById("changeUser");
var searchContent = document.getElementById("searchContent")
var searchIconP = document.getElementById("searchIconP");
var searchIcon = document.getElementById("searchIcon");
var searchIconM = document.getElementById("searchIconM");
var hotSongContainer = document.getElementById("hotSongContainer");
var hotUserContainer = document.getElementById("hotUserContainer");
if(window.innerWidth - 1131 > 0){
	container.style.width = window.innerWidth-17+'px';
	scrollPic.style.left = (window.innerWidth - 1131)/2+'px';
	logo.style.left = (window.innerWidth - 1131)/2+'px';
	siteName.style.left = (window.innerWidth - 1131)/2+20+'px';
	naviMenu.style.left = (window.innerWidth - 1131)/2+150+'px';
	login.style.left = (window.innerWidth - 1131)/2+400+'px';
	register.style.left = (window.innerWidth - 1131)/2+410+'px';
	if(changeUser!=null)
		changeUser.style.left = (window.innerWidth - 1131)/2+450+'px';
	searchContent.style.left = (window.innerWidth - 1131)/2+150+'px';
	searchIconP.style.left = (window.innerWidth - 1131)/2+100+'px';
	searchIcon.style.left = (window.innerWidth - 1131)/2+100+'px';
	searchIconM.style.left = (window.innerWidth - 1131)/2+100+'px';
	hotSongContainer.style.left = (window.innerWidth - 1131)/2+'px';
	hotUserContainer.style.left = (window.innerWidth - 1131)/2+'px';
}else{
	container.style.width = '1131px';
	scrollPic.style.left = '0px';
	logo.style.left = '0px';
	siteName.style.left = '20px';
	naviMenu.style.left = '150px';
	login.style.left = '400px';
	register.style.left = '410px';
	if(changeUser!=null)
		changeUser.style.left = '450px';
	searchContent.style.left = '150px';
	searchIconP.style.left = '100px';
	searchIcon.style.left = '100px';
	searchIconM.style.left = '100px';
	hotSongContainer.style.left = '0px';
	hotUserContainer.style.left = '0px';
}

window.onresize = function() {
	if(window.innerWidth - 1131 > 0) {
		container.style.width = window.innerWidth-17+'px';
		scrollPic.style.left = (window.innerWidth - 1131) / 2 + 'px';
		logo.style.left = (window.innerWidth - 1131) / 2 + 'px';
		siteName.style.left = (window.innerWidth - 1131) / 2 + 20 + 'px';
		naviMenu.style.left = (window.innerWidth - 1131) / 2 + 150 + 'px';
		login.style.left = (window.innerWidth - 1131) / 2 + 400 + 'px';
		register.style.left = (window.innerWidth - 1131) / 2 + 410 + 'px';
		if(changeUser!=null)
			changeUser.style.left = (window.innerWidth - 1131)/2+450+'px';
		searchContent.style.left = (window.innerWidth - 1131) / 2 + 150 + 'px';
		searchIconP.style.left = (window.innerWidth - 1131) / 2 + 100 + 'px';
		searchIcon.style.left = (window.innerWidth - 1131) / 2 + 100 + 'px';
		searchIconM.style.left = (window.innerWidth - 1131) / 2 + 100 + 'px';
		hotSongContainer.style.left = (window.innerWidth - 1131) / 2 + 'px';
		hotUserContainer.style.left = (window.innerWidth - 1131) / 2 + 'px';
	} else {
		container.style.width = '1131px';
		scrollPic.style.left = '0px';
		logo.style.left = '0px';
		siteName.style.left = '20px';
		naviMenu.style.left = '150px';
		login.style.left = '400px';
		register.style.left = '410px';
		if(changeUser!=null)
			changeUser.style.left = '450px';
		searchContent.style.left = '150px';
		searchIconP.style.left = '100px';
		searchIcon.style.left = '100px';
		searchIconM.style.left = '100px';
		hotSongContainer.style.left = '0px';
		hotUserContainer.style.left = '0px';
	}

}
//搜索框
searchIconP.onclick = function(){
	searchContent.style.visibility = 'visible';
	searchIcon.style.visibility = 'visible';
	searchIconM.style.visibility = 'visible';
}
searchIconM.onclick = function(){
	searchContent.style.visibility = 'hidden';
	searchIcon.style.visibility = 'hidden';
	searchIconM.style.visibility = 'hidden';
}
searchIcon.onclick = function(){
	document.getElementById("searchForm").submit();
}
//滚动图片
var leftCursor = document.getElementById("leftCursor");
var rightCursor = document.getElementById("rightCursor");
var lastPic = document.getElementById("lastPic");
var nextPic = document.getElementById("nextPic");
var naviPoints = document.getElementsByClassName("naviPoint");
var pics = document.getElementsByClassName("scrollImg")

lastPic.onmouseover = function(){
	leftCursor.src = "./images/leftCursorHover.png";
}

lastPic.onmouseout = function(){
	leftCursor.src = "./images/leftCursor.png";
}

nextPic.onmouseover = function(){
	rightCursor.src = "./images/rightCursorHover.png";
}

nextPic.onmouseout = function(){
	rightCursor.src = "./images/rightCursor.png";
}

lastPic.onclick = function(){
	var i;
	for(i=0;i<3;i++){
		if(pics[i].style.visibility=='visible')
			break;
	}
	i=i-1;
	if(i<0)
		i=2;
	
	for(var j=0;j<pics.length;j++){
		pics[j].style.visibility = 'hidden';
		naviPoints[j].style.backgroundColor = 'rgba(0,0,0,0)';
	}
	pics[i].style.visibility = 'visible';
	naviPoints[i].style.backgroundColor = 'white';
}

nextPic.onclick = function(){
	var i;
	for(i=0;i<3;i++){
		if(pics[i].style.visibility=='visible')
			break;
	}
	i=i+1;
	if(i>2)
		i=0;
	
	for(var j=0;j<pics.length;j++){
		pics[j].style.visibility = 'hidden';
		naviPoints[j].style.backgroundColor = 'rgba(0,0,0,0)';
	}
	pics[i].style.visibility = 'visible';
	naviPoints[i].style.backgroundColor = 'white';
}

naviPoints[0].onclick = function(){
	for(var i=0;i<pics.length;i++){
		pics[i].style.visibility = 'hidden';
		naviPoints[i].style.backgroundColor = 'rgba(0,0,0,0)';
	}
	pics[0].style.visibility = 'visible';
	naviPoints[0].style.backgroundColor = 'white';
}

naviPoints[1].onclick = function(){
	for(var i=0;i<pics.length;i++){
		pics[i].style.visibility = 'hidden';
		naviPoints[i].style.backgroundColor = 'rgba(0,0,0,0)';
	}
	pics[1].style.visibility = 'visible';
	naviPoints[1].style.backgroundColor = 'white';
}

naviPoints[2].onclick = function(){
	for(var i=0;i<pics.length;i++){
		pics[i].style.visibility = 'hidden';
		naviPoints[i].style.backgroundColor = 'rgba(0,0,0,0)';
	}
	pics[2].style.visibility = 'visible';
	naviPoints[2].style.backgroundColor = 'white';
}