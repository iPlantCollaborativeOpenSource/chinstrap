(defproject chinstrap "0.1.0-SNAPSHOT"
    :description "Diagnostic and status front-end for backend data"
    :dependencies [[org.clojure/clojure "1.4.0"]
                   [org.iplantc/clojure-commons "1.1.0-SNAPSHOT"]
                   [org.iplantc/kameleon "0.0.1-SNAPSHOT"]
                   [clj-time "0.4.3"]
                   [org.clojure/data.json "0.1.2"]
                   [com.novemberain/monger "1.1.2"]
                   [korma/korma "0.3.0-beta10"]
                   [log4j/log4j "1.2.16"]
                   [noir "1.3.0-beta10"]]
    :plugins [[org.iplantc/lein-iplant-rpm "1.3.2-SNAPSHOT"]]
    :profiles {:dev {:resource-paths ["resources/conf/test"]}}
    :aot [chinstrap.server]
    :main chinstrap.server
    :iplant-rpm {:summary "iPlant Chinstrap"
                :provides "chinstrap"
                :dependencies ["iplant-service-config >= 0.1.0-5"]
                :config-files ["log4j.properties"]
                :config-path "resources/conf/main"}
                :repositories {"iplantCollaborative"
                               "http://projects.iplantcollaborative.org/archiva/repository/internal/"})
