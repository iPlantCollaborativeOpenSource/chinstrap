function changePage(page) {
    request = $.ajax({
        url: "/" + page,
        contentType: "text/html",
        success: function(data){
            $('#content').html(data);
        }
    });
    if(page == "status"){
        getInfo();
    }else if(page == "apps"){
        $('.collapsibleContainer').collapsiblePanel();
        getApps();
    }else if(page == "components"){
        $('.collapsibleContainer').collapsiblePanel();
        getComponents();
    }else if(page == "graph"){
    }
};
