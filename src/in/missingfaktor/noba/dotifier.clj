(ns in.missingfaktor.noba.dotifier
  (:require [clojure.string :as string]))

(def max-label-length 30)

(defn dotify-char [c]
  "Returns DOT-compatible mapping of the given character."
  (if (Character/isLetterOrDigit c) c \_))

(defn dotify-identifier [exp]
  "Returns a DOT-compatible mapping of the given identifier."
  (string/join (map dotify-char exp)))

(defn dotify-label [exp]
  "If the label is longer than the max length, it gets trimmed and an ellipsis is appended at end."
  (if (> (count exp) max-label-length)
    (str (subvec exp 0 (- max-label-length 3)) "...")
    exp))

(defn dotify-node [node]
  "Returns a DOT representation of the given node."
  (str
    \newline
    (dotify-identifier (-> node :id name))
    "\n[label=\""
    (dotify-label (str node))
    "\"];\n"))

(defn dotify-nodes [nodes]
  "Returns a dot representation of given nodes-map."
  (string/join (map dotify-node nodes)))

