(ns in.missingfaktor.noba.test
  (:use [clojure.test]
        [in.missingfaktor.noba graph game-data])
  (:require [in.missingfaktor.noba.jung-imagifier :as ji]
            [in.missingfaktor.noba.graphviz-imagifier :as gi]))

; placeholder
(deftest imagifier-test
  (ji/save-graph-as-png :directed wizard-graph "testo1.png")
  (gi/save-graph-as-png :directed wizard-graph "testo2.png")
  (ji/save-graph-as-png :undirected wizard-graph "utesto1.png")
  (gi/save-graph-as-png :undirected wizard-graph "utesto2.png")
  (is true true))
