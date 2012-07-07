(ns in.missingfaktor.noba.test
  (:use [clojure.test]
        [in.missingfaktor.noba graph imagifier game-data]))

; placeholder
(deftest imagifier-test
  (save-graph-as-jpeg wizard-graph "testo.jpeg")
  (is true true))
