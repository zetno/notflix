$(window).on("hashchange", function() {
	getMovies();
});

window.addEventListener("load", function() {
	getMovies();
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
				for (var int = 0; int < data.length; int++) {
					var array_element = data[int];

					var descr = array_element.description;
					var title = array_element.title;

					$('#movieListview').append(
							"<li><a href='dialog.html' data-rel='dialog'>"
									+ title + "</a></li>").listview('refresh');
				}

			});

	;
}
