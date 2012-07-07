(ns in.missingfaktor.noba.imagifier2
  (:require [clojure.string :as string])
  (:use [clojure.io]))

(def
  ^{:private true}
  max-label-length 30)

(defn- shell [cmd]
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

(defn- save-dot-as-png [dot]
  (with-open [wrtr (writer "test.dot")]
    (.write wrtr dot))
  (shell "\"C:\\Program Files\\GraphViz 2.28\\bin\\dot.exe\" -O -Tpng test.dot"))

(defn save-graph-as-png [graph]
  (save-dot-as-png (dotify-graph graph)))