var movies = [];
var ratings = [];
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
	loadMovieData();
}

function loadMovieData(){
	//Load movie data for the rows
	$.ajax({
		url : "http://localhost:8080/Notflix/resources/movie/movielist",
		dataType : "json",
	}).fail(function(jqXHR, textStatus) {
		alert("API Requestfailed:" + textStatus);
	}).done(
			function(data) {
				movies = [];
				ratings = [];
				for (var i = 0; i < data.length; i++) {
					if(null != data[i]){
						movies.push(
						{
							imdb:"",
							title:"",
							date:"",
							length:"",
							producer:"",
							description:"",
							imageurl:"", 
							rating:0
						});
						
						setMovie(i, data[i]);
					}
				}
			}
	);
}

function setMovie(index, data){
	movies[index]["imdb"] = data.ttNr;
	movies[index]["title"] = data.title;
	movies[index]["date"] = data.date;
	movies[index]["length"] = data.length;
	movies[index]["producer"] = data.producer;
	movies[index]["description"] = data.description;
	update();
	loadRating(index, data.ttNr);
	loadImage(index, data.ttNr);
}

function setRating(index, rating){
	if(movies[index]["rating"] == 0){
		var newRating = rating;
	}else{
		var newRating = (Math.round(((movies[index]["rating"] + rating)/2) * 10) / 10);
	}
	movies[index]["rating"] = newRating;
	update();
}

function setImageUrl(index, imageurl){
	movies[index]["imageurl"] = imageurl;
	update();
}

function update(){
	$('#movieList').empty();
	
	for (var i = 0; i < movies.length; i++) {
		var rowContent = "<li ";
		
		if(null != movies[i]["imdb"] && "" != movies[i]["imdb"]){
			rowContent = rowContent + "onclick='onRowClick(" + i + ")'>";
		}else{
			rowContent = rowContent + ">";
		}
	
		if(null != movies[i]["imageurl"] && "" != movies[i]["imageurl"]){
			rowContent = rowContent + "<img class='row_image' src='" + movies[i]["imageurl"] + "'>";
		}else{
			rowContent = rowContent + "<img class='row_image' src='images/movie.jpg'>";
		}
	
		if(null != movies[i]["title"] && "" != movies[i]["title"]){
			rowContent = rowContent + "<a class='row_title'>" + movies[i]["title"] + "</a>";
		}
		
		if(null != movies[i]["rating"] && "" != movies[i]["rating"]){
			rowContent = rowContent + "<a style='color:" + getRatingColor(movies[i]["rating"]) + ";' class='row_rating'>" + movies[i]["rating"] + "</a>";
		}
		
		rowContent = rowContent + "</li>";
		$('#movieList').append(rowContent);
	}
}

function loadRating(index, imdb){
	$.ajax({
		url : "http://localhost:8080/Notflix/resources/rating/filmratings",
		dataType : "json",
		headers: {"ttID":imdb},
	}).fail(function(jqXHR, textStatus) {
		alert("API Requestfailed:" + textStatus);
	}).done(
			function(data) {
				
				for(var i = 0; i < data.length; i++){
					
					if(null != data[i]){
						ratings.push(
						{
							imdb:data[i]["movie"]["ttNr"],
							rating:data[i].rating,
							username:data[i]["user"]["username"],
							
						});
						setRating(index, data[i].rating);
					}
				}
				
				if(rowIndexClicked >= 0){
					onRowClick(rowIndexClicked);
				}
			}
	);
}

function loadImage(index, imdb){
	$.ajax({
		url : "http://www.omdbapi.com/?i=tt" + imdb,
		dataType : "json",
	}).fail(function(jqXHR, textStatus) {
		alert("API Requestfailed:" + textStatus);
	}).done(
			function(data) {
				if(null != data.Poster){
					setImageUrl(index, data.Poster);
				}
			}
	);
}

function getRatingColor(rating){
	var green = Math.floor(255 / (10/rating));
	var red = 255 - green;
	var blue = 0;
	
	return "rgb(" + red + "," + green + "," + blue + ")";
}

function onRowClick(index){
	if(index < 0){
		return;
	}
	
	var movie = movies[index];
	rowIndexClicked = index;
	
	if(null != movie){
		$('#movieDetailsTitle a').text(movie["title"]);
		$('#movieDetailsProducer a').text("Producer: " + movie["producer"]);
		$('#movieDetailsImdb a').text("Imdb: tt" + movie["imdb"]);
		$('#movieDetailsLength a').text("Length: " + movie["length"] + " minutes");
		$('#movieDetailsRating a').text("Rating: " + movie["rating"]);
		$('#movieDetailsDate a').text("Release date: " + new Date(movie["date"]).getFullYear());
		$('#movieDetailsDescription a').text("Description: \r\n" + movie["description"]);
		if(null != movie["imageurl"] && "" != movie["imageurl"]){
			$('#movieDetailsImage img').attr('src', movie["imageurl"]);
		}
	}
	
	//hide add rating button if the user allready rated the movie
	$('#btnOpenAddRating').css("visibility", "visible");
	for(var i = 0; i < ratings.length; i++){
		if(ratings[i]["imdb"] == movie["imdb"] && ratings[i]["username"] == localStorage.getItem("username")){
			$('#btnOpenAddRating').css("visibility", "hidden");
		}
	}
	
	$('#movieDetailsContent').css("visibility", "visible");
}

