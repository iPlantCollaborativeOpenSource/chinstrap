$(document).ready(function(){

    var picker = {showOn:'both',
                  hideIfNoPrevNext: true,
                  maxDate: '+0d',
                  buttonText:'Pick a Date'};

    $('#date').datepicker(picker);
    $('#date').datepicker('setDate', 'today');
    getInfo();

    var i = 0;
    Mousetrap.bind(['left', 'down', 'j', 'h'], function() {
        i--;
        $('#date').datepicker('setDate', i);
        getInfo();
    });

    Mousetrap.bind(['right', 'up', 'k', 'l'], function() {
        i++;
        if(i < 0){
            $('#date').datepicker('setDate', i);
            getInfo();
        } else {
            $('#date').datepicker('setDate', 'today');
            i--;
        }
    });

    Mousetrap.bind('r a m o n e s', function() {
            $('#inner').html("Hey Ho, Lets Go");
    });

    Mousetrap.bind(['t'], function() {
        i = -1;
        $('#date').datepicker('setDate', 'today');
        getInfo();
    });
});
