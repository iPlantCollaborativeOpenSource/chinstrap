function getInfo() {

	var request = new XMLHttpRequest();
	request.open("GET", "/get-info/" +
        $.datepicker.formatDate('@', $('#date').datepicker('getDate')));

	request.onreadystatechange = function() {
		if (request.readyState == 4) {
			var response = JSON.parse(request.responseText);
            var tools = "";
            var date = $('#date').val();
            console.log(request.responseText);

            if(response['tools'] == "") {
                $('#caption').html("");
                tools = "No tools on " + date + ".";
            } else {
                tools = "<table id='app-info'><thead><tr><th>Name</th><th>Count</th></tr></thead><tbody>";
                for(var i = 0; i < response['tools'].length; i++){
                    tools += "<tr><td>" + response['tools'][i]['name']+"</td>";
                    tools += "<td>" + response['tools'][i]['count']+"</td></tr>";
                }
                tools += "</tbody></table>";
                tools += "<br><button class='left' onclick=\"$('#app-info').table2CSV({header:['App Name','Count']})\">";
                tools += "Export to CVS</button>";
            }
			$('#inner').html(tools);
		}
	}
	request.send();
};
