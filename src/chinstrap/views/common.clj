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
      "http://code.jquery.com/jquery-1.8.0.min.js"
      "http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.9/jquery-ui.min.js")])

(defpartial graph-nav []
  [:div#graph-nav
    [:span.nav]
    [:a#day.nav {:href "/graph/day"} [:li.nav "Day"]]
    [:span.nav]
    [:a#month.nav {:href "/graph/month"} [:li.nav "Month"]]
    [:span.nav]])

(defpartial navbar []
  [:div#navbar
    [:span.nav]
    [:a#info.nav {:href "/info"} [:li.nav "Info"]]
    [:span.nav]
    [:a#apps.nav {:href "/apps"} [:li.nav "Apps"]]
    [:span.nav]
    [:a#components.nav {:href "/components"} [:li.nav "Components"]]
    [:span.nav]
    [:a#integrators.nav {:href "/integrators"} [:li.nav "Integrators"]]
    [:span.nav]
    [:a#graphs.nav {:href "/graph"} [:li.nav "Graphs"]]
    [:span.nav]])

(defpartial wrapper [& content]
  [:div#wrapper
    (image {:id "logo" :alt "iPlant Logo"} "/img/logo.png")
    [:br]
    content]
  [:br])

(defpartial footer []
  [:div#footer])

(defpartial page [& content]
  (navbar)
  (wrapper content)
  (footer))

(defpartial info-page [& content]
  (html5
    [:head
      (global "Info")
      (include-css "http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/themes/cupertino/jquery-ui.css")
      (include-js "/js/get-info.js"
                  "/js/info-script.js"
                  "/js/csv-parser.js"
                  "/js/lib/mousetrap.min.js")]
    [:body
      (javascript-tag "$(document).ready(function(){
        $('#info').addClass('active');})")
      (page content)]))

(defpartial apps-page [& content]
  (html5
    [:head
      (global "Apps")
      (include-js "/js/get-apps.js"
                  "/js/collapsible-panel.js")]
    [:body
      (javascript-tag "$(document).ready(function(){
        $('#apps').addClass('active');
        $('.collapsibleContainer').collapsiblePanel();});")
      (page content)]))

(defpartial integrators-page [& content]
  (html5
    [:head
      (global "Integrators")
      (include-css "/css/chosen.css")
      (include-js "/js/lib/chosen.jquery.min.js"
                  "/js/lib/jquery.color.js"
                  "/js/get-integrators.js"
                  "/js/lib/mousetrap.min.js"
                  "/js/integrators-script.js"
                  "/js/csv-parser.js"
                  "/js/collapsible-panel.js")]
    [:body
      (javascript-tag "$(document).ready(function(){
        $('#integrators').addClass('active')
        $('.chzn-select').chosen({max_selected_options: 1});
        $('.collapsibleContainer').collapsiblePanel()})")
      (page content)]))

(defpartial components-page [& content]
  (html5
    [:head
      (global "Components")
      (include-js "/js/get-components.js"
                  "/js/csv-parser.js"
                  "/js/collapsible-panel.js")]
    [:body
      (javascript-tag "$(document).ready(function(){
        $('#components').addClass('active');
        $('.collapsibleContainer').collapsiblePanel();});")
      (page content)]))

(defpartial graph-page [& content]
  (html5
    [:head
      (global "Graph - by Day")
      (include-js "/js/lib/spin.min.js"
                  "/js/spinner.js"
                  "/js/lib/underscore-min.js")
      (javascript-tag "$(document).ready(function(){
                       $('#graphs').addClass('active')
                       createChart()})")
    [:body
      (page
        [:h3 "DE Apps "
          [:select#type.selector {:onchange "reloadChart()"}
            [:option "Completed"]
            [:option "Failed"]]
        " Over Time"]
        [:br]
        (graph-nav)
        [:br]
        [:div#chart] content [:div#loader]
        [:h5.right "Data Starting from: " [:span#firstDate]])]]))

(defpartial day-page []
  (include-js "/js/lib/amcharts.js")
  (include-js "/js/day-graph.js")
  (javascript-tag "$(document).ready(function(){
                   $('#day').addClass('active')})"))

(defpartial month-page []
  (include-js "/js/lib/amcharts.js")
  (include-js "/js/month-graph.js")
  (javascript-tag "$(document).ready(function(){
                   $('#month').addClass('active')})"))

(defpartial raw-page [& content]
  (html5
    [:head
      (global "Raw Data")]
    [:body
      (page content)]))
