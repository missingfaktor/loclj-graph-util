(ns loclj-graph-util.data-structures)

(def orientations [:directed :undirected]) ; currently not used anywhere.

(defn fold-orientation [if-directed if-undirected orientation]
  (case orientation
    :directed if-directed
    :undirected if-undirected
    (throw (RuntimeException. "Invalid orientation value."))))

(defmacro defrecord*
  "Defines a record with a string representation in the following format:
    class-name(arg1: val1, arg2: val2, ..)"
  [rname args]
  `(defrecord ~rname [~@args]
     Object
     (toString [_]
       (str '~rname "(" ~@(interpose ", " (mapcat (fn [arg] [(str arg ": ") arg]) args)) ")"))))

(defrecord* Node [id description])

(defrecord* Edge [from to direction via])

(defrecord* Graph [nodes edges])