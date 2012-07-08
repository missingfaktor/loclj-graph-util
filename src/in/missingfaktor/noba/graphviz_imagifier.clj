(ns in.missingfaktor.noba.graphviz-imagifier
  (:require [clojure.string :as string])
  (:import [in.missingfaktor.noba.graph Edge])
  (:use [clojure.java.io]
        [clojure.pprint]))

(def
  ^{:private true}
  max-label-length 30)

; Similar to tails in Haskell, except the output doesn't include an empty sequence.
(defn- rests [coll]
  (take (count coll) (iterate rest coll)))

(defn- shell-exec [cmd]
  (.exec (Runtime/getRuntime) cmd))

(defn- join-with [f coll]
  (string/join (map f coll)))

(defn- dotify-char [c]
  (if (Character/isLetterOrDigit c) c \_))

(defn- dotify-identifier [exp]
  (join-with dotify-char exp))

(defn- dotify-label [exp]
  "If the label is longer than the max length, it gets trimmed and an ellipsis is appended at end."
  (if (> (count exp) max-label-length)
    (str (.substring exp 0 (- max-label-length 3)) "...")
    exp))

(defn- dotify-node [node]
  (str
    \newline
    (dotify-identifier (-> node :id name))
    "[label=\""
    (dotify-label (str node))
    "\"];"))

(defn- dotify-nodes [nodes]
  (join-with dotify-node nodes))

(defn- dotify-edge [orientation {:keys [from to direction via]}]
  (str
    \newline
    (dotify-identifier (name from))
    (case orientation
      :directed " -> "
      :undirected " -- ")
    (dotify-identifier (name to))
    "[label=\""
    (dotify-label (str "(" direction ", " via ")"))
    "\"];"))

(defn- dotify-directed-edges [edges]
  (join-with (partial dotify-edge :directed) edges))

(defn- dotify-undirected-edges [edges]
  (let [edge-groupings (seq (group-by :from edges))]
    (string/join (for [lst (rests edge-groupings)
                       edge (second (first lst))]
                   (if (some #(= (:to edge) (first %)) (rest lst))
                     ""
                     (dotify-edge :undirected edge))))))

(defn- dotify-edges [orientation edges]
  (case orientation
    :directed (dotify-directed-edges edges)
    :undirected (dotify-undirected-edges edges)))

(defn- dotify-graph [orientation {:keys [nodes edges]}]
  (str
    (case orientation
      :directed "digraph {"
      :undirected "graph {")
    (dotify-nodes nodes)
    (dotify-edges orientation edges)
    "}"))

(defn- save-dot-as-png [dot file-name]
  (with-open [wrtr (writer "test.dot")]
    (.write wrtr dot))
  (shell-exec (str "\"C:\\Program Files\\GraphViz 2.28\\bin\\dot.exe\" -Tpng test.dot -o " file-name)))

(defn save-graph-as-png [orientation graph file-name]
  (save-dot-as-png (dotify-graph orientation graph) file-name))

