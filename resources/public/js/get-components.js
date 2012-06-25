document.addEventListener("DOMContentLoaded", getComponents);

function getComponents() {

	var request = new XMLHttpRequest();
	request.open("GET", "/get-components");

	request.onreadystatechange = function() {
		if (request.readyState == 4) {
			var response = JSON.parse(request.responseText);

			document.getElementById('all').innerHTML = response['all'];
			document.getElementById('with').innerHTML = response['with'];
			document.getElementById('without').innerHTML = response['without'];
		}
	}
	request.send();
}
