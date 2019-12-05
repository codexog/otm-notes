(ns otm-notes.utility-test
  (:require [otm-notes.utility :as u]
            [clojure.test :as t :refer [is]]))

(t/deftest uuid
  (is (= nil (u/uuid nil)))
  (is (= nil (u/uuid "some string")))
  (is (uuid? (u/uuid)))
  (is (uuid? (u/uuid (str (u/uuid)))))
  (let [id (u/uuid)]
    (is (= id (u/uuid (str id))))))

(t/deftest clean-assoc
  (is (= {} (u/clean-assoc {} :a nil :b #{} :c [] :d '())))
  (is (= {} (u/clean-assoc {:a 1 :b 2 :c 3} :a nil :b nil :c nil)))
  (is (= (assoc {} :a 1 :b 2) (u/clean-assoc {} :a 1 :b 2)))
  (is (= (assoc {} :a 1) (u/clean-assoc {} :a 1 :b nil))))


