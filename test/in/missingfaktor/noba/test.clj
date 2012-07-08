(ns in.missingfaktor.noba.test
  (:use [clojure.test]
        [in.missingfaktor.noba graph game-data])
  (:require [in.missingfaktor.noba.jung-imagifier :as ji]
            [in.missingfaktor.noba.graphviz-imagifier :as gi]))

; placeholder
(deftest imagifier-test
  (println (gi/dotify-undirected-graph wizard-graph))
  (ji/save-graph-as-png wizard-graph "testo1.png")
  (gi/save-graph-as-png wizard-graph "testo2.png")
  (gi/save-undirected-graph-as-png wizard-graph "utesto2.png")
  (is true true))
