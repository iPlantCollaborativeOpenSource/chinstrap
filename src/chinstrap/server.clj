(ns chinstrap.server
  (:gen-class)
  (:use [chinstrap.db])
  (:require [noir.server :as server]))

(server/load-views-ns 'chinstrap.views)

(defn -main [& m]
  (db-config)
  (let [mode (keyword (or (first m) :dev))
        port (Integer. (get (System/getenv) "PORT" "8080"))]
    (server/start port {:mode mode
                        :ns 'chinstrap})))

