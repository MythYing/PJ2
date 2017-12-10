$(document).ready(function(){
	var cardsPlayed=new Array();
	// 设置异步为假
    $.ajaxSetup({ async: false });
    getVisibleGameData();

	$(".card").click(function(){
		var cardId=$(this).attr("id");
		var cardIndex=cardsPlayed.indexOf(cardId);
		if (cardIndex==-1) {
			$(this).css("top","0px");
			cardsPlayed.push(cardId);
		}else{
			cardsPlayed.splice(cardIndex,1);
			$(this).css("top","60px");
		}
		
	})
});

function getCardElement(cardId) {
	var cardElement='<button class="card" style="" id="{cardId}"><img width="172px" height="256px" src="static/images/cards/{cardId}.png" /></button>';
	cardElement=cardElement.replace(/\{cardId\}/g,cardId);
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
            $("#player-left").text(obj.playerLeftName+obj.playerLeftNumberOfCards);
			$("#player-right").text(obj.playerRightName+obj.playerRightNumberOfCards);
			for (const i in obj.cardsInMyHand) {
				var cardId=obj.cardsInMyHand[i].suit+obj.cardsInMyHand[i].rank;
				var cardElement=getCardElement(cardId);
				$("#player-i").append(cardElement);
			}
		}  // if
	}
	); // post
}