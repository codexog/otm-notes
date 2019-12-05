(ns otm-notes.state
  (:require [otm-notes.notes :as on]
            [otm-notes.io :as io]))

(defn init
  ([filename] (doto (atom {::filename filename})
                (swap! assoc ::notes (if-let [recovered-state (io/read-state filename)]
                                       recovered-state
                                       {}))))
  ([] (atom {})))

(defn save
  ([state filename] (io/write-state (::notes @state) filename))
  ([state] (let [state @state]
             (io/write-state (::notes state) (::filename state)))))

(defn set-filename
  ([state filename]
   (swap! state assoc ::filename filename)))

(defn add-note [state note]
  (swap! state update ::notes on/add-note note))

(defn remove-note [state note]
  (swap! state update ::notes on/remove-note note))

(defn update-note [state note-update]
  (swap! state update ::notes on/update-note note-update))

(defn add-active-tags [state tags]
  (swap! state update ::notes on/add-active-tags tags))

(defn remove-active-tags [state tags]
  (swap! state update ::notes on/remove-active-tags tags))

(defn clear-active-tags [state]
  (swap! state update ::notes on/clear-active-tags))

(defn set-active-tags [state tags]
  (swap! state update ::notes on/set-active-tags tags))

(defn get-note-by-id [state id]
  (on/get-note-by-id (::notes @state) id))

(defn active-tags [state]
  (get-in @state [::notes ::on/active-tags]))

(defn active-notes [state]
  (on/active-notes (::notes @state)))
