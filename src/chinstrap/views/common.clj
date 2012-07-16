(ns chinstrap.views.common
  (:use [noir.core :only [defpartial]]
        [hiccup.page]
        [hiccup.element]))

(defpartial global [title]
  [:head
    [:title (str "Chinstrap - " title)]
    (include-css
      "/css/reset.css"
      "/css/style.css")
    (include-js
      "https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"
      "http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.9/jquery-ui.min.js"
      "/js/mousetrap.min.js")])

(defpartial wrapper [& content]
  [:div#navbar
    [:span.vbr]
    [:li.nav (link-to "/main" "Status")]
    [:div.vbr]
    [:li.nav (link-to "/apps" "App Info")]
    [:div.vbr]
    [:li.nav (link-to "/components" "Component Info")]
    [:div.vbr]
    [:li.nav (link-to "/graph" "Graphs")]
    [:div.vbr]]
  [:div#wrapper content])

(defpartial footer []
  [:div#footer])

(defpartial main-page [& content]
    (html5
      [:head
        (global "Status")
        (include-css "http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/themes/cupertino/jquery-ui.css")
        (include-js "/js/get-info.js"
                    "/js/main.js")]
      [:body
        (wrapper content)
        (footer)]))

(defpartial apps-page [& content]
    (html5
      [:head
        (global "Apps")
        (include-js "/js/get-apps.js"
                    "/js/collapsible-panel.js")]
      [:body
        (javascript-tag "$(document).ready(function(){
          $('.collapsibleContainer').collapsiblePanel();});")
        (wrapper content)
        (footer)]))

(defpartial components-page [& content]
    (html5
      [:head
        (global "Components")
        (include-js "/js/get-components.js"
                    "/js/collapsible-panel.js")]
      [:body
        (javascript-tag "$(document).ready(function(){
          $('.collapsibleContainer').collapsiblePanel();});")
        (wrapper content)
        (footer)]))

(defpartial graph-page [& content]
    (html5
      [:head
        (global "Graph Test")
        (include-js "/js/amcharts.js")
        (include-js "/js/graph.js")]
      [:body
        (wrapper content)
        (footer)]))
