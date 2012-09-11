function getInfo() {
    $.get("/get-info/" + $.datepicker.formatDate('@', $('#date').datepicker('getDate')), function(resp) {

        if(resp['tools'] == "")
            $('#inner').html( "No tools on " + $('#date').val() + ".")
        else {
            tools = "Tools on " + $('#date').val() + ":<hr>"

            tools +=
                "<table id='app-info'><thead>" +
                "<tr><th>Name</th><th>Count</th></tr>" +
                "</thead><tbody>"

            for(var i = 0; i < resp['tools'].length; i++){
                tools +=
                    "<tr><td>" +
                    resp['tools'][i]['name'] +
                    "</td><td>" +
                    resp['tools'][i]['count'] +
                    "</td></tr>"
            }

            tools +=
                "</tbody></table><br>" +
                "<button class='left'" +
                "onclick=\"$('#app-info').table2CSV(" +
                "{header:['App Name','Count']})\">" +
                "Export to CVS</button>"

            $('#inner').html(tools)
        }
    })
}
