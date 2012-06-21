(ns chinstrap.views.pages
  (:require [chinstrap.views.common :as template]
            [monger.collection :as mc])
  (:use [noir.core :only [defpage]]
        [chinstrap.db]
        [hiccup.element]))

(defpage "/" []
    (template/start
        [:br][:br][:br][:br][:br][:br]
        [:br][:br][:br][:br][:br][:br]
        [:h1 (link-to "/status" "The Chinstrap has you.")]
        [:br][:br][:br][:br][:br][:br]
        (image "/img/logo.png")
    ))

(defpage "/status" []
    (template/page
        [:br][:br][:br][:br][:br][:br]
        (image "/img/logo.png")
        [:br][:br]
        [:div#info
            [:h1 "Running Jobs: " (mc/count "jobs" {:state.status "Running"})]
            [:h1 "Submitted Jobs: " (mc/count "jobs" {:state.status "Submitted"})]
            [:h1 "Completed Jobs: " (mc/count "jobs" {:state.status "Completed"})]]))
