(function($) {
    $.fn.extend({
        collapsiblePanel: function() {
            // Call the ConfigureCollapsiblePanel function for the selected element
            return $(this).each(ConfigureCollapsiblePanel);
        }
    });

})(jQuery);

var i = 0;

function ConfigureCollapsiblePanel() {
    $(this).addClass("ui-widget");

    // Wrap the contents of the container within a new div.
    $(this).children().wrapAll("<div class='collapsibleContainerContent ui-widget-content'></div>");

    // Create a new div as the first item within the container.  Put the title of the panel in here.
    $("<div class='collapsibleContainerTitle ui-widget-header'><div>" + $(this).attr("title") + "<img class='triangle' src='/img/circle_arrow_right.png'></img></div></div>").prependTo($(this));

    // Assign a call to CollapsibleContainerTitleOnClick for the click event of the new title div.
    $(".collapsibleContainerTitle", this).click(CollapsibleContainerTitleOnClick);
}

function CollapsibleContainerTitleOnClick() {
    // The item clicked is the title div... get this parent (the overall container) and toggle the content within it.
    if ((i % 2) == 0) {
        $('.triangle').attr("src" , "/img/circle_arrow_down.png");
        $('.collapsibleContainerTitle').css('border-bottom-left-radius', '0px');
        $('.collapsibleContainerTitle').css('border-bottom-right-radius', '0px');
        $('.collapsibleContainerTitle div').css('border-bottom-left-radius', '0px');
        $('.collapsibleContainerTitle div').css('border-bottom-right-radius', '0px');
    } else {
        $('.triangle').attr("src" , "/img/circle_arrow_right.png");
        $('.collapsibleContainerTitle').css('border-bottom-left-radius', '4px');
        $('.collapsibleContainerTitle').css('border-bottom-right-radius', '4px');
        $('.collapsibleContainerTitle div').css('border-bottom-left-radius', '4px');
        $('.collapsibleContainerTitle div').css('border-bottom-right-radius', '4px');
    }
    $(".collapsibleContainerContent", $(this).parent()).slideToggle();
    i++;
}
