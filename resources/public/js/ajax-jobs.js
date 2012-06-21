function getJobs() {

	var request = new XMLHttpRequest();
	request.open("GET", "ajax/get-jobs.clj");

	request.onreadystatechange = function() {
		if (request.readyState == 4) {
			var response = request.responseText;
			console.log(response);

			document.getElementById('running').innerHTML = response[running];
			document.getElementById('submitted').innerHTML = response[submitted];
			document.getElementById('completed').innerHTML = response[completed];
		}
	}
	request.send();
}