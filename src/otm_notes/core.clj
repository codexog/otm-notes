(ns otm-notes.core
  (:gen-class)
  (:require [otm-notes.ui.cmd :as cmd]
            [otm-notes.state :as state]))

(defn -main [& args]
  (if-let [filename (first args)]
    (cmd/main-loop (state/init filename))
    (cmd/main-loop (state/init))))
