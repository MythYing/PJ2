$(document).ready(function () {
	$("#register-button").click(function () {
		$.post("Register",
			{
				name: $("#name").val(),
				password: md5($("#password").val())
			},
			function (data, status) {

			}
		);// post
	})
});