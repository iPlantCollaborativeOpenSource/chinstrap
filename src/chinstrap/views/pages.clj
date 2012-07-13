(ns chinstrap.views.pages
  (:require [chinstrap.views.common :as template]
            [noir.response :as nr]
            [chinstrap.sqlqueries :as cq]
            [monger.collection :as mc])
  (:use [noir.core]
        [chinstrap.db]
        [chinstrap.models.model]
        [hiccup.element]))

(defpage "/" []
  (render "/main"))

(defpage "/main" []
  (template/main-page
    (image "/img/logo.png")
    [:h3 "Discovery Environment Status"]
    [:br][:br]
    [:h4#caption]
    [:div#inner "Pick a date to begin."]
    [:br]
    [:input#date {:type "text" :onChange "getInfo()"}]))

;Page listing the count of different states of Discovery Environment Apps.
(defpage "/apps" []
  (template/apps-page
    (image "/img/logo.png")
    (javascript-tag "window.setInterval(getApps,36000);")
    [:h3 "Discovery Environment App Status"]
    [:br]
    [:div#inner
      [:h3.left "Running Apps:" [:span#running.right]]
      [:h3.left "Submitted Apps:" [:span#submitted.right]]
      [:h3.left "Completed Apps:" [:span#completed.right]]]
    [:br]
    [:div.collapsibleContainer
      {:title "Currently Running Apps"}
      [:div#running-apps]]
    [:br]
    [:div.collapsibleContainer
      {:title "Submitted Apps"}
      [:div#submitted-apps]]))

;Page listing count and info of Components with no transformation activities.
(defpage "/components" []
  (template/components-page
    (image "/img/logo.png")
    (javascript-tag "window.setInterval(getComponents,36000);")
    [:h3 "Discovery Environment Components Info"]
    [:br]
    [:div#inner
      [:h3.left "With Associated Apps:" [:span#with.right]]
      [:h3.left "Without Associated Apps:" [:span#without.right]]
      [:h3.left "Total Components:" [:span#all.right]]]
    [:br]
    [:div.collapsibleContainer {:title "Unused Componenent Details"}
      [:table
        [:thead
          [:tr [:th ""]
               [:th "Name"]
               [:th "Version"]]]
        [:tbody
          (let [list (cq/unused-list) count (count list)]
              (for [i (range 1 count) :let [record (nth list i)]]
                [:tr
                  [:td.center i]
                  [:td (:name record)]
                  [:td.center (or (:version record) "No Version")]]))]]]
      [:br]
      [:div.collapsibleContainer {:title "Discovery Enviroment App Leaderboard"}
        [:table
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
