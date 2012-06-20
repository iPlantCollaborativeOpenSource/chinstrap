(ns chinstrap.views.welcome
  (:require [chinstrap.views.common :as common])
  (:use [noir.core :only [defpage]]))

(defpage "/" []
    (common/layout
        [:h1 "The Chinstrap has you."]
        [:h2 "The Chinstrap has you."]
        [:h3 "The Chinstrap has you."]
        [:h4 "The Chinstrap has you."]
    ))
