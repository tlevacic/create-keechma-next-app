(ns app.keechma-app
  (:require [app.controllers.router]))

(def app
  {:keechma/controllers {:router #:keechma.controller {:params true}}})
