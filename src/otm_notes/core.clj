(ns otm-notes.core
  (:gen-class)
  (:require [otm-notes.ui.cmd :as cmd]
            [otm-notes.ui.jfx :as jfx]
            [otm-notes.state :as state]))

(defn -main [& args]
  (let [filename (first args)
        shared-state (if filename
                       (state/init filename)
                       (state/init))]
    (jfx/start shared-state)
    (cmd/main-loop shared-state)))
