document.addEventListener("DOMContentLoaded",function(){getComponents();window.setInterval(getComponents,36000);});

function getComponents() {

	var request = new XMLHttpRequest();
	request.open("GET", "/get-components");
	request.onreadystatechange = function() {
		if (request.readyState == 4) {
			var response = JSON.parse(request.responseText);

			$('#all').html(response['all']);
			$('#without').html(response['without']);
			$('#with').html(response['with']);
		}
	}
	request.send();
};
