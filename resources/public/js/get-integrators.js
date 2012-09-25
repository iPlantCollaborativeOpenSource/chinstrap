function getIntegrator(who) {
    if (!who || who == "General Data"){
        generalData();
    }else{
        $.get('get-integrator-data/' + who, function(resp){
            body =
                "<h3><strong>" + resp['integrator_name'] + "</h3></strong>"
                + "<hr>"
                + "<h4 class='left'>Email:"
                + "<span class='right'>"
                + resp['integrator_email'] + "</span></h4>"
                + "<h4 class='left'>Integrator ID:"
                + "<span class='right'>" + resp['id'] + "</span></h4>"
            $('#inner').html(body)
        })
    }
}
