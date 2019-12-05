(ns otm-notes.notes-test
  (:require [otm-notes.notes :as n]
            [clojure.spec.alpha :as s]
            [clojure.test :as t :refer [is]]
            [clojure.spec.gen.alpha :as gen]))

(t/deftest add-note
  (let [note #::n {:title "A"
                   :body "B"
                   :tags [:a :b]}
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
                   :tags [:a :b]}
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

(t/deftest commutation-for-adding-and-removing-notes
  (let [state {}
        notes (gen/sample (s/gen ::n/note) 100)
        state-after (reduce n/remove-note
                            (reduce n/add-note state notes)
                            notes)]
    (println ::n/tag-register state)
    (is (s/valid? ::n/state state-after))
    (is (= state state-after))))
