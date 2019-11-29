(ns otm-notes.notes
  (:require [clojure.spec.alpha :as s]))

(s/def ::note-body string?)
(s/def ::note-title string?)
(s/def ::tag keyword?)
(s/def ::id uuid?)
(s/def ::tags (s/coll-of ::tag :kind set?))
(s/def ::note (s/keys :req [::tags ::note-title ::note-body ::id]))
(s/def ::proto-note (s/keys :req [::tags ::title ::body]))
(s/def ::notes (s/coll-of ::notes))

(s/def ::tag-register (s/map-of ::tag (s/coll-of ::id :kind set?)))
(s/def ::note-register (s/map-of ::id ::note))

(s/def ::state (s/keys :opt [::tag-register ::note-register]))

(defn- uuid
  ([] (java.util.UUID/randomUUID))
  ([s] (java.util.UUID/fromString s)))

(defn add-id-to-note
  "Adds a uuid to the note"
  ([note] (assoc note ::id (uuid)))
  ([note s] (assoc note ::id (uuid s))))

(defn- add-note-to-note-register
  "Adds a note to the note register."
  [note-register {::keys [id] :as note}]
  (assoc note-register id note))

(defn- add-note-to-tag-register
  "Adds note to the tag register."
  [tag-register {::keys [id tags] :as _note}]
  (reduce (fn [tag-register tag]
            (assoc tag-register tag (conj (get tag-register tag #{}) id)))
          tag-register
          tags))

(defn add-note
  "Adds a note to the state."
  [{::keys [note-register tag-register] :as _state} note]
  {::note-register (add-note-to-note-register note-register note)
   ::tag-register (add-note-to-tag-register tag-register note)})


(defn- remove-note-from-note-register
  "Remove note from note register."
  [note-register {::keys [id] :as _note}]
  (dissoc note-register id))

(defn- remove-note-from-tag-register
  "Remove note from tag register."
  [tag-register {::keys [id] :as _note}]
  (into (empty tag-register) (map (fn [[k v]] [k (disj v id)])) tag-register))

(defn remove-note
  "Removes a note from the state"
  [{::keys [note-register tag-register] :as _state} note]
  {::note-register (remove-note-from-note-register note-register note)
   ::tag-register (remove-note-from-tag-register tag-register note)})

