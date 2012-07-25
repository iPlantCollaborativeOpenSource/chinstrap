(ns chinstrap.views.common
  (:use [noir.core :only [defpartial]]
        [hiccup.page]
        [hiccup.element]))

(defpartial navbar []
  [:div#navbar
    [:span.vbr]
    [:li.nav (link-to {:onClick "changePage('status')"} "#main" "Status")]
    [:div.vbr]
    [:li.nav (link-to {:onClick "changePage('apps')"} "#apps" "App Info")]
    [:div.vbr]
    [:li.nav (link-to {:onClick "changePage('components')"} "#components" "Component Info")]
    [:div.vbr]
    [:li.nav (link-to {:onClick "changePage('graph')" } "#graph" "Graphs")]
    [:div.vbr]])

(defpartial wrapper [& content]
  [:div#wrapper
    (image {:onClick "changePage('main')" :id "logo" :alt "iPlant Logo"} "img/logo.png")
    [:br]
    [:div#content content]
    [:br]])
(defpartial footer []
  [:div#footer])

(defpartial main [& content]
    (html5
      [:head
        [:title "Chinstrap"]
        (include-css "/css/reset.css"
                     "http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/themes/cupertino/jquery-ui.css"
                     "/css/style.css")
        (include-js "https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"
                    "http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.9/jquery-ui.min.js"
                    "/js/mousetrap.min.js"
                    "/js/get-info.js"
                    "/js/get-apps.js"
                    "/js/get-components.js"
                    "/js/collapsible-panel.js"
                    "/js/onload.js")]
      [:body
        (javascript-tag "changePage('main')")
        (navbar)
        (wrapper content)
        (footer)]))

(defpartial html [& content]
    (html5 content))

(defpartial status-page []
    (html5
        (include-js "/js/status.js")))

(defpartial apps-page []
    (html5))

(defpartial components-page []
    (html5))

(defpartial graph-page []
    (html5
        (include-js "/js/amcharts.js"
                    "/js/underscore-min.js"
                    "/js/graph.js")))
