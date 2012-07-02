(ns chinstrap.views.pages
  (:require [chinstrap.views.common :as template]
            [noir.response :as nr]
            [chinstrap.queries :as cq]
            [monger.collection :as mc])
  (:use [noir.core]
        [chinstrap.db]
        [clojure.data.json :only (json-str)]
        [hiccup.element]))

(defpage "/" []
  (render "/jobs"))

;Page listing the count of different states of DE Jobs.
(defpage "/jobs" []
  (template/DE-Page
    (image "/img/logo.png")
    (javascript-tag "window.setInterval(getJobs,36000);")
    [:h3 "Discovery Environment Job Statuses"]
    [:br]
    [:div#inner
      [:h3.text "Running Apps: " [:span#running]]
      [:h3.text "Submitted Apps: " [:span#submitted]]
      [:h3.text "Completed Apps: " [:span#completed]]]
    [:br]
    (link-to "/components" "Components")))

;AJAX call from the Javascript file 'get-de-jobs.js'.
(defpage "/get-jobs" []
  (nr/json {:running (mc/count "jobs" {:state.status "Running"}),
            :submitted (mc/count "jobs" {:state.status "Submitted"}),
            :completed (mc/count "jobs" {:state.status "Completed"})}))


;Page listing count and info of Components with no transformation activities.
(defpage "/components" []
  (template/Components-Page
    (image "/img/logo.png")
    [:h3 "DE Tools and Associated App Info"]
    [:br]
    [:div#inner
      [:h3.text "With associated DE apps: " [:span#public]]
      [:h3.text "Without associated DE apps: " [:span#private]]
      [:h3.text "Total Tools: " [:span#all]]]
      [:br]
      (link-to "/jobs" "Discovery Environment Job Status")
      [:br]
      [:br]
      [:table
        [:caption [:h4 "Unused Component Details:"]]
        [:tr [:th "Name"]
             [:th "Version"]]
        (for [list
               (map identity (cq/unused-list))]
             [:tr
               [:td (str (:name list))]
               [:td (str (or (:version list) "No Version"))]])]
      [:br]))

;AJAX call from the Javascript file 'get-components.js'.
(defpage "/get-components" []
  (nr/json {:all (cq/all-count)
            :private (cq/unused-count)
            :public (cq/used-count)
           }))
