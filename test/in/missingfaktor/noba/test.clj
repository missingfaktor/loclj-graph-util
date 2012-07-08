(ns in.missingfaktor.noba.test
  (:use [clojure.test]
        [in.missingfaktor.noba graph game-data])
  (:require [in.missingfaktor.noba.jung-imagifier :as ji]
            [in.missingfaktor.noba.graphviz-imagifier :as gi]))

(def graphviz-command
  "\"C:\\Program Files\\GraphViz 2.28\\bin\\dot.exe\"")

; placeholder
(deftest imagifier-test
  (ji/save-graph-as-png :directed "testo1.png" wizard-graph)
  (ji/save-graph-as-png :undirected "utesto1.png" wizard-graph)
  (gi/save-graph-as-png :directed "testo2.png" graphviz-command wizard-graph)
  (gi/save-graph-as-png :undirected "utesto2.png" graphviz-command wizard-graph)
  (is true true))
