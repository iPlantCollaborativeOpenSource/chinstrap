(ns chinstrap.views.common
  (:use [noir.core :only [defpartial]]
        [hiccup.page]
        [hiccup.element]))
(defpartial wrapper [& content]
  [:div#navbar
    [:span.vbr]
    [:li.nav (link-to "/main" "Status")][:div.vbr]
    [:li.nav (link-to "/apps" "App Info")][:div.vbr]
    [:li.nav (link-to "/components" "Component Info")][:div.vbr]]
  [:div#wrapper content])

(defpartial main-page [& content]
    (html5
      [:head
        [:title "Discovery Enviroment Status"]
        (include-css "/css/reset.css"
                     "http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/themes/cupertino/jquery-ui.css"
                     "/css/style.css")
        (include-js  "/js/get-info.js"
                     "https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"
                     "http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.9/jquery-ui.min.js")]
      [:body
        (javascript-tag "$(document).ready(function(){
          var picker={showOn:'both',
                      hideIfNoPrevNext: true,
                      maxDate: '+0d',
                      buttonText:'Pick a Date'};
          $('#date').datepicker(picker);});")
(wrapper content)]))

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
        (wrapper content)]))

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
        (wrapper content)]))
