var cardsPlayed=new Array();
$(document).ready(function () {	
	refreshStatusOnce();
	$("#game-result").hide();
	// ----监听事件开始----
	var isListening = false;
	var websocket = null;
	// 判断当前浏览器是否支持WebSocket
	if ('WebSocket' in window && (!isListening)) {
		websocket = new WebSocket("ws://localhost:8080/PJ2/RoomDataInform");
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
				getAndShowVisibleGameData();
				break;
			case "GameOver":
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
					var obj=JSON.parse(data);
					switch (obj.status) {
						case "GameOver":
							// 游戏结束
							break;
						case "SuccessfulPlay":
							// showVisibleGameData();
							break;
						case "FailedPlay":
							alert("出牌不合法");
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
					var obj=JSON.parse(data);
					switch (obj.status) {
						case "GameOver":
							// 游戏结束
							break;
						case "SuccessfulPlay":
							// showVisibleGameData();
							break;
						case "FailedPlay":
							alert("出牌不合法");
							break;
						default:
							break;
					}
				}
			}
		)
	})


})
//----------------------------------------获取游戏数据--------------------------------------------------------------
function getCardElement(card) {
	var cardElement = '<button class="card" id="{card}"><img src="static/images/cards/{card}.png" /></button>';
	cardElement = cardElement.replace(/\{card\}/g, card);
	return cardElement;
}
function showVisibleGameData(obj) {
	$("#player-left>.name").text(obj.playerLeftName);
	$("#player-left>.number-of-cards").text(obj.playerLeftNumberOfCards);
	$("#player-right>.name").text(obj.playerRightName);
	$("#player-right>.number-of-cards").text(obj.playerRightNumberOfCards);
	$("#cards").css("width", (obj.cardsInMyHand.length * 50 + 122).toString() + "px");
	$("#cards").html("");
	cardsPlayed=new Array();
	for (const i in obj.cardsInMyHand) {
		var cardElement = getCardElement(obj.cardsInMyHand[i]);
		$("#cards").append(cardElement);
	}
	if (obj.myId!= obj.turn) {
		$("#play-card").attr("disabled", "disabled");
		$("#pass").attr("disabled", "disabled");
		$("#time-left").hide();
	} else {
		$("#play-card").removeAttr("disabled");
		$("#pass").removeAttr("disabled");
		$("#time-left").show();
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
//------------------------------------游戏结束------------------------------------------------------
function getResultElement(roomIndex, name, carrotChange) {
	var resultElement = '<tr><td>{roomIndex}</td><td>{name}</td><td>{carrotChange}</td>';
	cardElement = cardElement.replace(/\{roomIndex\}/g, roomIndex);
	cardElement = cardElement.replace(/\{name\}/g, name);
	cardElement = cardElement.replace(/\{carrotChange\}/g, carrotChange);
	return resultElement;
}
function showGameResultData(obj){
	for(i=0;i<3;i++){
		var resultElement=getResultElement(i, obj.names[i], carrotChanges[i]);
		$("#result-table").append(resultElement);
	}
	$("#game-result").show();
}
function getAndShowGameResultData() {
	$.post("getGameResultData", {},
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
						alert("您还未登陆，正在转跳至登录页面！")
						$(window).attr('location','login.html');
						break;
					case "Matching":
					case "Logined":
						$(window).attr('location','hall.html');
						break;
					case "Gaming":
						getAndShowVisibleGameData();				
						break;
					default:
						break;
				}
			}  // if
		}
	); // post
}