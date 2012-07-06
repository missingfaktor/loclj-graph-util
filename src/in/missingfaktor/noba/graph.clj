(ns in.missingfaktor.noba.graph)

(defrecord Node [id description])

(defrecord Edge [from to direction via])