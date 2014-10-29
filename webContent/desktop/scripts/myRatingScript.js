var movies = [];
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
	loadMyRatingMovieData();
}

function loadMyRatingMovieData(){
	//Load movie data for the rows
	$.ajax({
		url : "http://localhost:8080/Notflix/resources/rating/ratedfilms",
		dataType : "json",
		headers: {"token": localStorage.getItem("accesstoken")},
	}).fail(function(jqXHR, textStatus) {
		alert("API Requestfailed:" + textStatus);
	}).done(
			function(data) {
				$('#movieList').empty();
				movies = [];
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
				
				if(rowIndexClicked > -1){
					onRowClick(rowIndexClicked)
				}
			}
	);
}

function setMovie(index, data){
	movies[index]["imdb"] = data["movie"].ttNr;
	movies[index]["title"] = data["movie"].title;
	movies[index]["date"] = data["movie"].date;
	movies[index]["length"] = data["movie"].length;
	movies[index]["producer"] = data["movie"].producer;
	movies[index]["description"] = data["movie"].description;
	movies[index]["rating"] = data["rating"];
	update();
	loadImage(index, data["movie"].ttNr);
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
		//add remove rating option
		rowContent = rowContent + "<button id='btnRemoveRating' onclick='deleteRating(event," + i + ")'>Remove</button>";
		
		rowContent = rowContent + "</li>";
		$('#movieList').append(rowContent);
	}
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
	
	$('#movieDetailsContent').css("visibility", "visible");
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
}

function setClickers(){
	$("#movieDetailsClose").click(function(){
   		$('#movieDetailsContent').css("visibility", "hidden");
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
  	
  	$("#btnOpenEditRating").click(function(){
  		if(localStorage.getItem("accesstoken") != null){
			$("#editRating").css("visibility", "visible");	
  		}
  	});	
  	
  	$("#btnEditRating").click(function(){
  		if(localStorage.getItem("accesstoken") != null){
			var rating = $("#inputRating").val();
			if(rating != null && rating > 0 && rating < 11){
				editRating(rating);
			}
  		}
  	});	
  	
  	$("#editRatingClose").click(function(){
  		$("#editRating").css("visibility", "hidden");
  	});
  	
  	
}

function editRating(rating){
	var token = localStorage.getItem("accesstoken");
	
	if(token != null && rating != null && rowIndexClicked > -1){
		$.ajax({
			url : "http://localhost:8080/Notflix/resources/rating/edit/" + rating,
			dataType : "json",
			headers : {"token": token, "ttID":movies[rowIndexClicked]["imdb"]},
			type: "PUT",
		}).fail(function(jqXHR, textStatus) {
			alert("API Requestfailed:" + textStatus);
		}).done(
			function(data) {
				if(null != data["statusCode"]){
					//failed
				}else{
					//succesfull
					loadMyRatingMovieData();
				}
				$("#editRating").css("visibility", "hidden");
			}
		);
	}
}

function deleteRating(event, index){
	//stop bubbling effect
	if (event.stopPropagation) {
        event.stopPropagation()
    } else {
        event.cancelBubble = true
    }
    
    //delete rating call
    $.ajax({
		url : "http://localhost:8080/Notflix/resources/rating/delete",
		dataType : "json",
		type: "DELETE",
		headers: {"token": localStorage.getItem("accesstoken"), "ttID": movies[index]["imdb"]},
	}).fail(function(jqXHR, textStatus) {
		alert("API Requestfailed:" + textStatus);
	}).done(
			function(data) {
				if(null != data["statusCode"] && data["statusCode"] == "200"){
					//succesfull
					loadMyRatingMovieData();
				}else{
					//failed
				}
			}
	);
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
	window.location.assign("http://localhost:8080/Notflix");
}

