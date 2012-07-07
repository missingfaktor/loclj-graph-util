(ns in.missingfaktor.noba.test
  (:use [clojure.test]
        [in.missingfaktor.noba graph dotifier game-data interface])
  (:import [java.io File]
           [java.awt Color Dimension]
           [java.awt.image BufferedImage]
           [javax.imageio ImageIO]
           [edu.uci.ics.jung.graph SparseGraph]
           [edu.uci.ics.jung.algorithms.layout CircleLayout]
           [edu.uci.ics.jung.visualization VisualizationViewer]))

(defn- save-graph-as-jpeg [graph file-name]
  (let [lt (CircleLayout. graph)
        vv (VisualizationViewer. lt)
        size (.getPreferredSize vv)
        width (.width size)
        height (.height size)
        img (BufferedImage. width height BufferedImage/TYPE_INT_RGB)
        gfx (.createGraphics img)]
    (.fillRect gfx 0 0 width height)
    (.paintAll vv gfx)
    (.dispose gfx)
    (ImageIO/write img "jpeg" (File. file-name))))

(defn- probe-jung []
  (let [g (SparseGraph.)]
    (doto g
      (.addVertex 1)
      (.addVertex 2)
      (.addVertex 5)
      (.addEdge "Edge A" 1 2)
      (.addEdge "Edge B" 2 5))
    (println g)
    (save-graph-as-jpeg g "testo.jpeg")))

; This test is but a placeholder.
(deftest dotify-nodes-test
  (probe-jung)
  ;(dot>png (dotify-graph wizard-graph)) ; <-- Generates image but uses native shell.
  (is (dotify-identifier "hello$%df") "hello__df"))
