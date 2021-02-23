(ns codox.core
  (:require [codox.main :refer [generate-docs]]))

(defn build-docs
  []
  (generate-docs
   {:name "frontend",
    :version "0.1.0-SNAPSHOT",
    :description "Frontend for the application.",
    :language :clojurescript,
    :source-paths ["src/app/"],
    :output-path "codox",
    :root-path ".",
    :source-uri
    "http://github.com/tiborkr/dev-example-app/blob/{version}/{filepath}#L{line}",
    :metadata {:doc/format :markdown, :doc "FIXME: Write documentation."}}))