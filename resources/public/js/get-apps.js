document.addEventListener("DOMContentLoaded", function(){getApps();window.setInterval(getApps,36000);});

function getApps() {

	var request = new XMLHttpRequest();
	request.open("GET", "/get-apps");

	request.onreadystatechange = function() {
		if (request.readyState == 4) {
			var response = JSON.parse(request.responseText);

			$('#running').html(response['running']);
			$('#submitted').html(response['submitted']);
			$('#failed').html(response['failed']);
			$('#completed').html(response['completed']);
			$('#running-apps').html(response['running-names']);
			$('#failed-apps').html(response['failed-names']);
			$('#submitted-apps').html(response['submitted-names']);
		}
	}
	request.send();
};
