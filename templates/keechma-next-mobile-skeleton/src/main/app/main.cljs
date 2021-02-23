(ns app.main
  (:require [keechma.next.helix.core :refer [with-keechma dispatch]]
            ["@react-navigation/native" :refer [NavigationContainer]]
            ["@react-navigation/drawer" :refer [createDrawerNavigator]]
            [app.lib :refer [defnc $]]
            [app.tailwind :refer [get-color]]
            [app.ui.screens.splash :as splash]
            [app.ui.components.drawer :refer [Drawer]]
            [app.rn.navigation :refer [create-stack-navigator screen navigator]]))

(def main-stack (create-stack-navigator))
(def drawer-navigator (createDrawerNavigator))

(def theme
  {:dark true
   :colors {:primary "white"
            :background (get-color :black)
            :card (get-color :gray-dark)
            :text "white"
            :border (get-color :gray-dark)}})

(defnc Main []
  ($ (navigator main-stack)
     {:initialRouteName "splash"}
     ($ (screen main-stack)
        {:name "splash"
         :component splash/Screen
         :options #js{:headerTitle ""
                      :headerShown false}})))

(defonce router-state (atom nil))

(defnc RootRenderer [props]
  ($ NavigationContainer
     {:onStateChange (fn [state]
                       (reset! router-state state)
                       (dispatch props :router :route-change state))
      :initialState @router-state
      :theme (clj->js theme)}
     ($ (navigator drawer-navigator)
        {:drawerType "slide"
         :drawerContent (fn [props] ($ Drawer {& props}))}
        ($ (screen drawer-navigator)
           {:name "main"
            :component Main}))))

(def Root (with-keechma RootRenderer))