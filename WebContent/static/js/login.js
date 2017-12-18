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
						alert("登陆成功，欢迎您，" + data);
						$(window).attr('location','hall.html');
					}else{
						alert("登录失败！");
					}
					
				}  // if
			}
		);//post       
	}) // click
	$("#register-button").click(function(){
		$(window).attr('location','register.html');
	})
});