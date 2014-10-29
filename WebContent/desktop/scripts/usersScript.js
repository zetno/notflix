var users = [];
var rowIndexClicked = -1;

$(document).ready(function() {
	init();
});

function init(){
	//check if someone's logged in
	if(localStorage.getItem("accesstoken") == null){
		$("#navUsers").css("visibility", "hidden");
		$("#navMyRatings").css("visibility", "hidden");
	}else{
		userLoggedIn(localStorage.getItem("username"));
	}

	setClickers();
	loadUsersData();
}

function loadUsersData(){
	
	//Load users data for the rows
	$.ajax({
		url : "http://localhost:8080/Notflix/resources/user/userlist",
		dataType : "json",
		headers: {"token": localStorage.getItem("accesstoken")},
	}).fail(function(jqXHR, textStatus) {
		alert("API Requestfailed:" + textStatus);
	}).done(
			function(data) {
				users = [];
				for (var i = 0; i < data.length; i++) {
					if(null != data[i]){
						users.push(
						{
							username:"",
							firstname:"",
							middlename:"",
							surname:"",
						});
						
						setUser(i, data[i]);
					}
				}
			}
	);
}

function setUser(index, data){
	users[index]["username"] = data.username;
	users[index]["firstname"] = data.firstName;
	users[index]["middlename"] = data.middleName;
	users[index]["surname"] = data.surname;
	
	update();
}

function update(){
	$('#usersList').empty();
	
	for (var i = 0; i < users.length; i++) {
		var rowContent = "<li onclick='onRowClick(" + i + ")'>";
		
		rowContent = rowContent + "<a id='row_username'>" + users[i]["username"] + "</a>";
		
		rowContent = rowContent + "</li>";
		$('#usersList').append(rowContent);
	}
}

function onRowClick(index){
	if(index < 0){
		return;
	}
	
	var user = users[index];
	rowIndexClicked = index;
	
	if(null != user){
		$('#userDetailsUsername a').text("Username: " + user["username"]);
		$('#userDetailsFirstname a').text("Firstname: " + user["firstname"]);
		$('#userDetailsMiddlename a').text("Middlename: " + user["middlename"]);
		$('#userDetailsSurname a').text("Surname: " + user["surname"]);
	}
	
	$('#userDetailsContent').css("visibility", "visible");
}

function setClickers(){
	$("#userDetailsClose").click(function(){
   		$('#movieDetailsContent').css("visibility", "hidden");
   		$('#btnOpenAddRating').css("visibility", "hidden");
  	});
  	
  	$("#signinClose").click(function(){
   		$('#signin').css("visibility", "hidden");
  	});
  	
  	$("#headerSignInOut").click(function(){
  		if(localStorage.getItem("accesstoken") == null){
  			
  		}else{
  			userLogOff();
  		}
  	});
  	
  	$("#userDetailsClose").click(function(){
   		$('#userDetailsContent').css("visibility", "hidden");
  	});
}

function userLoggedIn(username){
	$("#navUsers").css("visibility", "visible");
	$("#navMyRatings").css("visibility", "visible");
	$('#signin').css("visibility", "hidden");
	$('#headerSignInOut').text("Sign out");
	$('#loggedin').text(username);
	$("#loggedin").css("visibility", "visible");
}

function userLogOff(){
 	localStorage.clear();
	$("#navUsers").css("visibility", "hidden");
	$("#navMyRatings").css("visibility", "hidden");
	$('#loggedin').text("");
	$('#headerSignInOut').text("Sign in");
	window.location.assign("http://localhost:8080/notflix");
}

