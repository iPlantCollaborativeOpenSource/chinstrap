document.addEventListener("DOMContentLoaded", getApps);

function getApps() {

	var request = new XMLHttpRequest();
	request.open("GET", "/get-apps");

	request.onreadystatechange = function() {
		if (request.readyState == 4) {
			var response = JSON.parse(request.responseText);

			$('#running').html(response['running']);
			$('#submitted').html(response['submitted']);
			$('#completed').html(response['completed']);
			$('#running-apps').html(response['running-names']);
			$('#submitted-apps').html(response['submitted-names']);
		}
	}
	request.send();
};
