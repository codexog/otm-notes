(ns otm-notes.utility
  (:require [clojure.spec.alpha :as s]))

(defn uuid
  ([] (java.util.UUID/randomUUID))
  ([s] (try (java.util.UUID/fromString s)
            (catch Exception _e nil))))

(s/fdef uuid
  :args (s/cat :string (s/? (s/nilable string?)))
  :ret (s/nilable uuid?))

(defn clean-assoc
  "Acts as assoc but dissociates keys paired with values that are empty or nil."
  ([coll] coll)
  ([coll & kvs]
   (reduce (fn [coll [k v]] (if ((some-fn nil? (every-pred coll? empty?)) v)
                              (dissoc coll k)
                              (assoc coll k v)))
           coll
           (partition 2 2 kvs))))
