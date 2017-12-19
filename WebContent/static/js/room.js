var websocket = null;
//判断当前浏览器是否支持WebSocket
if ('WebSocket' in window) {
	websocket = new WebSocket("ws://localhost:8080/PJ2/RoomDataInform");
}
else {
	alert('当前浏览器不支持websocket');
}
websocket.onopen = function(){}
// 当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常
window.onbeforeunload = function () {
	websocket.close();
}
websocket.onmessage = function(event){
	if(event.data=="Refresh"){
		getVisibleGameData();
	}
}

$(document).ready(function () {
	// 设置异步为假
	$.ajaxSetup({ async: false });
	getVisibleGameData();
	var cardsPlayed = new Array();
	// 点击牌
	$(".card").click(function () {
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
							// getVisibleGameData();
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


});

function getCardElement(card) {
	var cardElement = '<button class="card" id="{card}"><img src="static/images/cards/{card}.png" /></button>';
	cardElement = cardElement.replace(/\{card\}/g, card);
	return cardElement;
}
function getVisibleGameData() {
	$.post("GetVisibleGameData", {},
		function (data, status) {
			if (status == "success") {
				var obj = JSON.parse(data);
				$("#player-left>.name").text(obj.playerLeftName);
				$("#player-left>.number-of-cards").text(obj.playerLeftNumberOfCards);
				$("#player-right>.name").text(obj.playerRightName);
				$("#player-right>.number-of-cards").text(obj.playerRightNumberOfCards);
				$("#cards").css("width", (obj.cardsInMyHand.length * 50 + 122).toString() + "px");
				$("#cards").html("");
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
			}  // if
		}
	); // post
}