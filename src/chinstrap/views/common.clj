(ns chinstrap.views.common
  (:use [noir.core :only [defpartial]]
        [hiccup.page]
        [hiccup.element]))

(defpartial main-page [& content]
    (html5
      [:head
        [:title "Discovery Enviroment Status"]
        (include-css "/css/reset.css"
                     "http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/themes/cupertino/jquery-ui.css"
                     "/css/style.css")
        (include-js  "/js/get-info.js"
                     "https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"
                     "http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.9/jquery-ui.min.js"
                     "/js/collapsible-panel.js")]
      [:body
        (javascript-tag "$(document).ready(function(){
          $('.collapsibleContainer').collapsiblePanel();
          var picker={showOn:'both',
                      autoSize: true,
                      hideIfNoPrevNext: true,
                      buttonText:'Pick a Date'};
          $('#date').datepicker(picker);});")
[:div#wrapper content]]))

(defpartial apps-page [& content]
    (html5
      [:head
        [:title "Discovery Enviroment App Status"]
        (include-css "/css/reset.css"
                     "/css/style.css")
        (include-js  "/js/get-apps.js"
                     "https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"
                     "/js/collapsible-panel.js")]
      [:body
        (javascript-tag "$(document).ready(function(){
          $('.collapsibleContainer').collapsiblePanel();});")
        [:div#wrapper content]]))

(defpartial components-page [& content]
    (html5
      [:head
        [:title "Components Without Transformations"]
        (include-css "/css/reset.css"
                     "/css/style.css")
        (include-js  "/js/get-components.js"
                     "https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"
                     "/js/collapsible-panel.js")]
      [:body
        (javascript-tag "$(document).ready(function(){
          $('.collapsibleContainer').collapsiblePanel();});")
        [:div#wrapper content]]))
