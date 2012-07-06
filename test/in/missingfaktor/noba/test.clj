(ns in.missingfaktor.noba.test
  (:use [clojure.test]
        [clojure.java.io]
        [in.missingfaktor.noba graph dotifier game-data interface]))

; This test is but a placeholder.
(deftest dotify-nodes-test
  (dot>png (dotify-graph wizard-graph))
  (is (dotify-identifier "hello$%df") "hello__df"))
