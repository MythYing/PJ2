$(document).ready(function(){

	// 设置异步为假
	$.ajaxSetup (
		{
			async: false
		}
	);
	$("#content>div").eq(0).show().siblings().hide();
	gameHallPage();
	$("nav>ul>li").click(function(){
		var index = $("nav>ul>li").index($(this));
		$("#content>div").eq(index).show().siblings().hide();
	})

	$("#pid").text("欢迎您");

	//  寻找对局
	$("#match-game").click(function(){
		$.post("MatchGame",
			{
			},
			function(data,status){
			}
		);//post 
		matchingPage();		
	}) // click
	$("#cancel-match").click(function(){
		$.post("CancelMatch",
			{
			},
			function(data,status){
			}
		);//post 
		gameHallPage();
		
	}) // click
	// 退出登录
	$("#logout-button").click(function(){
		$.post("Logout", {});
		$(window).attr('location','index.html');
	})

});

function refreshStatusOnce(){
	var obj=new Object();
	$.post("GetStatus", {},
	function (data, status) {
		if (status == "success") {
			obj=JSON.parse(data);
			if (obj.status=="NotLogined") {
				alert("您还未登陆，正在转跳至登录页面！")
				$(window).attr('location','login.html');
			}else if (status == "success") {
				obj=JSON.parse(data);

				if(obj.status=="Gaming"){
					// 转跳游戏界面
					$(window).attr('location','room.html');
				}else if (obj.status=="Matching") {	
					matchingPage();
				}
			}  // if
		}
	}
	); // post
}

function refreshStatus(){
	var obj=new Object();
	$.post("GetStatus",	{},
	function (data, status) {
		if (status == "success") {
			obj=JSON.parse(data);
			if (obj.status=="NotLogined") {
				alert("您还未登陆，正在转跳至登录页面！")
				$(window).attr('location','login.html');
			}else if(obj.status=="Gaming"){
				// 转跳游戏界面
				$(window).attr('location','room.html');
			}else if (obj.status=="Matching") {	
				$("#matching-info").text("正在匹配，已匹配人数："+obj.data);
				window.setTimeout(refreshStatus,1000);
			}
		}  // if
	}
	); // post
}


function matchingPage(){
	$("#match-game").hide();
	$("#cancel-match").show();
	$("#matching-info").show();
	window.setTimeout(refreshStatus,1000);
}

function gameHallPage(){
	$("#match-game").show();
	$("#cancel-match").hide();
	$("#matching-info").hide();
	refreshStatusOnce();
}