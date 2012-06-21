function getJobs() {

	var request = new XMLHttpRequest();
	request.open("GET", "/get-jobs");

	request.onreadystatechange = function() {
		if (request.readyState == 4) {
			var response = JSON.parse(request.responseText);

			document.getElementById('running').innerHTML = response['running'];
			document.getElementById('submitted').innerHTML = response['submitted'];
			document.getElementById('completed').innerHTML = response['completed'];
		}
	}
	request.send();
}
