(ns chinstrap.views.common
    (:use [noir.core :only [defpartial]]
          [hiccup.page]))

(defpartial page [& content]
    (html5
        [:head
            [:title "Discovery Enviroment Jobs Status"]
            (include-css "/css/reset.css"
                         "/css/style.css")
            (include-js  "/js/ajax-jobs.js")]
        [:body {:onload "getJobs()"} [:div#wrapper content]]))