function setClickers(){
	$("#movieDetailsClose").click(function(){
   		$('#movieDetailsContent').css("visibility", "hidden");
   		$('#btnOpenAddRating').css("visibility", "hidden");
  	});
  	
  	$("#signinClose").click(function(){
   		$('#signin').css("visibility", "hidden");
  	});
  	
  	$("#registerClose").click(function(){
   		$('#register').css("visibility", "hidden");
  	});
  	
  	$("#headerSignInOut").click(function(){
  		if(localStorage.getItem("accesstoken") == null){
  			$('#signin').css("visibility", "visible");
  		}else{
  			userLogOff();
  		}
   		
  	});
  	
  	$("#btnSignIn").click(function(){
   		var username = $('#inputUsername').val();
   		var password = $('#inputPassword').val();
   		
   		if(null != username
   			&& "" != username
   			&& null != password
   			&& "" != password){
   		
   			userLogIn(username, password);
   		}
  	});
  	
  	$("#btnOpenAddRating").click(function(){
  		if(localStorage.getItem("accesstoken") != null){
			$("#addRating").css("visibility", "visible");	
  		}
  	});	
  	
  	$("#btnAddRating").click(function(){
  		if(localStorage.getItem("accesstoken") != null){
			var rating = $("#inputRating").val();
			if(rating != null && rating > 0 && rating < 11){
				addRating(rating);
			}
  		}
  	});	
  	
  	$("#addRatingClose").click(function(){
  		$("#addRating").css("visibility", "hidden");
  	});
  	
  	$("#headerRegister").click(function(){
  		$("#register").css("visibility", "visible");
  	});
  	
  	$("#btnRegister").click(function(){
   		var firstname = $('#inputRegisterFirstname').val();
   		var middlename = $('#inputRegisterMiddlename').val();
   		var surname = $('#inputRegisterSurname').val();
   		var username = $('#inputRegisterUsername').val();
   		var password = $('#inputRegisterPassword').val();
   		
   		if(null != firstname
   			&& "" != firstname
   			&& null != surname
   			&& "" != surname
   			&& null != username
   			&& "" != username
   			&& null != password
   			&& "" != password){
   		
   			registerUser(firstname, middlename, surname, username, password);
   		}
  	});
  	
}

function registerUser(firstname, middlename, surname, username, password){
	$.ajax({
			url : "http://localhost:8080/Notflix/resources/user/add/",
			dataType : "json",
			headers : {
				"firstname": firstname,
				"middlename": middlename,
				"surname": surname,
				"username": username,
				"password": password,
			},
			type: "POST",
		}).fail(function(jqXHR, textStatus) {
			alert("API Requestfailed:" + textStatus);
		}).done(
			function(data) {
				if(null != data && data["username"] != null){
					alert(data["username"] + " has succesfully registered.");
				}else{
					alert("Failed to register");
				}
				$("#register").css("visibility", "hidden");
			}
		);
}

function addRating(rating){
	var token = localStorage.getItem("accesstoken");
	
	if(token != null && rating != null){
		$.ajax({
			url : "http://localhost:8080/Notflix/resources/rating/add/" + rating,
			dataType : "json",
			headers : {"token": token, "ttID":movies[rowIndexClicked]["imdb"]},
			type: "POST",
		}).fail(function(jqXHR, textStatus) {
			alert("API Requestfailed:" + textStatus);
		}).done(
			function(data) {
				if(null != data["statusCode"] && data["statusCode"] == "404"){
					//user allready has film rated
				}else{
					//succesfull
					loadMovieData();
				}
				$("#addRating").css("visibility", "hidden");
			}
		);
	}
}

function userLogIn(username, password){
	var accesstoken = getAccessToken(username, password);
}

function getAccessToken(username, password){
	$.ajax({
		url : "http://localhost:8080/Notflix/resources/login",
		dataType : "json",
		headers : {"username": username, "password":password},
	}).fail(function(jqXHR, textStatus) {
		alert("API Requestfailed:" + textStatus);
	}).done(
			function(data) {
				var token = data["token"];
	
				if(null != token && "" != token){
					getAccessTokenSuccesfull(username, token);
				}else{
					getAccessTokenFailed();
				}
			}
	);
}	

function getAccessTokenSuccesfull(username, accesstoken){
	localStorage.clear();
	localStorage.setItem("accesstoken",accesstoken);
	localStorage.setItem("username",username);
	userLoggedIn(username);
}

function userLoggedIn(username){
	$("#navUsers").css("visibility", "visible");
	$("#navMyRatings").css("visibility", "visible");
	$('#signin').css("visibility", "hidden");
	$('#headerSignInOut').text("Sign out");
	$('#loggedin').text(username);
	$("#loggedin").css("visibility", "visible");
	$("#headerRegister").css("visibility", "hidden");
}

function userLogOff(){
 	localStorage.clear();
	$("#navUsers").css("visibility", "hidden");
	$("#navMyRatings").css("visibility", "hidden");
	$('#loggedin').text("");
	$('#headerSignInOut').text("Sign in");
	$("#headerRegister").css("visibility", "visible");
}

