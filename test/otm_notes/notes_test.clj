(ns otm-notes.notes-test
  (:require [otm-notes.notes :as n]
            [clojure.spec.alpha :as s]
            [clojure.test :as t :refer [is]]
            [clojure.test.check.properties :as prop]
            [clojure.test.check.clojure-test :refer [defspec]]
            [clojure.spec.gen.alpha :as gen]))

(t/deftest add-note
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
    (is (= state-after (n/add-note state note)))))

(t/deftest remove-note
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
    (is (= state (n/remove-note state-after note)))))

(t/deftest string->tags
  (is (= #{:a :b :c} (n/string->tags "a b c")))
  (is (= nil (n/string->tags nil)))
  (is (= #{} (n/string->tags ""))))

(t/deftest tags->string
  (is (= "a b c" (n/tags->string #{:a :b :c})))
  (is (= nil (n/tags->string nil)))
  (is (= "" (n/tags->string #{}))))

(defspec remove-note-is-the-left-inverse-of-add-note 100
  (prop/for-all [state (s/gen ::n/state)
                 note (s/gen ::n/note)]
                (= state (-> state
                             (n/add-note note)
                             (n/remove-note note)))))

(t/deftest commutation-for-tag-string
  (is (true? (every? (fn [tags] (= tags (n/string->tags (n/tags->string tags))))
                     (gen/sample (s/gen ::n/tags) 1000)))))
