document.addEventListener("DOMContentLoaded", getComponents);

function getComponents() {

	var request = new XMLHttpRequest();
	request.open("GET", "/get-components");

	request.onreadystatechange = function() {
		if (request.readyState == 4) {
			var response = JSON.parse(request.responseText);

			document.getElementById('all').innerHTML = response['all'];
			document.getElementById('private').innerHTML = response['private'];
			document.getElementById('public').innerHTML = response['public'];
		}
	}
	request.send();
}
