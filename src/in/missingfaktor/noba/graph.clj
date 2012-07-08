(ns in.missingfaktor.noba.graph)

(def graph-orientations [:directed :undirected])

(defrecord Node [id description]
  Object
  (toString [_]
    (str "Node(" id ", " description ")")))

(defrecord Edge [from to direction via]
  Object
  (toString [_]
    (str "Edge(" from " -> " to ", direction: " direction ", via: " via)))

(defrecord Graph [nodes edges]
  Object
  (toString [_]
    (str "Graph(" nodes ", " edges ")")))