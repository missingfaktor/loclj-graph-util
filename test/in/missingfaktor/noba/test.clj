(ns in.missingfaktor.noba.test
  (:use [clojure.test]
        [in.missingfaktor.noba graph game-data])
  (:require [in.missingfaktor.noba.imagifier :as i]
            [in.missingfaktor.noba.imagifier2 :as i2]))

; placeholder
(deftest imagifier-test
  (i/save-graph-as-png wizard-graph "testo1.png")
  (i2/save-graph-as-png wizard-graph "testo2.png")
  (is true true))
