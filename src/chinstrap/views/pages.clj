(ns chinstrap.views.pages
  (:require [chinstrap.views.common :as template])
  (:use [noir.core :only [defpage]]
        [chinstrap.db]
        [clojure.pprint]
        [hiccup.element]))

(defpage "/" []
    (template/start
        [:br][:br][:br][:br][:br][:br]
        [:br][:br][:br][:br][:br][:br]
        [:h1 (link-to "/status" "The Chinstrap has you.")]
        [:br][:br][:br][:br][:br][:br]
        (image "/img/logo.png")
    ))

(defpage "/status/" []
    (template/page
        [:br][:br][:br][:br][:br][:br]
        (image "/img/logo.png")
        [:h1 "Config Data (For Testing Only)"]
        [:p ""(interpose "<br>" (db-spec))]
        [:br][:br][:br][:br][:br][:br]
    ))
