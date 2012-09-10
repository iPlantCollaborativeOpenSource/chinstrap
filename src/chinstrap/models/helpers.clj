(ns chinstrap.models.helpers
  (:require [monger.collection :as mc]))

(defn format-data-for-graph
  "This function takes in dates and their counts and parses them into a JSON
  object for easy graph data parsing in javascript."
  [data]
  (map
    #(hash-map
      :date (key %)
      :count (val %))
    data))

(defn get-app-names
  "This function returns the names of apps with the passed status.
  E.G. (get-apps-names \"Completed\")"
  [status]
    (apply str
      (map #(str (:name (:state %)) "<br>")
        (mc/find-maps "jobs" {:state.status (str status)} [:state.name]))))

(defn fetch-submission-date-by-status
  "Helper fuction for graph data calls to the mongoDB, returns a map of the dates in milliseconds of apps with the passed status."
  [status]
  (map #(:submission_date (:state %))
    (mc/find-maps "jobs"
      {:state.status {"$in" [status]}}
      [:state.submission_date])))
