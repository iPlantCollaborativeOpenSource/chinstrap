(ns chinstrap.views.pages
  (:require [chinstrap.views.common :as template]
            [noir.response :as nr]
            [clojure.string :as string]
            [chinstrap.models.sqlqueries :as cq]
            [monger.collection :as mc])
  (:use [noir.core]
        [chinstrap.db]
        [chinstrap.models.model]
        [hiccup.element]))

(defpage "/" []
  (render "/info"))

(defpage "/info" []
  (template/info-page
    [:h3 "Discovery Environment App Info by Day"]
    [:br]
    [:h4#caption]
    [:div#inner "Pick a date to begin."]
    [:br]
    [:input#date.left {:onChange "getInfo()"}]))

;Page listing the count of different states of Discovery Environment Apps.
(defpage "/apps" []
  (template/apps-page
    [:h3 "Discovery Environment App Status"]
    [:br]
    [:div#inner
      [:h3.left "Running Apps:" [:span#running.right]]
      [:h3.left "Submitted Apps:" [:span#submitted.right]]
      [:h3.left "Failed Apps:" [:span#failed.right]]
      [:h3.left "Completed Apps:" [:span#completed.right]]]
    [:br]
    [:div.collapsibleContainer
      {:title "Currently Running Apps"}
      [:div#running-apps]]
    [:br]
    [:div.collapsibleContainer
      {:title "Submitted Apps"}
      [:div#submitted-apps]]
    [:br]
    [:div.collapsibleContainer
      {:title "Failed Apps"}
      [:div#failed-apps]]))

;Page listing count and info of Components with no transformation activities.
(defpage "/components" []
  (template/components-page
    [:h3 "Discovery Environment Component Info"]
    [:br]
    [:div#inner
      [:h3.left "With Associated Apps:" [:span#with.right]]
      [:h3.left "Without Associated Apps:" [:span#without.right]]
      [:h3.left "Total Components:" [:span#all.right]]]
    [:br]
    [:div.collapsibleContainer {:title "Unused Componenent Details"}
      [:button
        {:onClick "$('#unused').table2CSV({header:['#','App Name','Version']});"}
        "Export to CSV"]
      [:table#unused
        [:thead
          [:tr [:th ""]
               [:th "Name"]
               [:th "Version"]]]
        [:tbody
          (let [list (cq/unused-app-list) count (count list)]
              (for [i (range 1 count) :let [record (nth list i)]]
                [:tr
                  [:td.center i]
                  [:td (:name record)]
                  [:td.center (if
                                (or (nil? (:version record))
                                    (string/blank? (:version record)))
                                "No Version" (:version record))]]))]]]
      [:br]
      [:div.collapsibleContainer {:title "Discovery Enviroment App Leaderboard"}
        [:button
          {:onClick "$('#leaderboard').table2CSV({header:['#','Contributor Name','Number of Apps']});"}
          "Export to CSV"]
        [:table#leaderboard
          [:thead
            [:tr [:th ""]
                 [:th "Name"]
                 [:th "Count"]]]
          [:tbody
            (let [list (cq/leader-list) count (count list)]
              (for [i (range 1 count)
                    :let [record (nth list i)]]
                  [:tr
                    [:td.center i]
                    [:td (:name record)]
                    [:td.center (:count record)]]))]]]))

(defpage "/graph" []
  (render "/graph/day"))

(defpage "/graph/day" []
  (template/day-page
      [:div.select
        [:input#rb1 {:type "radio" :name "dayGroup" :onClick "setPanSelect()"} "Select&nbsp&nbsp"]
        [:input {:type "radio" :checked "true" :name "dayGroup" :onClick "setPanSelect()"} "Pan"]]))

(defpage "/graph/month" []
  (template/month-page))
