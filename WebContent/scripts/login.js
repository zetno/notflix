$(document).on("pageinit", "#loginpage", function() {

	$('#loginButton').click(function() {
		login();
	});

});

function login() {

	var username = $("#loginUsername").val();
	var password = $("#loginPassword").val();

	$.ajax({
		url : "http://localhost:8080/Notflix/resources/login",
		headers : {
			"username" : username,
			"password" : password
		},
		dataType : "json",
	}).fail(function(jqXHR, textStatus) {
		alert("API Requestfailed:" + textStatus);
	}).done(function(data) {
		var token = data.token;
		localStorage.setItem("token", token);

		if (token == null) {
			alert("login incorrect");
		} else {
			$.mobile.changePage("#moviepage");
		}

	});

}