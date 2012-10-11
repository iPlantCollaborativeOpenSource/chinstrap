function getIntegrator(who) {
    if (!who || who == "General Data"){
        generalData();
    }else{
        $.get('get-integrator-data/' + who, function(resp){
            body = "<h3><strong>"
                + resp['data'][0]['integrator_name']
                + "</h3></strong>"
                + "<hr>"
                + "<h4 class='left'>Email:"
                + "<span class='right'>"
                + resp['data'][0]['integrator_email']
                + "</span></h4>"
                + "<h4 class='left'>Integrator ID:"
                + "<span class='right'>"
                + resp['data'][0]['id']
                + "</span></h4>"

            apps = "<table id='app-info'><thead>"
                + "<tr><th>Name</th>"
                + "<th>Rating</th>"
                + "<th>Description</th>"
                + "<th>Type</th>"
                + "<th>Date Integrated</th></tr>"
                + "</thead><tbody>"

            for(var i = 0; i < resp['apps'].length; i++){
                apps += "<tr><td>"
                    + "<a href='"
                    + resp['apps'][i]['wikiurl'] 
                    + "'>" + resp['apps'][i]['name']
                    + "</a>"
                    + "</td><td>"
                    + resp['apps'][i]['average_rating']
                    + "</td><td>"
                    + resp['apps'][i]['description']
                    + "</td><td>"
                    + resp['apps'][i]['overall_job_type']
                    + "</td><td>"
                    + new Date(resp['apps'][i]['integration_date'])
                    + "</td></tr>";
            }

            apps += "</tbody></table><br>"
                + "<button class='left'"
                + "onclick=\"$('#app-info').table2CSV("
                + ")\">"
                + "Export to CVS</button>"
                + "<table>"
                + "</table>";

            body += apps;
            $('#inner').html(body);
        })
    }
}
