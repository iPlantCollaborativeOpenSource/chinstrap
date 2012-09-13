$(document).ready(function() {
    $('.integrator').hover(
    function() {
        $(this).children('.name').text(
        $(this).children('.email').attr('value'));
        $(this).children('.name').animate({ color: '#555' }, 100)
    },
    function() {
        $(this).children('.name').text(
        $(this).children('.name').attr('value'))
        $(this).children('.name').animate({ color: '#6D929B' }, 100)
    })

    $('.integrator').click(function() {
        getIntegrators($(this).children('.id').attr('value'))
    })

    $.get('get-integrator-data/', function(resp){
        body =
              "<h3><strong>General Integrator Info</strong></h3><hr>"
            + "<ul>"
                + "<li>On average Integrators create "
                + "<strong>" + resp['average'] + "</strong>"
                + " apps</li>"
            +"</ul>"
        $('#inner').html(body)
    })

})
