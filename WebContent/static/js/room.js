$(document).ready(function(){
	var cardsPlayed=new Array();
	// 设置异步为假
    $.ajaxSetup({ async: false });
    getVisibleGameData();

	$(".card").click(function(){
		
	})
});


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
				var cardString=obj.cardsInMyHand[i].suit+obj.cardsInMyHand[i].rank;
				$("#player-i").append('<button class="card" id="'+cardString+'">'+cardString+'</button>');
			}
		}  // if
	}
	); // post
}