var cardsPlayed=new Array();
var maxCards;
var timeLeft;
var timeStop=false;
$(document).ready(function () {
	
	refreshStatusOnce();
	$("#game-result").hide();
	// ----监听事件开始----
	var isListening = false;
	var websocket = null;
	// 判断当前浏览器是否支持WebSocket
	if ('WebSocket' in window && (!isListening)) {
		websocket = new WebSocket("ws://111.230.130.50:8080/PJ2/RoomDataInform");
		isListening=true;
	}
	websocket.onopen = function(){}
	// 当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常
	window.onbeforeunload = function () {
		websocket.close();
	}
	websocket.onmessage = function(event){
		switch (event.data) {
			case "Refresh":
				timeStop=true;
				getAndShowVisibleGameData();
				break;
			case "GameOver":
				timeStop=true;
				getAndShowGameResultData();
				break;
			default:
				break;
		}
	}
	// ----监听事件结束----

	// 事件委托，点击牌
	$("#cards").on("click", ".card", function () {
		var cardId = $(this).attr("id");
		var cardIndex = cardsPlayed.indexOf(cardId);
		if (cardIndex == -1) {	// 牌没被选中
			$(this).css("top", "-60px");
			cardsPlayed.push(cardId);
		} else {					// 牌已经被选中了
			cardsPlayed.splice(cardIndex, 1);
			$(this).css("top", "0px");
		}
	})
	// 出牌
	$("#play-card").click(function () {
		$.post("PlayCards",
			{
				cardsPlayed: cardsPlayed
			},
			function (data, status) {
				if (status=="success") {
					switch (data) {
						case "GameOver":
							// 游戏结束
							break;
						case "SuccessfulPlay":
							// showVisibleGameData();
							break;
						case "FailedPlay":
							messageBox("出牌不合法");
							break;
						default:
							break;
					}
				}
			}
		)
	})
	// PASS
	$("#pass").click(function () {
		$.post("PlayCards",
			{
				cardsPlayed:new Array()
			},
			function (data, status) {
				if (status=="success") {
					switch (data) {
						case "GameOver":
							// 游戏结束
							break;
						case "SuccessfulPlay":
							// showVisibleGameData();
							break;
						case "FailedPlay":
							messageBox("出牌不合法");
							break;
						default:
							break;
					}
				}
			}
		)
	})

	$("#game-result-OK").click(function(){
		$(window).attr('location','hall.html');
	})


//----------------------------------------获取游戏数据--------------------------------------------------------------
function getCardElement(card) {
	var cardElement = '<button class="card" id="{card}"><img src="static/images/cards/{card}.png" /></button>';
	cardElement = cardElement.replace(/\{card\}/g, card);
	return cardElement;
}
function showStaticGameData(obj) {
	$("#player-my>.name").text(obj.myName);
	$("#player-left>.name").text(obj.playerLeftName);
	$("#player-right>.name").text(obj.playerRightName);
	$("#player-my>.icon>img").attr("src", obj.myIcon);
	$("#player-left>.icon>img").attr("src", obj.playerLeftIcon);
	$("#player-right>.icon>img").attr("src", obj.playerRightIcon);
	$("#player-my>.number-of-cards").css("background-image", "url("+obj.myCardSkin+")");
	$("#player-left>.number-of-cards").css("background-image", "url("+obj.playerLeftCardSkin+")");
	$("#player-right>.number-of-cards").css("background-image", "url("+obj.playerRightCardSkin+")");

}
function showVisibleGameData(obj) {
	$("#player-my>.number-of-cards").text(obj.cardsInMyHand.length);
	$("#player-left>.number-of-cards").text(obj.playerLeftNumberOfCards);
	$("#player-right>.number-of-cards").text(obj.playerRightNumberOfCards);
	$("#cards").css("width", (obj.cardsInMyHand.length * 50 + 122).toString() + "px");
	$("#cards").html("");
	cardsPlayed=new Array();
	for (const i in obj.cardsInMyHand) {
		var cardElement = getCardElement(obj.cardsInMyHand[i]);
		$("#cards").append(cardElement);
	}
	// 最大牌
	if (obj.maxCards!=null) {
		$("#max-cards>ul").html("");
		$("#max-cards").css("width", (obj.maxCards.length*28+58)+"px");
		for (const i in obj.maxCards) {
			var cardElement='<li><img src="static/images/cards/{card}.png"></li>';
			cardElement = cardElement.replace(/\{card\}/g, obj.maxCards[i]);
			$("#max-cards>ul").append(cardElement);
		}
	}
	
	if (obj.myId!= obj.turn) {
		$("#play-card").attr("disabled", "disabled");
		$("#pass").attr("disabled", "disabled");
		$("#time-left").hide();
	} else {
		$("#play-card").removeAttr("disabled");
		$("#pass").removeAttr("disabled");
		timeStop=false;
		timeLeft=30;
		$("#time-left>#time").text(timeLeft);
		$("#time-left").show();
		window.setTimeout(reduceTime, 1000);
	}
}

function reduceTime(){
	timeLeft=timeLeft-1;
	$("#time-left>#time").text(timeLeft);
	if (timeStop) {
		// 不用做任何事
	}else if (timeLeft<=0) {
		$("#pass").click();
	}else{
		window.setTimeout(reduceTime, 1000);
	}
}

function getAndShowVisibleGameData() {
	$.post("GetVisibleGameData", {},
		function (data, status) {
			if (status == "success") {
				var obj = JSON.parse(data);
				showVisibleGameData(obj);
			}  // if
		}
	); // post
}
function getInitialGameData(){
	$.post("GetInitialGameData", {},
		function (data, status) {			
			var obj = JSON.parse(data);
			showStaticGameData(obj);
			showVisibleGameData(obj);
		}
	); // post
}
//------------------------------------游戏结束------------------------------------------------------
function getResultElement(roomIndex, name, carrotsChange) {
	var resultElement = '<tr><td>{roomIndex}</td><td>{name}</td><td class="carrots-change">{carrotsChange}</td>';
	resultElement = resultElement.replace(/\{roomIndex\}/g, roomIndex);
	resultElement = resultElement.replace(/\{name\}/g, name);
	resultElement = resultElement.replace(/\{carrotsChange\}/g, (carrotsChange>0)?("+"+carrotsChange):carrotsChange);
	return resultElement;
}
function showGameResultData(obj){
	for(i=0;i<3;i++){
		var resultElement=getResultElement(i, obj.names[i], obj.carrotsChange[i]);
		$("#result-table").append(resultElement);
	}
	$("#game-result").show();
}
function getAndShowGameResultData() {
	$.post("GetGameResult", {},
		function (data, status) {
			if (status == "success") {
				var obj = JSON.parse(data);
				showGameResultData(obj);
			}  // if
		}
	); // post
}
//----------------------------------检测状态---------------------------------------------------------
function refreshStatusOnce(){
	var obj=new Object();
	$.post("GetStatus", {},
		function (data, status) {
			if (status == "success") {
				obj=JSON.parse(data);
				switch (obj.status) {
					case "NotLogined":
						messageBox("您还未登陆，正在转跳至登录页面！")
						$(window).attr('location','login.html');
						break;
					case "Matching":
					case "Logined":
						$(window).attr('location','hall.html');
						break;
					case "Gaming":
						getInitialGameData();				
						break;
					default:
						break;
				}
			}  // if
		}
	); // post
}

})


function messageBox(message){
	$("#message-box").text(message).show();
	window.setTimeout(function(){
		$("#message-box").fadeOut(500)
	}, 2000);
}
