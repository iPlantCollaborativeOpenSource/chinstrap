(ns chinstrap.queries
  (:use [kameleon.entities]
        [korma.core]
        [chinstrap.db])
  (:require [clojure.tools.logging :as log]))

(defn all-count
  "Returns a count of all the queried deployed components in the DB."
  []
  (log/warn(select deployed_components (aggregate (count :*) :count))))

(defn with-count
  "Returns a count of the queried deployed components in the DB with associated
   transformation activities."
  []
  (log/warn(select deployed_components
                    (aggregate "COUNT(DISTINCT \"deployed_components\".*)" :count)
                    (join "inner" :template
                      (= :deployed_components.id :template.component_id)))))

(defn without-count
  "Returns a count of all the queried deployed components in the DB without
   associated transformation activities."  []
  (log/warn(select deployed_components
                    (aggregate (count :*) :count)
                    (join "left outer" :template
                      (= :template.component_id :deployed_components.id))
                    (where {:template.component_id nil}))))
