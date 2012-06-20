(ns chinstrap.views.pages
  (:require [chinstrap.views.common :as template])
  (:use [noir.core :only [defpage]]))

(defpage "/" []
    (template/start
        [:br][:br][:br][:br][:br][:br]
        [:br][:br][:br][:br][:br][:br]
        [:h1 "The Chinstrap has you."]
    ))
