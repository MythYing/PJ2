$(document).ready(function(){
	var cardsPlayed=new Array();
	// 设置异步为假
    $.ajaxSetup({ async: false });
    getVisibleGameData();

	$(".card").click(function(){
		var cardId=$(this).attr("id");
		var cardIndex=cardsPlayed.indexOf(cardId);
		// 点击出来
		if (cardIndex==-1) {
			$(this).css("top","-60px");
			cardsPlayed.push(cardId);
		}else{
			cardsPlayed.splice(cardIndex,1);
			$(this).css("top","0px");
		}
		
	})
});

function getCardElement(cardId) {
	var cardElement='<button class="card" id="{cardId}"><img src="static/images/cards/{cardId}.png" /></button>';
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
			$("#player-left>.name").text(obj.playerLeftName);
			$("#player-left>.number-of-cards").text(obj.playerLeftNumberOfCards);
			$("#player-right>.name").text(obj.playerRightName);	
			$("#player-right>.number-of-cards").text(obj.playerRightNumberOfCards);
			$("#cards").css("width",(obj.cardsInMyHand.length*50+122).toString()+"px");
			for (const i in obj.cardsInMyHand) {
				var cardId=obj.cardsInMyHand[i].suit+obj.cardsInMyHand[i].rank;
				var cardElement=getCardElement(cardId);
				$("#cards").append(cardElement);
			}
		}  // if
	}
	); // post
}