function getInfo() {

	var request = new XMLHttpRequest();
    var date = $.datepicker.formatDate('@', $('#date').datepicker('getDate'));
	request.open("GET", "/get-info/" + date);

	request.onreadystatechange = function() {
		if (request.readyState == 4) {
			var response = request.responseText;

			$('#inner').html("Date: " + date + "\n Response: " + response);
		}
	}
	request.send();
};
