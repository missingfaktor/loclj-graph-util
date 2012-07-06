(ns in.missingfaktor.noba.dotifier
  "This module contains functions for generating DOT-representations of given values."
  (:require [clojure.string :as string]))

(def max-label-length 30)

(defn- join-with [f coll]
  (string/join (map f coll)))

(defn dotify-char [c]
  (if (Character/isLetterOrDigit c) c \_))

(defn dotify-identifier [exp]
  (join-with dotify-char exp))

(defn dotify-label [exp]
  "If the label is longer than the max length, it gets trimmed and an ellipsis is appended at end."
  (if (> (count exp) max-label-length)
    (str (.substring exp 0 (- max-label-length 3)) "...")
    exp))

(defn dotify-node [node]
  (str
    \newline
    (dotify-identifier (-> node :id name))
    "[label=\""
    (dotify-label (str node))
    "\"];"))

(defn dotify-nodes [nodes]
  (join-with dotify-node nodes))

(defn dotify-edge [{:keys [from to direction via]}]
  (str
    \newline
    (dotify-identifier (name from))
    " -> "
    (dotify-identifier (name to))
    "[label=\""
    (dotify-label (str "(" direction ", " via ")"))
    "\"];"))

(defn dotify-edges [edges]
  (join-with dotify-edge edges))

(defn dotify-graph [{:keys [nodes edges]}]
  (str
    "digraph {"
    (dotify-nodes nodes)
    (dotify-edges edges)
    "}"))
