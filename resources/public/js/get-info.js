function getInfo() {

	var request = new XMLHttpRequest();
    var date = $('#date').val();
	request.open("GET", "/get-info?date");

	request.onreadystatechange = function() {
		if (request.readyState == 4) {
			var response = request.responseText;

			document.getElementById('inner').innerHTML = date;
		}
	}
	request.send();
};
