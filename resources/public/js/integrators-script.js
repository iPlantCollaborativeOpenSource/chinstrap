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
