$(document).ready(function(){
	// 登录
	$("#login-button").click(function(){
		$.post("Login",
			{
				name:$("#name").val(),
				password: md5($("#password").val())
			},
			function (data, status) {
				if (status == "success") {
					if (data!=-1) {
						messageBox("登陆成功，欢迎您，" + data);
						$(window).attr('location','hall.html');
					}else{
						messageBox("登录失败！");
					}
					
				}  // if
			}
		);//post       
	}) // click
	$("#register-button").click(function(){
		$(window).attr('location','register.html');
	})
	$("input").keydown(function(event){
		if (event.which==13) {
			$("#login-button").click();
		}	
	  });
});


function messageBox(message){
	$("#message-box").text(message).show();
	window.setTimeout(function(){
		$("#message-box").fadeOut(500)
	}, 2000);
}