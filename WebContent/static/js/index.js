$(document).ready(function(){
	// 初始化
	if ($.cookie("pid")==null) {
		$(window).attr('location','login.html');
	}else{
		$(window).attr('location','hall.html');
	}
});