(ns otm-notes.io
  (:require [clojure.java.io :as io]
            [clojure.edn :as edn]))

(defn read-state
  "Reads the state from the given edn file, returns nil in case of failure."
  [filename]
  (try (with-open [reader (io/reader filename)]
         (edn/read (java.io.PushbackReader. reader)))
       (catch Exception e
         (println (class e))
         (flush)
         nil)))

(defn write-state
  "Writes the state to the `filename` file address."
  [state filename]
  (try
    (spit filename state)
    (flush)
    state
    (catch Exception e
      (println (class e))
      (flush)
      nil)))
