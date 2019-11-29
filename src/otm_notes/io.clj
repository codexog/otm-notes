(ns otm-notes.io
  (:require [clojure.java.io :as io]
            [clojure.edn :as edn]))

(defn read-state
  "Reads the state from the given edn file, returns nil in case of failure."
  [file-name]
  (try (with-open [reader (io/reader file-name)]
         (edn/read (java.io.PushbackReader. reader)))
       (catch Exception e
         (println (class e))
         (flush)
         nil)))

(defn write-state
  "Writes the state to the `state-file-name` file address."
  [state file-name]
  (try
    (spit file-name state)
    (flush)
    state
    (catch Exception e
      (println (class e))
      (flush)
      nil)))
