(ns app.ui.screens.splash
  (:require ["react-native" :refer [View]]
            [app.lib :refer [$ defnc]]
            [app.tailwind :refer [tw]]
            [keechma.next.helix.core :refer [with-keechma]]
            [app.ui.components.screen-container :refer [ScreenContainer]]
            [app.ui.components.text :as text]))

(defnc ScreenRenderer []
  ($ ScreenContainer
     ($ View {:style [(tw :items-center :justify-center :px-5 :w-full :h-full)]}
        ($ text/H2 {:style (tw :text-center)} "Welcome to the Keechma Next!"))))

(def Screen (with-keechma ScreenRenderer))
