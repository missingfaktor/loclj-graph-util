(ns in.missingfaktor.noba.game-data
  (:import [in.missingfaktor.noba.graph Node Edge]))

(def wizard-nodes [(Node. :living-room "Living room")
                   (Node. :garden "Garden")
                   (Node. :attic "Attic")])

(def wizard-edges [(Edge. :living-room :garden :west :door )
                   (Edge. :living-room :attic :upstairs :ladder )
                   (Edge. :attic :living-room :east :door )
                   (Edge. :attic :living-room :downstairs :ladder )])

