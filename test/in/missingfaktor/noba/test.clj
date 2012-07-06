(ns in.missingfaktor.noba.test
  (:use [clojure.test]
        [in.missingfaktor.noba graph dotifier game-data]))

; This test is but a placeholder.
(deftest dotify-nodes-test
  (is (dotify-identifier "hello$%df") "hello__df"))
