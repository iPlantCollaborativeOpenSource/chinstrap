$(document).ready(function(){
    var i = 0;
    var picker = {showOn:'both',
                  hideIfNoPrevNext: true,
                  maxDate: '+0d',
                  buttonText:'Pick a Date',
                  onClose: function(dateText, inst) {
                               changeDay(inst);
                           }
                 };

    $('#date').datepicker(picker);
    $('#date').datepicker('setDate', 'today');
    getInfo();

    var now = $('#date').datepicker('getDate');
    now = $.datepicker.formatDate('mm/dd/yy', now);
    console.log(now);

    function changeDay(inst){
        console.log(inst);
    }

    Mousetrap.bind(['down', 'j'], function() {
        i--;
        $('#date').datepicker('setDate', i);
        getInfo();
    });

    Mousetrap.bind(['up', 'k'], function() {
        i++;
        if(i < 0){
            $('#date').datepicker('setDate', i);
            getInfo();
        } else {
            $('#date').datepicker('setDate', 'today');
            i--;
            getInfo();
        }
    });

    Mousetrap.bind(['t'], function() {
        i = 0;
        $('#date').datepicker('setDate', 'today');
        getInfo();
    });

    Mousetrap.bind('r a m o n e s', function() {
            $('#inner').html("Hey Ho, Lets Go!");
    });

    Mousetrap.bind('c h i n s t r a p', function() {
            $('#inner').html("The Chinstrap has you.");
    });
});
