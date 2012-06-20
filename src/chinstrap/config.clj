(ns chinstrap.config
  (:use [clojure.string :only (blank? split)])
  (:require [clojure-commons.props :as cc-props]
            [clojure.tools.logging :as log]))

(defn prop-file
  "The name of the properties file."
  []
  "zkhosts.properties")

(defn zk-props
  "The properties loaded from the properties file."
  []
  (cc-props/parse-properties (prop-file)))

(defn zk-url
  "The URL used to connect to zookeeper."
  []
  (get (zk-props) "zookeeper"))

(def props
  "The properites that have been loaded from Zookeeper."
  (atom nil))

(def required-props
  "The list of required properties."
  (ref []))

(def configuration-is-valid
  "True if the configuraiton is valid."
  (atom true))

(defn- record-missing-prop
  "Records a property that is missing.  Instead of failing on the first
   missing parameter, we log the missing parameter, mark the configuration
   as invalid and keep going so that we can log as many configuration errors
   as possible in one run."
  [prop-name]
  (log/error "required configuration setting" prop-name "is empty or"
             "undefined")
  (reset! configuration-is-valid false))

(defn- record-invalid-prop
  "Records a property that is invalid.  Instead of failing on the first
   invalid parameter, we log the parameter name, mark the configuraiton as
   invalid and keep going so that we can log as many configuration errors as
   possible in one run."
  [prop-name t]
  (log/error "invalid configuration setting for" prop-name ":" t)
  (reset! configuration-is-valid false))

(defn- get-str
  "Gets a string property from the properties that were loaded from
   Zookeeper."
  [prop-name]
  (let [value (get @props prop-name)]
    (log/trace prop-name "=" value)
    (when (blank? value)
      (record-missing-prop prop-name))
    value))

(defn- get-int
  "Gets an integer property from the properties that were loaded from
   Zookeeper."
  [prop-name]
  (try
    (Integer/valueOf (get-str prop-name))
    (catch NumberFormatException e
      (do (record-invalid-prop prop-name e) 0))))

(defn- get-vector
  "Gets a vector property from the properties that were loaded from
   Zookeeper."
  [prop-name]
  (split (get-str prop-name) #",\s*"))

(defmacro defprop
  "Defines a property."
  [sym docstr & init-forms]
  `(def ~(with-meta sym {:doc docstr}) (memoize (fn [] ~@init-forms))))

(defn- required
  "Registers a property in the list of required properties."
  [prop]
  (dosync (alter required-props conj prop)))

(required
  (defprop db-host
    "the host name or IP address used to connect to the database."
    (get-str "chinstrap.mongodb.host")))

(required
  (defprop db-port
    "The port used to connect to the database."
    (get-int "chinstrap.mongodb.port")))

(required
  (defprop db-database
    "The name of the database."
    (get-str "chinstrap.mongodb.database")))

(required
  (defprop db-connections-per-host
    "The max number of connections one host can have."
    (get-int "chinstrap.mongodb.connections-per-host")))

(required
  (defprop db-threads-allowed-to-block-for-connection-multiplier
    "The max number of connections one host can have."
    (get-int "chinstrap.mongodb.threads-allowed-to-block-for-connection-multiplier")))

(required
  (defprop db-max-wait-time
    "The maximum wait time in milliseconds that a thread may wait for a connection to become available. A value of 0 means that it will not wait. A negative value means to wait indefinitely."
    (get-int "chinstrap.mongodb.max-wait-time")))

(required
  (defprop db-connect-timeout
    "Time it takes for the database connection to timeout in milliseconds"
    (get-int "chinstrap.mongodb.connect-timeout")))

(required
  (defprop db-socket-timeout
    "Time it takes for the socket connection to timeout in milliseconds"
    (get-int "chinstrap.mongodb.socket-timeout")))

(required
  (defprop db-auto-connect-retry
    "Whether or not the database should attempt to auto-connect"
    (get-str "chinstrap.mongodb.auto-connect-retry")))

(required
  (defprop db-max-auto-connect-retry-time
    "The amount of time that the database should attempt to auto-connect"
    (get-int "chinstrap.mongodb.max-auto-connect-retry-time")))

(required
  (defprop db-bucket
    "The bucket in the database whose data will be accessed."
    (get-str "chinstrap.mongodb.bucket")))

(required
  (defprop db-listen-port
    "The port to listen to for incoming connections."
    (get-int "chinstrap.app.listen-port")))

(defn configuration-valid
  "Ensures that all required properties are valued."
  []
  (dorun (map #(%) @required-props))
  @configuration-is-valid)
