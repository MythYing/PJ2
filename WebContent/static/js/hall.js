$(document).ready(function(){
	// 设置异步为假
	$.ajaxSetup (
		{
			async: false
		});
	gameHallPage();
	$("#pid").text("欢迎您，"+$.cookie("pid"));

	//  寻找对局
	$("#match-game").click(function(){
		if($.cookie("pid")==null){
			alert("登录信息失效，请重新登录");
		}else{
			$.post("MatchGame",
				{
					pid:$.cookie("pid"),
				},
				function(data,status){
				}
			);//post 
			matchingPage();
		}
		
	}) // click
	$("#cancel-match").click(function(){
		if($.cookie("pid")==null){
			alert("登录信息失效，请重新登录");
		}else{
			$.post("CancelMatch",
				{
					pid:$.cookie("pid"),
				},
				function(data,status){
				}
			);//post 
			gameHallPage();
		}
		
	}) // click
	// 退出登录
	$("#logout-button").click(function(){
		$.removeCookie("pid");
		$(window).attr('location','index.html');
	})
});

function refreshStatusOnce(){
	var obj=new Object();
	$.post("GetStatus",
	{
		pid: $.cookie("pid")
	},
	function (data, status) {
		if (status == "success") {
			obj=JSON.parse(data);

			if(obj.status=="Gaming"){
				// 转跳游戏界面
				$(window).attr('location','room.html');
			}else if (obj.status=="Matching") {	
				matchingPage();
			}
		}  // if
	}
	); // post
}

function refreshStatus(){
	var obj=new Object();
	$.post("GetStatus",
	{
		pid: $.cookie("pid")
	},
	function (data, status) {
		if (status == "success") {
			obj=JSON.parse(data);

			if(obj.status=="Gaming"){
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