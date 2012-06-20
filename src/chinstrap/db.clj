(ns chinstrap.db
    (:use [monger.core :as mg]
          [chinstrap.config])
    (:import [com.mongodb MongoOptions ServerAddress])
    (:require [clojure.tools.logging :as log]
              [clojure-commons.clavin-client :as cl]))

(let [^MongoOptions opts
          (mg/mongo-options
              :threads-allowed-to-block-for-connection-multiplier 300)
      ^ServerAddress sa  (mg/server-address "127.0.0.1" 27017)]
    (mg/connect! sa opts))

(defn load-configuration
  "Loads the configuration properties from Zookeeper."
  []
  (cl/with-zk
    (zk-url)
    (when (not (cl/can-run?))
      (log/warn "THIS APPLICATION CANNOT RUN ON THIS MACHINE. SO SAYETH ZOOKEEPER.")
      (log/warn "THIS APPLICATION WILL NOT EXECUTE CORRECTLY.")
      (System/exit 1))
    (reset! props (cl/properties "chinstrap")))
  (log/warn @props)
  (when (not (configuration-valid))
    (log/warn "THE CONFIGURATION IS INVALID - EXITING NOW")
    (System/exit 1)))

(defn db-spec
  "Constructs a database connection specification from the configuration settings."
  []
  {:host (db-host)
   :port (db-port)
   :dbname (db-database)
   :connections-per-host (db-connections-per-host)
   :tatbfcm (db-threads-allowed-to-block-for-connection-multiplier)
   :max-wait-time (db-max-wait-time)
   :connect-timeout (db-connect-timeout)
   :socket-timeout (db-socket-timeout)
   :auto-connect-retry (db-auto-connect-retry)
   :max-auto-connect-retry-time (db-max-auto-connect-retry-time)
   :bucket (db-bucket)
   :listen-port (db-listen-port)})

(defn db-config
  "Sets up a connection to the database using config data loaded from zookeeper into Monger."
  []
  (load-configuration))
