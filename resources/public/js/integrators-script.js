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
})

function generalData() {
    $.get('get-integrator-data/', function(resp){
        body =
            "<h3><strong>General Integrator Info</strong></h3><hr>"
            + "<ul>"
                + "<li>There are a total of "
                + "<strong>" + resp['total'] + "</strong>"
                + " apps on the DE.</li>"
                + "<li>On average, an integrator creates "
                + "<strong>" + resp['average'].toPrecision(3) + "</strong>"
                + " apps.</li>"
            +"</ul>"
        $('#inner').html(body)
    })
}

function integratorData() {
$("#inner").html($("#integrator").val());

}
generalData();
