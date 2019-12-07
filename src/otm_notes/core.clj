(ns otm-notes.core
  "Main function of the program. Handles command line arguments."
  (:gen-class)
  (:require [otm-notes.ui.cmd :as cmd]
            [otm-notes.ui.jfx :as jfx]
            [otm-notes.state :as state]))

(defn -main [& args]
  (let [args (if (nil? args) '() args)
        argument-map (if (even? (count args))
                       (into {} (comp (map (juxt (comp keyword first) (comp second)))
                                      (filter (comp #{:-f :-u} first)))
                             (partition 2 2 args))
                       {})
        shared-state (if-let [filename (:-f argument-map)]
                       (state/init filename)
                       (state/init))]
    (case (:-u argument-map)
      "cmd" (cmd/main-loop shared-state)
      "gui" (jfx/start shared-state)
      nil (do (jfx/start shared-state)
              (cmd/main-loop shared-state))
      (println "Invalid value specified for -u <user-interface>, must be either 'ui' or 'gui'."))))
