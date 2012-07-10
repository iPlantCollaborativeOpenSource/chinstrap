(ns chinstrap.models.ajax
  (:require [noir.response :as nr]
            [chinstrap.queries :as cq]
            [monger.collection :as mc])
  (:use [noir.core]))

(defpage "/get-info/:date" {:keys [date]}
  (nr/json {:analysis_ids
    (map #(str (:analysis_id (:state %)))
      (mc/find-maps "jobs" {:state.submission_date
        {"$gte" (read-string date) "$lt" (+ 86400000 (read-string date))}
        :state.analysis_id {"$exists" true}}
          [:state.analysis_id]))}))

;AJAX call from the Javascript file 'get-apps.js'.
(defpage "/get-apps" []
  (nr/json {:running (mc/count "jobs" {:state.status "Running"}),
            :submitted (mc/count "jobs" {:state.status "Submitted"}),
            :completed (mc/count "jobs" {:state.status "Completed"})}))

;AJAX call from the Javascript file 'get-components.js'.
(defpage "/get-components" []
  (nr/json {:all (cq/all-count)
            :without (cq/without-count)
            :with (cq/with-count)}))
