(ns chinstrap.kormaconfig
  (:use [korma.db]
        [chinstrap.config]
        [chinstrap.db])
  (:require [clojure.tools.logging :as log]))

(defn define-database
  "Defines the database connection to use from within Clojure."
  []
  (let [spec (db-spec)]
    (defonce de (create-db spec))
    (default-connection de)))
