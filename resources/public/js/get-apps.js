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

            if(response['running'] === 0)
                $('#running-apps').html('There are currently no apps running.');
             else
                $('#running-apps').html(response['running-names']);

            if(response['submitted'] === 0)
                $('#submitted-apps').html('There are currently no submitted apps.');
             else
                $('#submitted-apps').html(response['submitted-names']);

            if(response['failed'] === 0)
                $('#failed-apps').html(response['failed-names']);
             else
                $('#failed-apps').html('There are currently no failed apps. Woohoo!');
		}
	}
	request.send();
};
