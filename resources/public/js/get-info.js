function getInfo() {

	var request = new XMLHttpRequest();
    var date = $.datepicker.formatDate('@', $('#date').datepicker('getDate'));
	request.open("GET", "/get-info/" + date);

	request.onreadystatechange = function() {
		if (request.readyState == 4) {
			var response = JSON.parse(request.responseText);
            var tools = "";
            var date = $('#date').val();

            if(response['tools'] == ""){
                tools = "No tools on " + date + ".";
            }else{
                $('#caption').html("Tools on " + date + ":");
                tools = "<br><table><tr><th>Name</th><th>Count</th></tr>";
                for(var i = 0; i < response['tools'].length; i++){
                    tools += "<tr><td>" + response['tools'][i]['name'] + "</td></tr>";
                }
                tools += "</table>";
            }

			$('#inner').html(tools);
		}
	}
	request.send();
};
