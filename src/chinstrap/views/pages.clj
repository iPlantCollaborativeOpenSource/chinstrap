(ns chinstrap.views.pages
  (:require [chinstrap.views.common :as template]
            [noir.response :as nr]
            [monger.collection :as mc])
  (:use [noir.core :only [defpage]]
        [chinstrap.db]
        [clojure.data.json :only (json-str)]
        [hiccup.element]))

(defpage "/" []
  (template/DE-Page
    (image "/img/logo.png")
    (javascript-tag "window.setInterval(getJobs,36000);")
    [:h1 "Discovery Environment:"]
    [:div#inner
      [:h2.text "Running Apps: " [:span#running]]
      [:h2.text "Submitted Apps: " [:span#submitted]]
      [:h2.text "Completed Apps: " [:span#completed]]]))

(defpage "/get-jobs" []
  (nr/json {:running (mc/count "jobs" {:state.status "Running"}),
             :submitted (mc/count "jobs" {:state.status "Submitted"}),
             :completed (mc/count "jobs" {:state.status "Completed"})}))

(defpage "/components" []
  (template/Components-Page
    (image "/img/logo.png")
    [:h2 "Components Found Without Transformation Activities:"]
    [:div#inner]))
