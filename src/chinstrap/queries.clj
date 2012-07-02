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

(defn unused-count
  "Returns a count of undeployed tools, and tools deployed in private or deleted apps" []
  (second (ffirst
            (exec-raw ["SELECT COUNT(DISTINCT dc.*)
                        FROM deployed_components dc
                        WHERE NOT EXISTS (
                        SELECT t.id FROM template t
                        LEFT JOIN transformations tx ON t.id = tx.template_id
                        LEFT JOIN transformation_steps ts ON tx.id = ts.transformation_id
                        LEFT JOIN transformation_task_steps tts ON ts.id = tts.transformation_step_id
                        LEFT JOIN transformation_activity a ON tts.transformation_task_id = a.hid
                        LEFT JOIN template_group_template tgt ON a.hid = tgt.template_id
                        LEFT JOIN template_group tg ON tgt.template_group_id = tg.hid
                        LEFT JOIN workspace w ON tg.workspace_id = w.id
                        WHERE t.component_id = dc.id
                        AND a.deleted IS FALSE
                        AND w.is_public IS TRUE);"] :results))))

(defn used-count
  "Returns a count of all tools used in public apps in the DB" []
  (second (ffirst
            (exec-raw ["SELECT COUNT(DISTINCT dc.*)
                        FROM deployed_components dc
                        LEFT JOIN template t ON dc.id = t.component_id
                        LEFT JOIN transformations tx ON t.id = tx.template_id
                        LEFT JOIN transformation_steps ts ON tx.id = ts.transformation_id
                        LEFT JOIN transformation_task_steps tts ON ts.id = tts.transformation_step_id
                        LEFT JOIN transformation_activity a ON tts.transformation_task_id = a.hid
                        LEFT JOIN template_group_template tgt ON a.hid = tgt.template_id
                        LEFT JOIN template_group tg ON tgt.template_group_id = tg.hid
                        LEFT JOIN workspace w ON tg.workspace_id = w.id
                        WHERE w.is_public IS TRUE
                        AND t.component_id IS NOT NULL;"] :results))))

(defn unused-list
  "Returns a list of all the deployed components in the DB without
   associated transformation activities."  []
  (exec-raw ["SELECT DISTINCT dc.*
              FROM deployed_components dc
              WHERE NOT EXISTS (
              SELECT t.id FROM template t
              LEFT JOIN transformations tx ON t.id = tx.template_id
              LEFT JOIN transformation_steps ts ON tx.id = ts.transformation_id
              LEFT JOIN transformation_task_steps tts ON ts.id = tts.transformation_step_id
              LEFT JOIN transformation_activity a ON tts.transformation_task_id = a.hid
              LEFT JOIN template_group_template tgt ON a.hid = tgt.template_id
              LEFT JOIN template_group tg ON tgt.template_group_id = tg.hid
              LEFT JOIN workspace w ON tg.workspace_id = w.id
              WHERE t.component_id = dc.id
              AND a.deleted IS FALSE
              AND w.is_public IS TRUE)
              ORDER BY dc.name ASC;"] :results))
