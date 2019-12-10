(ns otm-notes.notes-test
  (:require [otm-notes.notes :as n]
            [clojure.spec.alpha :as s]
            [clojure.test :as t :refer [is deftest]]
            [clojure.test.check.properties :as prop]
            [clojure.test.check.clojure-test :refer [defspec]]
            [clojure.spec.gen.alpha :as gen]))

(deftest add-note
  (let [note #::n {:title "A"
                   :body "B"
                   :tags #{:a :b}}
        note (n/add-id-to-note note)
        id (::n/id note)
        state #::n {}
        state-after #::n {:note-register {id note}
                          :tag-register {:a #{id}
                                         :b #{id}}}]
    (is (s/valid? ::n/note note))
    (is (s/valid? ::n/state state))
    (is (s/valid? ::n/state state-after))
    (is (= state (n/add-note state nil)))
    (is (= state-after (n/add-note state note)))
    (is (= state-after (n/add-note state-after note)))))

(deftest remove-note
  (let [note #::n {:title "A"
                   :body "B"
                   :tags #{:a :b}}
        note (n/add-id-to-note note)
        id (::n/id note)
        state #::n {}
        state-after #::n {:note-register {id note}
                          :tag-register {:a #{id}
                                         :b #{id}}}]

    (is (s/valid? ::n/note note))
    (is (s/valid? ::n/state state))
    (is (s/valid? ::n/state state-after))
    (is (= state (n/remove-note state nil)))
    (is (= state (n/remove-note state-after note)))
    (is (= state (n/remove-note state note)))))

(deftest string->tags
  (is (= #{:a :b :c} (n/string->tags "a b c")))
  (is (= nil (n/string->tags nil)))
  (is (= #{} (n/string->tags "")))
  (is (= #{} (n/string->tags "   "))))

(deftest tags->string
  (is (= "a b c" (n/tags->string #{:a :b :c})))
  (is (= nil (n/tags->string nil)))
  (is (= "" (n/tags->string #{}))))

(deftest add-active-tags
  (let [state  #::n {:active-tags #{:a :b :c}}
        tags #{:c :d :e}]
    (is (s/valid? ::n/state state))
    (is (s/valid? ::n/tags tags))
    (is (= tags (::n/active-tags (n/add-active-tags nil tags))))
    (is (= nil (n/add-active-tags nil nil)))
    (is (= state (n/add-active-tags state (empty tags))))
    (is (= state (n/add-active-tags state nil)))))

(deftest remove-active-tags
  (let [state #::n {:note-register {} :tag-register {}
                    :active-tags #{:a :b :c}}
        tags (gen/generate (s/gen ::n/tags))]
    (is (= state (n/remove-active-tags state (empty tags))))
    (is (= state (n/remove-active-tags state nil)))
    (is (= nil (n/remove-active-tags nil tags)))
    (is (= (empty state) (n/remove-active-tags (empty state) tags)))
    (is (= false (contains? (n/remove-active-tags state (::n/active-tags state))
                            ::n/active-tags)))))

(defspec remove-note-is-the-left-inverse-of-add-note 100
  (prop/for-all [state (s/gen ::n/state)
                 note (s/gen ::n/note)]
                (= state (-> state
                             (n/add-note note)
                             (n/remove-note note)))))

(defspec add-note-is-idempotent
  (prop/for-all [notes (s/gen ::n/notes)
                 note (s/gen ::n/note)]
                (let [state (reduce n/add-note {} notes)
                      add-note #(n/add-note % note)]
                  (= (add-note state) (add-note (add-note state))))))

(defspec remove-note-is-idempotent
  (prop/for-all [notes (s/gen ::n/notes)
                 note (s/gen ::n/note)]
                (let [state (reduce n/add-note {} (conj notes note))
                      remove-note #(n/remove-note % note)]
                  (= (remove-note state) ((comp remove-note remove-note) state)))))


(defspec string->tags-is-the-left-inverse-of-tags->string 100
  (prop/for-all [tags (s/gen ::n/tags)]
                (= tags (-> tags
                            n/tags->string
                            n/string->tags))))
