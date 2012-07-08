(ns in.missingfaktor.noba.graphviz-imagifier
  (:require [clojure.string :as string])
  (:import [in.missingfaktor.noba.graph Edge])
  (:use [clojure.java.io]
        [clojure.pprint]))

(def
  ^{:private true}
  max-label-length 30)

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

(defn- dotify-edge [{:keys [from to direction via]}]
  (str
    \newline
    (dotify-identifier (name from))
    " -> "
    (dotify-identifier (name to))
    "[label=\""
    (dotify-label (str "(" direction ", " via ")"))
    "\"];"))

(defn- dotify-edges [edges]
  (join-with dotify-edge edges))

(defn- dotify-graph [{:keys [nodes edges]}]
  (str
    "digraph {"
    (dotify-nodes nodes)
    (dotify-edges edges)
    "}"))

(defn- shell-exec [cmd]
  (.exec (Runtime/getRuntime) cmd))

(defn- save-dot-as-png [dot file-name]
  (with-open [wrtr (writer "test.dot")]
    (.write wrtr dot))
  (shell-exec (str "\"C:\\Program Files\\GraphViz 2.28\\bin\\dot.exe\" -Tpng test.dot -o " file-name)))

(defn save-graph-as-png [graph file-name]
  (save-dot-as-png (dotify-graph graph) file-name))

;;;;;;;;; Undirected-graph counterparts ;;;;;;;;;

; These are indicated with a prime sign.


; Similar to tails in Haskell, except the output doesn't include an empty sequence.
(defn- rests [coll]
  (take (count coll) (iterate rest coll)))

(defn tap [desc x]
  (println desc)
  (println x)
  x)

(defn- dotify-edges' [edges]
  (let [edge-groupings (seq (group-by :from edges))]
    (string/join (for [lst (rests edge-groupings)
                       edge (second (first lst))
                       :let [{:keys [from to direction via]} edge]]
                     (if (some #(= to (first %)) (rest lst))
                       ""
                       (str
                         \newline
                         (-> from name dotify-identifier)
                         "--"
                         (-> to name dotify-identifier)
                         "[label=\""
                         (dotify-label (str "(" direction ", " via ")"))
                         "\"];"))))))

(defn dotify-graph' [{:keys [nodes edges]}]
  (str
    "graph{"
    (dotify-nodes nodes)
    (dotify-edges' edges)
    "}"))

(defn save-graph-as-png' [graph file-name]
  (save-dot-as-png (dotify-graph' graph) file-name))
