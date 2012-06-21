(ns chinstrap.views.pages
  (:require [chinstrap.views.common :as template]
            [monger.collection :as mc])
  (:use [noir.core :only [defpage]]
        [chinstrap.db]
        [clojure.data.json :only (json-str)]
        [hiccup.element]))

(defpage "/" []
  (template/start
    [:br][:br][:br][:br][:br][:br]
    [:br][:br][:br][:br][:br][:br]
    [:h1 (link-to "/status" "The Chinstrap has you.")]
    [:br][:br][:br][:br][:br][:br]
    (image "/img/logo.png")))

(defpage "/status" []
  (template/page
    [:br][:br][:br][:br][:br][:br]
    (image "/img/logo.png")
    [:br][:br]
    [:div#test]
;    (javascript-tag "window.setInterval(getJobs,1000);")
    [:h1 "Running Jobs: " [:span#running]]
    [:h1 "Submitted Jobs: " [:span#submitted]]
    [:h1 "Completed Jobs: " [:span#completed]]))

(def running (mc/count "jobs" {:state.status "Running"}))
(def submitted (mc/count "jobs" {:state.status "Submitted"}))
(def completed (mc/count "jobs" {:state.status "Completed"}))

(defpage "/get-jobs"
  []
  (json-str {:running running, :submitted submitted, :completed completed}))
