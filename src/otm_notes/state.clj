(ns otm-notes.state
  (:require [otm-notes.notes :as on]
            [otm-notes.io :as io]))

(defn init
  ([filename] (doto (atom {})
                (reset! (if-let [recovered-state (io/read-state filename)]
                          recovered-state
                          {}))))
  ([] (atom {})))

(defn save [state filename]
  (io/write-state @state filename))

(defn add-note [state note]
  (swap! state on/add-note note))

(defn remove-note [state note]
  (swap! state on/remove-note note))

(defn add-active-tags [state tags]
  (swap! state on/add-active-tags tags))

(defn remove-active-tags [state tags]
  (swap! state on/remove-active-tags tags))

(defn clear-active-tags [state]
  (swap! state on/clear-active-tags))

(defn get-note-by-id [state id]
  (on/get-note-by-id @state id))

(defn active-notes [state]
  (on/active-notes @state))
