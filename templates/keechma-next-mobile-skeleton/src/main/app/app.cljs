(ns app.app
  (:require ["expo" :as ex]
            ["react" :as react]
            [shadow.expo :as expo]
            ["react-native-safe-area-context" :refer [SafeAreaProvider]]
            ["react-native-screens" :refer [enableScreens]]
            [app.main :refer [Root]]
            [app.lib :refer [defnc $]]
            [keechma.next.core :as keechma]
            [keechma.next.helix.core :refer [KeechmaRoot]]
            [app.keechma-app :refer [app]]))

(enableScreens)

(defonce app-instance* (atom nil))

(defnc App [_]
  ($ SafeAreaProvider
     ($ Root)))

(defn reload
  {:dev/after-load true}
  []
  (when-let [app-instance @app-instance*]
    (keechma/stop! app-instance))
  (let [app-instance (keechma/start! app)]
    (reset! app-instance* app-instance)
    (expo/render-root ($ KeechmaRoot {:keechma/app app-instance} ($ App)))))

(defn init []
  (reload))
