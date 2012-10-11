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
                + "</span></h4><br>"

            apps = "<table id='app-info' style='font-size:8px'><thead style='font-size: 10pt'>"
                + "<tr><th>Name</th>"
                + "<th>Rating</th>"
                + "<th>Description</th>"
                + "<th>Type</th>"
                + "<th>Date Integrated</th></tr>"
                + "</thead><tbody>"

            for(var i = 0; i < resp['apps'].length; i++){
                apps += "<tr><td style='font-size: 10pt'>"
                    + "<a href='"
                    + resp['apps'][i]['wikiurl']
                    + "'>" + resp['apps'][i]['name']
                    + "</a>"
                    + "</td><td style='font-size: 10pt; padding: 0px; text-align:center; margin: 0px auto'>"
                    + resp['apps'][i]['average_rating']
                    + "</td><td style='max-width: 100px; overflow: auto; font-size: 8pt;'>"
                    + resp['apps'][i]['description']
                    + "</td><td>"
                    + resp['apps'][i]['overall_job_type']
                    + "</td><td>"
                    + new Date(resp['apps'][i]['integration_date']).toLocaleDateString()
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
