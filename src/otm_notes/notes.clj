(ns otm-notes.notes
  (:require [clojure.spec.alpha :as s]
            [otm-notes.utility :refer [uuid]]))

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

(s/def ::active-tags ::tags)

(s/def ::state (s/keys :opt [::tag-register ::note-register ::active-tags]))

(defn add-id-to-note
  "Adds an id to the note"
  [note]
  (assoc note ::id (uuid)))

(defn- add-note-to-note-register
  "Adds a note to the note register."
  [note-register {::keys [id] :as note}]
  (if (and id note)
    (assoc note-register id note)
    note-register))

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
  (into {} (remove (comp empty? second))
        {::note-register (add-note-to-note-register note-register note)
         ::tag-register (add-note-to-tag-register tag-register note)}))


(defn- remove-note-from-note-register
  "Remove note from note register."
  [note-register {::keys [id] :as _note}]
  (dissoc note-register id))

(defn- remove-note-from-tag-register
  "Remove note from tag register."
  [tag-register {::keys [id] :as _note}]
  (into (empty tag-register) (comp (map (fn [[k v]] [k (disj v id)]))
                                   (remove (comp empty? second)))
        tag-register))

(defn remove-note
  "Removes a note from the state."
  [{::keys [note-register tag-register] :as _state} note]
  (into {} (remove (comp empty? second))
        {::note-register (remove-note-from-note-register note-register note)
         ::tag-register (remove-note-from-tag-register tag-register note)}))

(defn get-note-by-id
  "Returns the note with the given id."
  [{::keys [note-register] :as _state} id]
  (get note-register id))

(defn add-active-tags
  "Adds a tag or tags to the set of active tags in the state."
  [{::keys [active-tags] :as state} tags]
  (assoc state ::active-tags (into (or active-tags #{}) tags)))

(defn remove-active-tags
  "Removes a tag or tags from the set of active tags in the state."
  [{::keys [active-tags] :as state} tags]
  (assoc state ::active-tags (apply disj active-tags tags)))

(defn clear-active-tags
  "Removes all active tags from the state."
  [state]
  (dissoc state ::active-tags))

(defn active-notes
  "Returns a map consisting only of the notes that have an active tag from the state."
  [{::keys [tag-register note-register active-tags] :as _state}]
  (if (seq active-tags)
    (select-keys note-register (mapcat (partial get tag-register) active-tags))
    note-register))
