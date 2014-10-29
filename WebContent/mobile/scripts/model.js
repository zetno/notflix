var movies = [];
var movielist = [];

var averageRating = 0;
var ownRating = "-";
var hasUserRating = false;

$(document).on("pageinit", "#moviepage", function() {

	getMovies();

	var ttNr = "";

	$('#movieListview').on("click", "li a", function() {

		ttNr = $(this).attr('id');

		var movie = getMovieById(ttNr);

		var title = movie.mtitle;
		var description = movie.mdescription;

		$('#titledialog').text(title);
		$('#descriptiondialog').text(description);

		getRatingsFromMovie(ttNr);
		getRatingsFromUser(ttNr);
	});

	$("#ratebutton").on("click", function() {

		var value = $("#select-rating").val();

		addRatingToMovie(ttNr, value);
	});

});

function getMovies() {
	$.ajax({
		url : "http://localhost:8080/Notflix/resources/movie/movielist",
		dataType : "json",
	}).fail(function(jqXHR, textStatus) {
		alert("API Requestfailed:" + textStatus);
	}).done(
			function(data) {

				// Do something with the data
				$('#movieListview').empty();

				movieItems = [];
				movieList = [];

				for (var i = 0; i < data.length; i++) {
					var array_element = data[i];

					var descr = array_element.description;
					var title = array_element.title;

					var ttNr = array_element.ttNr;

					movieItems.push({
						mttNr : ttNr,
						mtitle : title,
						mdescription : descr
					});
					movieList.push(movieItems[i]);

					$('#movieListview').append(
							"<li><a href='#dialogpage' id=" + ttNr
									+ " data-rel='dialog'>" + title
									+ "</a></li>").listview('refresh');
				}

			});
}

function getMovieById(id) {

	for ( var key in movieList) {

		var obj = movieList[key];

		if (obj.mttNr == id) {
			return obj;
		}
	}
	return null;
}

function addRatingToMovie(ttNr, rating) {

	if (rating === "no-rating") {
		removeRatingFromMovie(ttNr);
	} else {
		$.ajax(
				{
					url : "http://localhost:8080/Notflix/resources/rating/add/"
							+ rating,
					headers : {
						token : localStorage.getItem("token"),
						ttID : ttNr
					},
					type : "POST",
					dataType : "json",
				}).fail(function(jqXHR, textStatus) {

			alert("API Requestfailed:" + textStatus);

		}).done(function(data) {

			// if already rated this movie, edit it
			if (data.statusCode == "404") {
				editRatingFromMovie(ttNr, rating);
			}
		});
	}
}

function removeRatingFromMovie(ttNr) {

	$.ajax({
		url : "http://localhost:8080/Notflix/resources/rating/delete",
		headers : {
			token : localStorage.getItem("token"),
			ttID : ttNr
		},
		type : "DELETE",
		dataType : "json",
	}).fail(function(jqXHR, textStatus) {
		alert("API Requestfailed:" + textStatus);
	}).done(function(data) {
	});

}

function editRatingFromMovie(ttNr, newRating) {

	$.ajax(
			{
				url : "http://localhost:8080/Notflix/resources/rating/edit/"
						+ newRating,
				headers : {
					token : localStorage.getItem("token"),
					ttID : ttNr
				},
				type : "PUT",
				dataType : "json",
			}).fail(function(jqXHR, textStatus) {
		alert("API Requestfailed:" + textStatus);
	}).done(function(data) {
	});

}

function getRatingsFromUser(id) {

	$.ajax({
		url : "http://localhost:8080/Notflix/resources/rating/ratedfilms",
		headers : {
			token : localStorage.getItem("token"),
		},
		type : "GET",
		dataType : "json",
	}).fail(function(jqXHR, textStatus) {
		alert("API Requestfailed:" + textStatus);
	}).done(function(data) {

		for (var int = 0; int < data.length; int++) {

			array_element = data[int];

			var rating = array_element.rating;
			var movie = array_element.movie;

			var ttNr = movie["ttNr"];

			if (id == ttNr) {
				ownRating = rating;
				hasUserRating = true;
			}
		}
		if (hasUserRating == true) {
			$("#userrating").text("My rating: " + ownRating);
		} else {
			$("#userrating").text("My rating: -");
		}
		ownRating = "-";
	});

}

function getRatingsFromMovie(ttNr) {

	$.ajax({
		url : "http://localhost:8080/Notflix/resources/rating/filmratings",
		headers : {
			token : localStorage.getItem("token"),
			ttID : ttNr
		},
		dataType : "json",
	}).fail(function(jqXHR, textStatus) {
		alert("API Requestfailed:" + textStatus);
	}).done(function(data) {

		var ratings = [];
		var rating = null;

		for (var i = 0; i < data.length; i++) {

			var array_element = data[i];

			rating = array_element.rating;

			ratings.push(rating);
		}

		averageRating = 0;

		if (ratings.length > 2) {
			var movieRating = 0;

			for (var int = 0; int < ratings.length; int++) {
				movieRating = movieRating + ratings[int];
			}
			averageRating = movieRating / ratings.length;

			averageRating = Math.round(averageRating);

			$('#actualrating').text("Average: " + averageRating);
		} else {
			$('#actualrating').text("Average: -");
		}

	});

}
