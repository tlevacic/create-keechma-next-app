(ns app.ui.components.hello
  (:require [helix.dom :as d]
            [helix.core :as hx :refer [<>]]
            [helix.hooks :as hooks]
            [keechma.next.helix.lib :refer [defnc]]))

(defn click-counter
  [count set-count]
  (d/div "The atom " (d/code "click-count")
         " has value: " count
         ". " (d/input {:type "button",
                        :value "Click me!",
                        :on-click #(set-count inc count)})))

(defnc Hello []
  (let [[count set-count] (hooks/use-state 0)]
    (<> (d/p "Hello, my-app is running!")
        (d/p "Here's an example of using a component with state:")
        (click-counter count set-count))))
