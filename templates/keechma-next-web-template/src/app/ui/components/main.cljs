(ns app.ui.components.main
  (:require [helix.dom :as d]
            [keechma.next.helix.core :refer [with-keechma]]
            [keechma.next.helix.classified :refer [defclassified]]
            [helix.core :as hx :refer [$]]
            [keechma.next.helix.lib :refer [defnc]]))

(defclassified MainWrap :div "m2")

(defnc MainRenderer [_] ($ MainWrap (d/div "Foo") (d/div "Baz")))

(def Main (with-keechma MainRenderer))