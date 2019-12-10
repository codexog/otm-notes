(ns otm-notes.notes
  "Contains all data processing functionality."
  (:require [clojure.spec.alpha :as s]
            [otm-notes.utility :refer [uuid clean-assoc]]
            [clojure.string :refer [trim split join]]
            [clojure.set :refer [intersection difference union]]))

(s/def ::body string?)
(s/def ::title string?)
(s/def ::tag keyword?)
(s/def ::id uuid?)
(s/def ::tags (s/coll-of ::tag :kind set? :distinct ::tag))
(s/def ::note (s/keys :req [::tags ::title ::body ::id]))
(s/def ::note-update (s/keys :req [::id] :opt [::title ::body ::tags]))
(s/def ::proto-note (s/keys :req [::tags ::title ::body]))
(s/def ::notes (s/coll-of ::note :kind vector?))
(s/def ::tag-register (s/and (s/map-of ::tag (s/and (s/coll-of ::id :kind set?)
                                                    not-empty))
                             not-empty))
(s/def ::note-register (s/and (s/map-of ::id ::note)
                              not-empty))
(s/def ::active-tags (s/and ::tags
                            not-empty))
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
  [{::keys [note-register tag-register] :as state} note]
  (clean-assoc
    state
    ::note-register (add-note-to-note-register note-register note)
    ::tag-register (add-note-to-tag-register tag-register note)))

(s/fdef add-note
  :args (s/cat :state ::state :note ::note)
  :ret ::state)


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
  [{::keys [note-register tag-register] :as state} note]
  (clean-assoc
    state
    ::note-register (remove-note-from-note-register note-register note)
    ::tag-register (remove-note-from-tag-register tag-register note)))

(s/fdef remove-note
  :args (s/cat :state ::state :note ::note)
  :ret ::state)

(defn get-note-by-id
  "Returns the note with the given id."
  [{::keys [note-register] :as _state} id]
  (get note-register id))

(defn update-note
  "Updates a note in the state corresponding to the `note-update` given."
  [{::keys [note-register] :as state} {::keys [id] :as note-update}]
  (if (contains? note-register id)
    (let [old-note (get-note-by-id state id)
          new-note (merge old-note note-update)]
      (add-note (remove-note state old-note) new-note))
    state))

(defn add-active-tags
  "Adds a tag or tags to the set of active tags in the state."
  [{::keys [active-tags] :as state} tags]
  (clean-assoc
    state ::active-tags (union active-tags tags)))

(defn remove-active-tags
  "Removes a tag or tags from the set of active tags in the state."
  [{::keys [active-tags] :as state} tags]
  (clean-assoc
    state ::active-tags (difference active-tags tags)))

(defn clear-active-tags
  "Removes all active tags from the state."
  [state]
  (dissoc state ::active-tags))

(defn set-active-tags
  "Sets the active tags to be the given tags."
  [state tags]
  (add-active-tags (clear-active-tags state) tags))

(defn active-notes
  "Returns a map consisting only of the notes that have an active tag from the state."
  [{::keys [tag-register note-register active-tags] :as _state}]
  (if (and (seq active-tags) (seq note-register))
    (select-keys note-register (apply intersection (map #(get tag-register % #{}) active-tags)))
    note-register))

(defn string->tags
  "Takes a string of space separated keywords and outputs a tag vector."
  [s]
  (when s
    (into #{} (comp (map trim)
                    (remove empty?)
                    (map keyword))
          (seq (split (trim s) #" ")))))

(s/fdef string->tags
  :args (s/cat :string string?)
  :ret ::tags)

(defn tags->string
  "Takes a vector of tags and ouputs a string of space separated keywords."
  [tags]
  (when tags
    (join " " (sort (map (comp #(subs % 1) str) tags)))))

(s/fdef tags->string
  :args (s/cat :tags ::tags)
  :ret string?)
