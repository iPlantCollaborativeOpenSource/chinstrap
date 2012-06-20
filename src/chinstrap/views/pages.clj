(ns chinstrap.views.pages
  (:require [chinstrap.views.common :as template])
  (:use [noir.core :only [defpage]]
        [chinstrap.db]
        [hiccup.element]))

(defpage "/" []
    (template/start
        [:br][:br][:br][:br][:br][:br]
        [:br][:br][:br][:br][:br][:br]
        [:h1 "The Chinstrap has you."]
    ))

(defpage "/status" []
    (template/page
        (image "/img/logo.png")
        [:h1 "The Chinstrap has you."]
    ))
