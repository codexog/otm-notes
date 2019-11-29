(ns otm-notes.core
  (:gen-class)
  (:require [otm-notes.io :as io]
            [clojure.pprint :refer [pprint]]))

(defn -main [& args]
  (let [filename (first args)]
    (if filename
      (let [state (io/read-state filename)]
        (pprint state))
      (println "No file specified"))))
