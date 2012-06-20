(ns chinstrap.views.common
    (:use [noir.core :only [defpartial]]
          [hiccup.page :only [include-css html5]]))

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
            [:title "Discovery Enviroment Job Statuses"]
            (include-css "/css/reset.css"
                         "/css/style.css")]
        [:body [:div#wrapper content]]))
