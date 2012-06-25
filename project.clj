(defproject chinstrap "0.1.0-SNAPSHOT"
    :description "Middleware enabling DE job status queries to the OSM"
    :dependencies [[org.clojure/clojure "1.4.0"]
                   [org.iplantc/clojure-commons "1.1.0-SNAPSHOT"]
                   [org.iplantc/kameleon "0.0.1-SNAPSHOT"]
                   [org.clojure/data.json "0.1.2"]
                   [com.novemberain/monger "1.0.0-rc2"]
                   [korma/korma "0.3.0-beta10"]
                   [log4j/log4j "1.2.16"]
                   [noir "1.3.0-beta3"]]
    :plugins [[org.iplantc/lein-iplant-rpm "1.2.1-SNAPSHOT"]]
    :profiles {:dev {:resource-paths ["conf/test"]}}
    :main chinstrap.server
    :iplant-rpm {:summary "iPlant Chinstrap"
                :release 1
                :provides "Chinstrap"
                :dependencies ["iplant-service-config >= 0.1.0-5"]
                :config-files ["log4j.properties"]
                :config-path "conf/main"}
                :repositories {"iplantCollaborative"
                               "http://projects.iplantcollaborative.org/archiva/repository/internal/"})
