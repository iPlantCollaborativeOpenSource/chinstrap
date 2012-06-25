(ns chinstrap.views.pages
  (:require [chinstrap.views.common :as template]
            [noir.response :as nr]
            [monger.collection :as mc])
  (:use [noir.core :only [defpage]]
        [chinstrap.db]
        [hiccup.element]))

;Page listing the count of different states of DE Jobs.
(defpage "/" []
  (template/DE-Page
    (image "/img/logo.png")
    (javascript-tag "window.setInterval(getJobs,36000);")
    [:p "Discovery Environment Job Statuses"]
    [:br]
    [:div#inner
      [:h2.text "Running Apps: " [:span#running]]
      [:h2.text "Submitted Apps: " [:span#submitted]]
      [:h2.text "Completed Apps: " [:span#completed]]]))

;AJAX call from the Javascript file 'get-running-de-jobs.js'.
(defpage "/get-jobs" []
  (nr/json {:running (mc/count "jobs" {:state.status "Running"}),
            :submitted (mc/count "jobs" {:state.status "Submitted"}),
            :completed (mc/count "jobs" {:state.status "Completed"})}))


;Page listing count and info of Components with no transformation activities.
(defpage "/components" []
  (template/Components-Page
    (image "/img/logo.png")
    [:p "Components Found Without Transformation Activities"]
    [:br]
    [:div#inner]))
