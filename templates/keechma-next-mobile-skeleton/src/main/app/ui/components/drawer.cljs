(ns app.ui.components.drawer
  (:require ["react-native" :refer [View TouchableOpacity]]
            [app.lib :refer [$ defnc]]
            [app.tailwind :refer [tw]]
            [app.rn.navigation :refer [navigate]]
            [app.hooks :refer [use-safe-area]]
            [app.ui.components.text :as text]))

(defnc Drawer [{:keys [navigation]}]
  (let [insets (use-safe-area)]
    ($ View {:style [{:padding-top (:top insets)} (tw :px-4)]}
       ($ TouchableOpacity
          {:onPress #(navigate navigation "splash")
           :style (tw :py-4)}
          ($ text/P "Home")))))
