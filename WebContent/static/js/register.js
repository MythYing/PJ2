$(document).ready(function () {
	$("#register-button").click(function () {
		if ($("#password").val()==$("#password2").val()) {
			$.post("Register",
			{
				name: $("#name").val(),
				password: md5($("#password").val())
			},
			function (data, status) {
				if (data=="Success") {
					messageBox("注册成功，正在转到登录界面...")
					$(window).attr('location','login.html');
				}else{
					messageBox("注册失败，用户名被占用！")
				}
			}
		);// post
		} else {
			messageBox("两次密码输入不同");
		}	
	})
});

function messageBox(message){
	$("#message-box").text(message).show();
	window.setTimeout(function(){
		$("#message-box").fadeOut(500)
	}, 2000);
}