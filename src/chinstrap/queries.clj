(ns chinstrap.queries
  (:use [kameleon.entities]
        [korma.core]
        [chinstrap.db])
  (:require [clojure.tools.logging :as log]))

(defn all-count
  "Returns a count of all the queried deployed components in the DB."
  []
  (second (ffirst
    (select deployed_components
      (aggregate (count :*) :all)))))

(defn with-count
  "Returns a count of the queried deployed components in the DB with associated
   transformation activities."
  []
  (second (ffirst
    (select deployed_components
      (aggregate "COUNT(DISTINCT \"deployed_components\".*)" :with)
      (join "inner" :template
        (= :deployed_components.id :template.component_id))))))

(defn without-count
  "Returns a count of all the queried deployed components in the DB without
   associated transformation activities."  []
  (second (ffirst
    (select deployed_components
      (aggregate (count :*) :without)
      (join "left outer" :template
        (= :template.component_id :deployed_components.id))
      (where {:template.component_id nil})))))

(defn without-list
  "Returns a list of all the queried deployed components in the DB without
   associated transformation activities."  []
  (select deployed_components
    (fields :name :version)
    (join "left outer" :template
      (= :template.component_id :deployed_components.id))
    (where {:template.component_id nil})))
