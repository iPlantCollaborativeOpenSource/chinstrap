(ns chinstrap.models.model
  (:require [noir.response :as nr]
            [chinstrap.sqlqueries :as cq]
            [monger.collection :as mc])
  (:use [noir.core]))

(defn get-app-names
  "This function returns the application names currently operating at the passed state.
  E.G. (get-apps-names \"Completed\")"
  [state]
    (apply str
      (map #(str (:name (:state %)) "<br>")
        (mc/find-maps "jobs" {:state.status (str state)} [:state.name]))))

;AJAX call from the Javascript file 'resources/public/js/get-info.js'.
(defpage "/get-info/:date" {:keys [date]}
  (nr/json {:tools
    (cq/apps-by-ids
      (map #(str (:analysis_id (:state %)))
        (mc/find-maps "jobs" {:state.submission_date
          {"$gte" (read-string date) "$lt" (+ 86400000 (read-string date))}
            :state.analysis_id {"$exists" true}}[:state.analysis_id])))}))

;AJAX call from the Javascript file 'resources/public/js/get-apps.js'.
(defpage "/get-apps" []
  (nr/json {:running (mc/count "jobs" {:state.status "Running"}),
            :submitted (mc/count "jobs" {:state.status "Submitted"}),
            :completed (mc/count "jobs" {:state.status "Completed"}),
            :running-names (str (get-app-names "Running")),
            :submitted-names (str (get-app-names "Submitted"))}))

;AJAX call from the Javascript file 'resources/public/js/get-components.js'.
(defpage "/get-components" []
  (nr/json {:all (cq/all-count)
            :without (cq/without-count)
            :with (cq/with-count)}))
