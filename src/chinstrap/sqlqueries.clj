(ns chinstrap.sqlqueries
  (:use [kameleon.entities]
        [korma.core]
        [chinstrap.db])
  (:require [clojure.tools.logging :as log]))

(defn all-count
  "Returns a count of all the queried deployed components in the DB."
  []
  (second (ffirst
    (select deployed_components
      (aggregate (count :name) :all)))))

(defn without-count
  "Returns a count of components that are unused or are used in private or deleted apps" []
  (second (ffirst
            (exec-raw ["SELECT COUNT(DISTINCT dc.name)
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

(defn with-count
  "Returns a count of all components that are used in public apps in the DB" []
  (second (ffirst
            (exec-raw ["SELECT COUNT(DISTINCT dc.name)
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
                        AND a.deleted IS NOT TRUE
                        AND t.component_id IS NOT NULL;"] :results))))

(defn unused-list
  "Returns a list of all the deployed components in the DB that do not have
   associated transformation activities."  []
  (exec-raw ["SELECT DISTINCT dc.name, dc.version
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

(defn leader-list
   "Returns a list of all users with public apps and aggregates a count of
   them so that they can be ranked according to #'s of apps." []
  (exec-raw ["SELECT COUNT(ind.integrator_name) count, ind.integrator_name AS name
              FROM deployed_components dc
              LEFT JOIN template t ON dc.id = t.component_id
              LEFT JOIN transformations tx ON t.id = tx.template_id
              LEFT JOIN transformation_steps ts ON tx.id = ts.transformation_id
              LEFT JOIN transformation_task_steps tts ON ts.id = tts.transformation_step_id
              LEFT JOIN transformation_activity a ON tts.transformation_task_id = a.hid
              LEFT JOIN integration_data ind ON a.integration_data_id = ind.id
              WHERE t.component_id IS NOT NULL
              AND EXISTS (
                SELECT * FROM template_group_template tgt
                LEFT JOIN template_group tg ON tgt.template_group_id = tg.hid
                LEFT JOIN workspace w ON tg.workspace_id = w.id
                WHERE w.is_public IS TRUE
                AND tgt.template_id = a.hid
              )
              GROUP BY integrator_name
              ORDER BY count DESC, name ASC;"] :results))

(defn apps-by-ids
  "This function takes a collection of analysis_ids and queries the postgres
  database for distinct matching tool names."
  [ids]
  (select "template"
    (modifier "distinct")
    (aggregate (count :name) :count :name)
    (fields :name)
    (where {:name [in
      (map #(subselect "template"
        (fields :name)
        (where {:id %}))ids )]})
    (order :count :desc)))
