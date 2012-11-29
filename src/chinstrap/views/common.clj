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
      "//code.jquery.com/jquery-1.8.2.min.js"
      "//code.jquery.com/ui/1.9.0/jquery-ui.min.js")])

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
      (include-css "http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.2/themes/cupertino/jquery-ui.css")
      (include-js "/js/get-info.js"
                  "/js/info-script.js"
                  "/js/lib/mousetrap.min.js")]
    [:body
      (javascript-tag "$(document).ready(function(){
        $('#info').addClass('active');})")
      (page content)]
    (include-js "/js/lib/csv-parser.js")))

(defpartial apps-page [& content]
  (html5
    [:head
      (global "Apps")
      (include-js "/js/get-apps.js"
                  "/js/lib/collapsible-panel.js")]
    [:body
      (javascript-tag "$(document).ready(function(){
        $('#apps').addClass('active');
        $('.collapsibleContainer').collapsiblePanel();});")
      (page content)]))

(defpartial integrators-page [& content]
  (html5
    [:head
      (global "Integrators")
      (include-css "/css/chosen.css"
                   "/css/pagination.css")
      (include-js "/js/lib/chosen.jquery.min.js"
                  "/js/get-integrators.js"
                  "/js/lib/mousetrap.min.js"
                  "/js/integrators-script.js"
                  "/js/lib/collapsible-panel.js")]
    [:body
      (javascript-tag "$(document).ready(function(){
        $('#integrators').addClass('active');
        $('.chzn-select').chosen({max_selected_options: 1});
        $('.collapsibleContainer').collapsiblePanel();})")
      (page content)]
    (include-js "/js/lib/jquery.dataTables.min.js"
                "/js/lib/csv-parser.js")))

(defpartial components-page [& content]
  (html5
    [:head
      (global "Components")
      (include-js "/js/get-components.js"
                  "/js/lib/collapsible-panel.js")]
    [:body
      (javascript-tag "$(document).ready(function(){
        $('#components').addClass('active');
        $('.collapsibleContainer').collapsiblePanel();});")
      (page content)]
    (include-js "/js/lib/csv-parser.js")))

(defpartial graph-page [& content]
  (html5
    [:head
      (global "Graph - by Day")
      (include-js "/js/lib/spin.min.js"
                  "/js/spinner.js")
      (javascript-tag "$(document).ready(function(){
                       $('#graphs').addClass('active');});")]
    [:body
      {:onload "createChart()"}
      (page
        [:h3
          [:select#type.selector {:onchange "reloadChart()"}
            [:option  {:data ""} "All"]
            [:option {:data "Completed"} "Completed"]
            [:option {:data "Failed"} "Failed"]]
        " DE Apps Over Time"]
        [:br] (graph-nav) [:br]
        [:div#chart]
        [:div#loader]
        content
        [:h5.right "Data Starting from: " [:span#firstDate]])
      (include-js "/js/lib/amcharts.js"
                  "/js/lib/underscore-min.js")]))

(defpartial day-page []
  (include-js "/js/day-graph.js")
  (javascript-tag "$('#day').addClass('active')"))

(defpartial month-page []
  (include-js "/js/month-graph.js")
  (javascript-tag "$('#month').addClass('active')"))

(defpartial raw-page [& content]
  (html5
    [:head
      (global "Raw Data")]
    [:body
      (page content)]))
