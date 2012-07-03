(ns chinstrap.views.common
  (:use [noir.core :only [defpartial]]
        [hiccup.page]
        [hiccup.element]))

(defpartial jobs-page [& content]
    (html5
      [:head
        [:title "Discovery Enviroment Jobs Status"]
        (include-css "/css/reset.css"
                     "/css/style.css"
                     "http://fonts.googleapis.com/css?family=Ubuntu")
        (include-js  "/js/get-de-jobs.js"
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
                     "/css/style.css"
                     "http://fonts.googleapis.com/css?family=Ubuntu")
        (include-js  "/js/get-components.js"
                     "https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"
                     "/js/collapsible-panel.js")]
      [:body
        (javascript-tag "$(document).ready(function(){
          $('.collapsibleContainer').collapsiblePanel();});")
        [:div#wrapper content]]))

(defpartial leaderboard-page [& content]
    (html5
      [:head
        [:title "Discovery Enviroment App Leaderboard"]
        (include-css "/css/reset.css"
                     "/css/style.css"
                     "http://fonts.googleapis.com/css?family=Ubuntu")
        (include-js  "https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"
                     "/js/collapsible-panel.js")]
      [:body
        (javascript-tag "$(document).ready(function(){
          $('.collapsibleContainer').collapsiblePanel();});")
        [:div#wrapper content]]))
