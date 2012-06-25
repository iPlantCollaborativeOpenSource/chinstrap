(ns chinstrap.db
    (:use [chinstrap.config])
    (:import [com.mongodb MongoOptions ServerAddress])
    (:require [clojure.tools.logging :as log]
              [monger.core :as mg ]
              [clojure-commons.clavin-client :as cl]))

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
  "Constructs a database connection specification from the configuration
   settings."
  []
  {:classname (postgresdb-driver)
   :subprotocol (postgresdb-subprotocol)
   :subname (str "//" (postgresdb-host) ":" (postgresdb-port) "/" (postgresdb-database))
   :user (postgresdb-user)
   :password (postgresdb-password)
   :max-idle-time (postgresdb-max-idle-time)})

(defn mongodb-connect
  "Connects to the mongoDB using the settings passed in from zookeeper to monger."
  []
  (let [^MongoOptions opts
            (mg/mongo-options
                :connections-per-host (mongodb-connections-per-host)
                :max-wait-time (mongodb-max-wait-time)
                :connect-timeout (mongodb-connect-timeout)
                :socket-timeout (mongodb-socket-timeout)
                :auto-connect-retry (mongodb-auto-connect-retry))
        ^ServerAddress sa
            (mg/server-address (mongodb-host) (mongodb-port))]
       (mg/connect! sa opts))
  (mg/set-db! (mg/get-db (mongodb-database))))

(defn db-config
  "Sets up a connection to the database using config data loaded from zookeeper into Monger."
  []
  (load-configuration)
  (mongodb-connect))
