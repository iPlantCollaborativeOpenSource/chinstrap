(ns chinstrap.views.common
    (:use [noir.core :only [defpartial]]
          [hiccup.page]))

(defpartial start [& content]
    (html5
        [:head
            [:title "chinstrap"]
            (include-css "/css/reset.css"
                         "/css/splash.css"
                         "http://fonts.googleapis.com/css?family=Gloria+Hallelujah")]
        [:body content]))

(defpartial page [& content]
    (html5
        [:head
            [:title "Discovery Enviroment Jobs Status"]
            (include-css "/css/reset.css"
                         "/css/style.css")
            (include-js  "/js/ajax-jobs.js")]
        [:body {:onload "getJobs()"} [:div#wrapper content]]))
