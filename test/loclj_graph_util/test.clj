(ns loclj-graph-util.test
  (:use [clojure.test]
        [loclj-graph-util.data-structures])
  (:require [loclj-graph-util.jung-imagifier :as ju]
            [loclj-graph-util.graphviz-imagifier :as gv])
  (:import [loclj_graph_util.data_structures Node Edge Graph])) ; ☜ Those underscores make me sad.

(def wizard-nodes [(Node. :living-room "You are in the living room. A wizard is snoring loudly at the couch.")
                   (Node. :garden "You are in a garden full of gnomes, and that is creeping you out.")
                   (Node. :attic "You are in the attic, trying to persuade youself that there are no monsters there.")])

(def wizard-edges [(Edge. :living-room :garden :west :door )
                   (Edge. :living-room :attic :upstairs :ladder )
                   (Edge. :garden :living-room :east :door )
                   (Edge. :attic :living-room :downstairs :ladder )])

(def wizard-graph (Graph. wizard-nodes wizard-edges))

; Change this to point the dot executable in your Graphviz installation.
(def graphviz-command
  "\"C:\\Program Files\\GraphViz 2.28\\bin\\dot.exe\"")

(deftest imagifier-test
  (ju/save-graph-as-png :directed "testo1.png" wizard-graph)
  (ju/save-graph-as-png :undirected "utesto1.png" wizard-graph)
  (gv/save-graph-as-png :directed "testo2.png" graphviz-command wizard-graph)
  (gv/save-graph-as-png :undirected "utesto2.png" graphviz-command wizard-graph)
  (is true true))
