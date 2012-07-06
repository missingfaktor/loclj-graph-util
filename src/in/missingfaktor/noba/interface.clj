(ns in.missingfaktor.noba.interface
  "Interfacing with the outside world."
  (:use [clojure.java.io]))

(defn- shell [cmd]
  (.exec (Runtime/getRuntime) cmd))

; BAD. Hardcoded stuff.
; Use JUNG/Grappa.
(defn dot>png [dot]
  (with-open [wrtr (writer "test.dot")]
    (.write wrtr dot))
  (shell "\"C:\\Program Files\\GraphViz 2.28\\bin\\dot.exe\" -O -Tpng test.dot"))