(ns app.hooks
  (:require ["react-native-safe-area-context" :refer [useSafeArea]]
            ["@rnhooks/dimensions" :refer [default] :rename {default useDimensions}]
            [cljs-bean.core :refer [->clj]]))

(defn use-safe-area []
  (->clj (useSafeArea)))

(defn use-dimensions
  ([] (use-dimensions "window"))
  ([dimension-type]
   (->clj (useDimensions (name dimension-type)))))
