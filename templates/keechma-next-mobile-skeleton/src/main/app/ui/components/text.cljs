(ns app.ui.components.text
  (:require
    ["react" :as react]
    ["react-native" :refer [Text Animated]]
    [app.lib :refer [defnc $]]
    [app.tailwind :refer [get-color]]
    [oops.core :refer [oget]]))

(def AnimatedText (oget Animated :Text))

(defnc P [{:keys [children style]}]
  ($ Text
     {:style [{:line-height 20
               :font-size 20
               :color (get-color :gray-text)}
              style]}
     children))

(defnc H2 [{:keys [children style]}]
  ($ Text
     {:style [{:line-height 46
               :font-size 38
               :color "white"}
              style]}
     children))
