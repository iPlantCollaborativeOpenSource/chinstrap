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
                + "<button class='left'"
                + "onclick=\"$('#app-info').table2CSV("
                + ")\">Export to CVS</button>"

            apps = "<table id='app-info'><thead style='font-size: 10pt; text-align: center'>"
                + "<tr><th>Name</th>"
                + "<th>Rating</th>"
                + "<th>Description</th>"
                + "<th>Type</th>"
                + "<th>Integrated On</th></tr>"
                + "</thead><tbody>"
            for(var i = 0; i < resp['apps'].length; i++){
                date = new Date(resp['apps'][i]['integration_date'])
                day = date.getDate()
                month = date.getMonth() + 1
                year = date.getFullYear()

                apps += "<tr><td style='max-height: 50px; font-size: 10pt'>"
                    + "<a href='"
                    + resp['apps'][i]['wikiurl']
                    + "'>" + resp['apps'][i]['name']
                    + "</a>"
                    + "</td><td>"
                    + resp['apps'][i]['average_rating'].toString().substring(0, 4) + "/5"
                    + "</td><td style='max-width: 100px; max-height: 50px; font-size: 8pt;'>"
                    + resp['apps'][i]['description']
                    + "</td><td>"
                    + resp['apps'][i]['overall_job_type']
                    + "</td><td>"
                    + month + "/" + day +  "/" + year
                    + "</td></tr>";
            }

            apps += "</tbody></table><br>"

            body += apps;
            $('#inner').html(body);
        })
    }
}
