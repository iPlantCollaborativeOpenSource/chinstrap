(ns chinstrap.server
  (:gen-class)
  (:use [chinstrap.config :only [listen-port]]
        [chinstrap.db])
  (:require [noir.server :as server]))

(server/load-views-ns 'chinstrap.views)

(defn -main [& m]
  (db-config)
  (let [mode (keyword (or (first m) :dev))
        port (listen-port)]
    (server/start port {:mode mode
                        :ns 'chinstrap})))

