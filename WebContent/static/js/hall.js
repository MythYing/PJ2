$(document).ready(function(){
	getGameRecords();
	var shopVue = new Vue({
		el: "#shop",
		data: {
			type: "icon",
			itemClass: "item-icon"
		},
		computed: {
			items: function(){
				var obj;
				$.ajax({
					type: "POST",
					url: "GetShopItem",
					data: {type: this.type},
					async: false,
					success: function(data){
						obj = JSON.parse(data);
					}
				});
				return obj;
			}
		},
		watch: {
			type: function(newType){
				switch (newType) {
					case "icon":
						this.itemClass="item-icon";
						break;
					case "card-skin":
						this.itemClass="item-card-skin";
						break;
					default:
						break;
				}
			}
		}
	});
	var bagVue = new Vue({
		el: "#bag",
		data: {
			type: "icon",
			itemClass: "item-icon"
		},
		computed: {
			items: function(){
				var obj;
				$.ajax({
					type: "POST",
					url: "GetBagItem",
					data: {type: this.type},
					async: false,
					success: function(data){
						obj = JSON.parse(data);
					}
				});
				return obj;
			}
		},
		watch: {
			type: function(newType){
				switch (newType) {
					case "icon":
						this.itemClass="item-icon";
						break;
					case "card-skin":
						this.itemClass="item-card-skin";
						break;
					default:
						break;
				}
			}
		}
	});
	$("#shop .items").mCustomScrollbar({
		axis: "y",
		theme: "inset"
	});
	$("#bag .items").mCustomScrollbar({
		axis: "y",
		theme: "inset"
	});
	// 设置异步为假
	// $.ajaxSetup ({async: false});
	$("#content>div").eq(0).show().siblings().hide();
	gameHallPage();
	$("nav>ul>li").click(function(){
		var index = $("nav>ul>li").index($(this));
		$("#content>div").eq(index).show().siblings().hide();
	})
	// 规则
	$("#show-rules").mouseenter(function(){
		$("#rules").show();
	}).mouseleave(function(){
		$("#rules").hide();
	})
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

	// --------------------------------商店------------------------------------------
	$(".items").on("mouseenter", "li",function(){
		$(this).children("button").show();
	}).on("mouseleave", "li",function(){
		$(this).children("button").hide();
	})
	
	$(".items").on("click", ".buy", function(){
		var item=$(this).parent().attr("id").match(/\d+/);
		$.post("BuyItem", {item: item[0]}, function(data, status){
			if(data=="Success"){
				messageBox("购买成功!");
				getInfo();
			}else{
				messageBox("购买失败！请检查:\n1.金币是否充足\n2.是否已拥有");
			}
		})
	})

	$(".items").on("click", ".use", function(){
		var item=$(this).parent().attr("id").match(/\d+/);
		$.post("UseItem", {item: item[0]}, function(data, status){
			if(data=="Success"){
				messageBox("使用成功！");
				getInfo();
			}else{
				messageBox("使用失败！请检查:\n1.是否已拥有");
			}
		})
	})
});

function refreshStatusOnce(){
	var obj=new Object();
	$.post("GetStatus", {},
		function (data, status) {
			if (status == "success") {
				obj=JSON.parse(data);
				if (obj.status=="NotLogined") {
					messageBox("您还未登陆，正在转跳至登录页面！")
					$(window).attr('location','login.html');
				}else if(obj.status=="Gaming"){
					// 转跳游戏界面
					$(window).attr('location','room.html');
				}else if (obj.status=="Matching") {	
					matchingPage();
				}else {
					getInfo();
				}
			}  // if
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
					messageBox("您还未登陆，正在转跳至登录页面！")
					$(window).attr('location','login.html');
				}else if(obj.status=="Gaming"){
					// 转跳游戏界面
					$(window).attr('location','room.html');
				}else if (obj.status=="Matching") {	
					$("#matching-info").text("正在匹配，已匹配人数："+obj.data);
					window.setTimeout(refreshStatus, 1000);
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

function getInfo() {
	$.post("GetInfo", {}, function(data, status){
		obj=JSON.parse(data);
		$("#name").text(obj.name);
		$("#carrots>#quantity").text(obj.carrots);
		$("#icon>img").attr("src", obj.icon);
	})
}


function getGameRecords() {
	$.post("GetGameRecords", {}, function(data, status){
		var records=JSON.parse(data);
		new Vue({
			el: "#game-records-table",
			data: {
				records: records
			}
		})
		$("#game-records-table").mCustomScrollbar({
			axis: "y",
			theme: "inset"
		});
	})
}


function messageBox(message){
	$("#message-box").text(message).show();
	window.setTimeout(function(){
		$("#message-box").fadeOut(500)
	}, 2000);
}