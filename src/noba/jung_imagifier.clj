(ns noba.jung-imagifier
  "This module contains functions for generating an image from a given graph."
  (:use [noba.data-structures :only [fold-orientation]])
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

(defn- graph>jung-graph [orientation {:keys [nodes edges]}]
  (let [jung-graph (SparseGraph.)
        edge-type (fold-orientation EdgeType/DIRECTED EdgeType/UNDIRECTED orientation)]
    (doseq [node nodes]
      (.addVertex jung-graph (:id node)))
    (doseq [{:keys [from to direction via]} edges]
      (.addEdge jung-graph (str direction via) from to edge-type))
    jung-graph))

(defn- save-jung-graph-as-png [file-name jung-graph]
  (let [lt (CircleLayout. jung-graph)
        vs (VisualizationImageServer. lt preferred-image-dimension)]
    (configure-visualization-image-server! vs)
    (let [img (.getImage vs (Point2D$Double. 0 0) preferred-image-dimension)]
      (ImageIO/write img "png" (File. file-name)))))

(defn save-graph-as-png [orientation file-name graph]
  (save-jung-graph-as-png file-name (graph>jung-graph orientation graph)))
