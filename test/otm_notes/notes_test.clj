(ns otm-notes.notes-test
  (:require [otm-notes.notes :as n]
            [clojure.spec.alpha :as s]
            [clojure.test :as t :refer [is]]))

(t/deftest adding-notes
  (let [note #::n {:note-title "A"
                   :note-body "B"
                   :tags #{:a :b}}
        note (n/add-id-to-note note)
        id (::n/id note)
        state #::n {:note-register {}
                    :tag-register {}}
        state-after #::n {:note-register {id note}
                          :tag-register {:a #{id}
                                         :b #{id}}}]
    (is (s/valid? ::n/note note))
    (is (s/valid? ::n/state state))
    (is (s/valid? ::n/state state-after))
    (is (= state-after (n/add-note state note)))))
