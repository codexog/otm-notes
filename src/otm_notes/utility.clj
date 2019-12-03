(ns otm-notes.utility)

(defn uuid
  ([] (java.util.UUID/randomUUID))
  ([s] (try (java.util.UUID/fromString s)
            (catch Exception _e nil))))
