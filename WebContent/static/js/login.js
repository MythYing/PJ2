$(document).ready(function(){
	// 登录
	$("#login-button").click(function(){
		$.post("Login",
			{
				pid: $("#login-pid").val()
			},
			function (data, status) {
				if (status == "success") {
					alert("登陆成功，欢迎您，" + data);
					$(window).attr('location','hall.html');
				}  // if
			}
		);//post       
	}) // click
});