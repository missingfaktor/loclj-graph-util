(ns in.missingfaktor.noba.graph)

(defrecord Node [id description]
  Object
  (toString [this]
    (str "Node(" (:id this) ", " (:description this) ")")))

(defrecord Edge [from to direction via]
  Object
  (toString [this]
    (str "Edge(" (:from this) " -> " (:to this) ", direction: " (:direction this) ", via: " (:via this))))