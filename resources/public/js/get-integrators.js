function getIntegrators(who) {
    $.get('get-integrator-data/' + who, function(resp){
        $('#inner').html(JSON.stringify(resp));
    });
}
