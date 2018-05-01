var container = document.getElementById("container");
var logo = document.getElementById("logo");
var siteName = document.getElementById("siteName");
var naviMenu = document.getElementById("naviMenu");
var login = document.getElementById("login");
var register = document.getElementById("register");
var searchContent = document.getElementById("searchContent")
var searchIconP = document.getElementById("searchIconP");
var searchIcon = document.getElementById("searchIcon");
var searchIconM = document.getElementById("searchIconM");
var resultContainer = document.getElementById("resultContainer");
var song = document.getElementById("song");
var cover = document.getElementById("cover");
if(window.innerWidth - 1131 > 0){
	container.style.width = window.innerWidth-17+'px';
	logo.style.left = (window.innerWidth - 1131)/2+'px';
	siteName.style.left = (window.innerWidth - 1131)/2+20+'px';
	naviMenu.style.left = (window.innerWidth - 1131)/2+150+'px';
	login.style.left = (window.innerWidth - 1131)/2+400+'px';
	register.style.left = (window.innerWidth - 1131)/2+410+'px';
	searchContent.style.left = (window.innerWidth - 1131)/2+150+'px';
	searchIconP.style.left = (window.innerWidth - 1131)/2+100+'px';
	searchIcon.style.left = (window.innerWidth - 1131)/2+100+'px';
	searchIconM.style.left = (window.innerWidth - 1131)/2+100+'px';
	resultContainer.style.left = (window.innerWidth - 1131)/2+'px';
}else{
	container.style.width = '1131px';
	logo.style.left = '0px';
	siteName.style.left = '20px';
	naviMenu.style.left = '150px';
	login.style.left = '400px';
	register.style.left = '410px';
	searchContent.style.left = '150px';
	searchIconP.style.left = '100px';
	searchIcon.style.left = '100px';
	searchIconM.style.left = '100px';
	resultContainer.style.left = '0px';
}

window.onresize = function() {
	if(window.innerWidth - 1131 > 0) {
		container.style.width = window.innerWidth-17+'px';
		logo.style.left = (window.innerWidth - 1131) / 2 + 'px';
		siteName.style.left = (window.innerWidth - 1131) / 2 + 20 + 'px';
		naviMenu.style.left = (window.innerWidth - 1131) / 2 + 150 + 'px';
		login.style.left = (window.innerWidth - 1131) / 2 + 400 + 'px';
		register.style.left = (window.innerWidth - 1131) / 2 + 410 + 'px';
		searchContent.style.left = (window.innerWidth - 1131) / 2 + 150 + 'px';
		searchIconP.style.left = (window.innerWidth - 1131) / 2 + 100 + 'px';
		searchIcon.style.left = (window.innerWidth - 1131) / 2 + 100 + 'px';
		searchIconM.style.left = (window.innerWidth - 1131) / 2 + 100 + 'px';
		resultContainer.style.left = (window.innerWidth - 1131) / 2 + 'px';
	} else {
		container.style.width = '1131px';
		logo.style.left = '0px';
		siteName.style.left = '20px';
		naviMenu.style.left = '150px';
		login.style.left = '400px';
		register.style.left = '410px';
		searchContent.style.left = '150px';
		searchIconP.style.left = '100px';
		searchIcon.style.left = '100px';
		searchIconM.style.left = '100px';
		resultContainer.style.left = '0px';
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
	var searchType = document.createElement('input');
	searchType.type = 'text';
	searchType.style.visibility='hidden';
	searchType.name = 'searchType';
	if(song.checked){
		searchType.value='song';
	}else{
		searchType.value='cover';
	}
	var searchForm = document.getElementById("searchForm")
	searchForm.appendChild(searchType);
	searchForm.submit();
}