(ns app.ui.components.screen-container
  (:require ["react-native" :refer [View]]
            [app.lib :refer [$ defnc]]
            [app.tailwind :refer [tw]]))

(defnc ScreenContainer
  [{:keys [children style]}]
  ($ View {:style [(tw :h-full) style]} children))