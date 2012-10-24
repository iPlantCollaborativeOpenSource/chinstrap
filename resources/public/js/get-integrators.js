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
                + "{header:['App Name', 'Rating', 'Type', 'Created Date']}"
                + ")\">Export to CVS</button>"

            apps = "<table id='app-info'><thead>"
                + "<tr><th>Name</th>"
                + "<th>Rating</th>"
                + "<th>Type</th>"
                + "<th>Created</th></tr>"
                + "</thead><tbody>"
            for(var i = 0; i < resp['apps'].length; i++){
                date = new Date(resp['apps'][i]['integration_date'])
                day = date.getDate()
                month = date.getMonth() + 1
                year = date.getFullYear()

                apps += "<tr><td title='"
                    + resp['apps'][i]['description']
                    + "'><a href='"
                    + resp['apps'][i]['wikiurl']
                    + "'>" + resp['apps'][i]['name']
                    + "</a>"
                    + "</td><td>"
                    + resp['apps'][i]['average_rating'].toString().substring(0, 4) + "/5"
                    + "</td><td>"
                    + resp['apps'][i]['overall_job_type']
                    + "</td><td>"
                    + month + "/" + day +  "/" + year
                    + "</td></tr>";
            }

            apps += "</tbody></table><br>"
                + "<script>$('#app-info').tooltip({hide: 'false', position: {my: 'right bottom', at: 'left+40 top', collision: 'fit flip'}})</script>" 
            body += apps;
            $('#inner').html(body);
        })
    }
}
