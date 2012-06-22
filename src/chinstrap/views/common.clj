(ns chinstrap.views.common
  (:use [noir.core :only [defpartial]]
        [hiccup.page]))

(defpartial page [& content]
    (html5
      [:head
        [:title "Discovery Enviroment Jobs Status"]
        (include-css "/css/reset.css"
                      "/css/style.css"
                      "http://fonts.googleapis.com/css?family=Arvo:400"
                      "http://fonts.googleapis.com/css?family=Ubuntu")
        (include-js  "/js/ajax-jobs.js")]
      [:body [:div#wrapper content]]))
