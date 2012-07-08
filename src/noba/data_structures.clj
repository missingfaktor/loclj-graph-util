(ns noba.data-structures)

(def orientations [:directed :undirected]) ; currently not used anywhere.

(defn fold-orientation [if-directed if-undirected orientation]
  (case orientation
    :directed if-directed
    :undirected if-undirected
    (throw (RuntimeException. "Invalid orientation value."))))

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