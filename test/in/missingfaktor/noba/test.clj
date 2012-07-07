(ns in.missingfaktor.noba.test
  (:use [clojure.test]
        [in.missingfaktor.noba graph imagifier imagifier2 game-data]))

; placeholder
(deftest imagifier-test
  (save-graph-as-jpeg wizard-graph "testo.jpeg")
  (save-graph-as-png wizard-graph "testo.png")
  (is true true))
