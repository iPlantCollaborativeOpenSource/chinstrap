(ns chinstrap.db
    (:use [chinstrap.config]
          [korma.db])
    (:import [com.mongodb MongoOptions ServerAddress])
    (:require [clojure.tools.logging :as log]
              [monger.core :as mg ]
              [clojure-commons.clavin-client :as cl]))

(defn load-configuration
  "Loads the configuration properties from Zookeeper."
  []
  (cl/with-zk
    (zk-props)
    (when-not (cl/can-run?)
      (log/warn "Chinstrap has no configuration data from zookeeper.")
      (log/warn "Attempting to load local configuration data...")
        (System/exit 1))
    (reset! props (cl/properties "chinstrap")))
  (log/warn @props)
  (when-not (configuration-valid)
    (log/warn "THE CONFIGURATION IS INVALID - EXITING NOW")
    (System/exit 1)))

(defn postgresdb-spec
  "Constructs a database connection specification from the configuration
   settings."
  []
  {:classname (postgresdb-driver)
   :subprotocol (postgresdb-subprotocol)
   :subname (str "//" (postgresdb-host) ":" (postgresdb-port) "/" (postgresdb-database))
   :user (postgresdb-user)
   :password (postgresdb-password)
   :max-idle-time (postgresdb-max-idle-time)})

(defn korma-define
  "Defines a korma representation of the database using the settings passed in from zookeeper."
  []
  (let [spec (postgresdb-spec)]
    (defonce de (create-db spec))
    (default-connection de)))

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
  "Sets up a connection to the database using config data loaded from zookeeper into Monger and Korma."
  []
  (load-configuration)
  (mongodb-connect)
  (korma-define))
