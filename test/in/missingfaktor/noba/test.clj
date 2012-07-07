(ns in.missingfaktor.noba.test
  (:use [clojure.test]
        [in.missingfaktor.noba graph dotifier game-data interface])
  (:import [java.io File]
           [java.awt Color Dimension BasicStroke]
           [java.awt.image BufferedImage]
           [java.awt.geom Point2D$Double Ellipse2D$Double]
           [javax.imageio ImageIO]
           [edu.uci.ics.jung.graph SparseGraph]
           [edu.uci.ics.jung.graph.util EdgeType]
           [edu.uci.ics.jung.algorithms.layout CircleLayout]
           [edu.uci.ics.jung.visualization VisualizationViewer VisualizationImageServer]
           [edu.uci.ics.jung.visualization.decorators ToStringLabeller]
           [edu.uci.ics.jung.visualization.renderers Renderer$VertexLabel$Position]
           [org.apache.commons.collections15.functors ConstantTransformer]))

(def
  ^{:private true}
  preferred-image-dimension
  (Dimension. 600 600))

(defn- configure-visualization-image-server! [vs]
  (let [dash (float-array [10])
        sk (ConstantTransformer. (BasicStroke. 1 BasicStroke/CAP_BUTT BasicStroke/JOIN_MITER 10 dash 0))]
    (.. vs getRenderer getVertexLabelRenderer (setPosition Renderer$VertexLabel$Position/CNTR))
    (doto (.getRenderContext vs)
      (.setVertexFillPaintTransformer (ConstantTransformer. Color/YELLOW))
      (.setVertexShapeTransformer (ConstantTransformer. (Ellipse2D$Double. -24 -24 48 48)))
      (.setEdgeStrokeTransformer sk)
      (.setEdgeArrowStrokeTransformer sk)
      (.setVertexLabelTransformer (ToStringLabeller.))
      (.setEdgeLabelTransformer (ToStringLabeller.)))))

(defn- save-graph-as-jpeg [graph file-name]
  (let [lt (CircleLayout. graph)
        vs (VisualizationImageServer. lt preferred-image-dimension)]
    (configure-visualization-image-server! vs)
    (let [img (.getImage vs (Point2D$Double. 0 0) preferred-image-dimension)]
      (ImageIO/write img "jpeg" (File. file-name)))))

(defn- probe-jung []
  (let [g (SparseGraph.)]
    (doto g
      (.addVertex 1)
      (.addVertex 2)
      (.addVertex 5)
      (.addEdge "Edge A" 1 2 EdgeType/DIRECTED)
      (.addEdge "Edge B" 2 5 EdgeType/DIRECTED))
    (println g)
    (save-graph-as-jpeg g "testo.jpeg")))

; This test is but a placeholder.
(deftest dotify-nodes-test
  (probe-jung)
  ;(dot>png (dotify-graph wizard-graph)) ; <-- Generates image but uses native shell.
  (is (dotify-identifier "hello$%df") "hello__df"))
