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
  (defprop listen-port
    "The port to listen to for incoming connections."
    (get-int "chinstrap.listen-port")))

(required
  (defprop db-vendor
    "The name of the database vendor (e.g. postgresql)."
    (get-str "chinstrap.db.vendor")))

(required
  (defprop db-host
    "the host name or IP address used to connect to the database."
    (get-str "chinstrap.db.host")))

(required
  (defprop db-port
    "The port used to connect to the database."
    (get-str "chinstrap.db.port")))

(required
  (defprop db-name
    "The name of the database."
    (get-str "chinstrap.db.name")))

(required
  (defprop db-user
    "The username used to authenticate to the databse."
    (get-str "chinstrap.db.user")))

(required
  (defprop db-max-idle-time
    "The maximum amount of time to retain idle database connections."
    (* (get-int "chinstrap.db.max-idle-minutes") 60)))

(defn configuration-valid
  "Ensures that all required properties are valued."
  []
  (dorun (map #(%) @required-props))
  @configuration-is-valid)
