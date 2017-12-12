$(document).ready(function(){
	var cardsPlayed=new Array();
	// 设置异步为假
    $.ajaxSetup({ async: false });
    getVisibleGameData();
	// 点击牌
	$(".card").click(function(){
		var cardId=$(this).attr("id");
		var cardIndex=cardsPlayed.indexOf(cardId);
		if (cardIndex==-1) {	// 牌没被选中
			$(this).css("top","-60px");
			cardsPlayed.push(cardId);
		}else{					// 牌已经被选中了
			cardsPlayed.splice(cardIndex,1);
			$(this).css("top","0px");
		}
	})
	// 出牌
	$("#play-card").click(function(){
		$.post("PlayCards",
		{
			pid: $.cookie("pid"),
			cardsPlayed: cardsPlayed
		},
		function(data, status){

		})
	})

});

function getCardElement(card) {
	var cardElement='<button class="card" id="{card}"><img src="static/images/cards/{card}.png" /></button>';
	cardElement=cardElement.replace(/\{card\}/g,card);
	return cardElement;
}
function getVisibleGameData(){
	$.post("GetVisibleGameData",
	{
		pid: $.cookie("pid")
	},
	function (data, status) {
		if (status == "success") {
            var obj=JSON.parse(data);           
			$("#player-left>.name").text(obj.playerLeftName);
			$("#player-left>.number-of-cards").text(obj.playerLeftNumberOfCards);
			$("#player-right>.name").text(obj.playerRightName);	
			$("#player-right>.number-of-cards").text(obj.playerRightNumberOfCards);
			$("#cards").css("width",(obj.cardsInMyHand.length*50+122).toString()+"px");
			for (const card in obj.cardsInMyHand) {
				var cardElement=getCardElement(card);
				$("#cards").append(cardElement);
			}
			if (obj.myRoomIndex==obj.turn) {
				
			}
		}  // if
	}
	); // post
}