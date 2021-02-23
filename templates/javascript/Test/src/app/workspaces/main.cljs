(ns app.workspaces.main
  (:require [nubank.workspaces.core :as ws]
            [app.workspaces.component-cards]))

(defonce init (ws/mount))