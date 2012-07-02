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

(defn unused-list-deprecate
  "Returns a list of all the deployed components in the DB without
   associated transformation activities."  []
  (select deployed_components
    (fields :name :version)
    (order :name :asc)
    (join "left outer" :template
      (= :template.component_id :deployed_components.id))
    (where {:template.component_id nil})))

(defn used-count-korma
  "Returns a count of tools publicly deployed in DE apps" []
  (second (ffirst
    (select deployed_components
      (aggregate "COUNT(DISTINCT \"deployed_components\".*)" :unused)
      (join "LEFT" :template (= :deployed_components.id :template.component_id))
      (join "LEFT" :transformations (= :template.id :transformations.template_id))
      (join "LEFT" :transformation_steps (= :transformations.id :transformation_steps.transformation_id))
      (join "LEFT" :transformation_task_steps (= :transformation_steps.id :transformation_task_steps.transformation_step_id))
      (join "LEFT" :transformation_activity (= :transformation_task_steps.transformation_task_id :transformation_activity.hid))
      (join "LEFT" :template_group_template (= :transformation_activity.hid :template_group_template.template_id))
      (join "LEFT" :template_group (= :template_group_template.template_group_id :template_group.hid))
      (join "LEFT" :workspace (= :template_group.workspace_id :workspace.id))
      (where {:workspace.is_public true :template.component_id [not= nil]})))))

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
