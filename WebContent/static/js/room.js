$(document).ready(function(){
	// 设置异步为假
    $.ajaxSetup({ async: false });
    getVisibleGameData();

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
				$("#player-i").append('<div class="card"><input type="checkbox" name="vehicle" value="Bike">'+obj.cardsInMyHand[i].suit+obj.cardsInMyHand[i].rank+'</div>');
			}
		}  // if
	}
	); // post
}