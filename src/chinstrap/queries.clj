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
  "Returns a count of all public deployed components in the DB with associated
   transformation activities."
  []
  (second (ffirst
    (select deployed_components
      (aggregate "COUNT(DISTINCT \"deployed_components\".*)" :with)
      (join "inner" :template
        (= :deployed_components.id :template.component_id))))))

(defn without-count
  "Returns a count of all the deployed components in the DB without
   associated transformation activities."  []
  (second (ffirst
    (select deployed_components
      (aggregate (count :*) :without)
      (join "left outer" :template
        (= :template.component_id :deployed_components.id))
      (where {:template.component_id nil})))))

(defn unused-list
  "Returns a list of all the deployed components in the DB without
   associated transformation activities."  []
  (select deployed_components
    (fields :name :version)
    (order :name :asc)
    (join "left outer" :template
      (= :template.component_id :deployed_components.id))
    (where {:template.component_id nil})))

(defn unused-count
  "Returns a count of undeployed tools, and tools deployed in private or deleted apps" []
  (second (ffirst (exec-raw ["
            SELECT COUNT(DISTINCT dc.*)
            FROM deployed_components dc
            LEFT JOIN template t ON dc.id = t.component_id
            LEFT JOIN transformations tx ON t.id = tx.template_id
            LEFT JOIN transformation_steps ts ON tx.id = ts.transformation_id
            LEFT JOIN transformation_task_steps tts ON ts.id = tts.transformation_step_id
            LEFT JOIN transformation_activity a ON tts.transformation_task_id = a.hid
            LEFT JOIN template_group_template tgt ON a.hid = tgt.template_id
            LEFT JOIN template_group tg ON tgt.template_group_id = tg.hid
            LEFT JOIN workspace w ON tg.workspace_id = w.id
            WHERE w.is_public IS FALSE
            OR a.deleted IS TRUE
            OR t.component_id IS NULL;"] :results))))

(defn used-count
  "Returns a count of all tools used in public apps in the DB" []
  (count (exec-raw ["
            SELECT DISTINCT dc.*
            FROM deployed_components dc
            LEFT JOIN template t ON dc.id = t.component_id
            EXCEPT
            SELECT DISTINCT dc.*
            FROM deployed_components dc
            LEFT JOIN template t ON dc.id = t.component_id
            LEFT JOIN transformations tx ON t.id = tx.template_id
            LEFT JOIN transformation_steps ts ON tx.id = ts.transformation_id
            LEFT JOIN transformation_task_steps tts ON ts.id = tts.transformation_step_id
            LEFT JOIN transformation_activity a ON tts.transformation_task_id = a.hid
            LEFT JOIN template_group_template tgt ON a.hid = tgt.template_id
            LEFT JOIN template_group tg ON tgt.template_group_id = tg.hid
            LEFT JOIN workspace w ON tg.workspace_id = w.id
            WHERE w.is_public IS FALSE
            OR a.deleted IS TRUE
            OR t.component_id IS NULL;"] :results)))
