(ns app.rn.navigation
  (:require ["@react-navigation/stack" :refer [createStackNavigator]]
            [oops.core :refer [oget ocall]]))

(def create-stack-navigator createStackNavigator)

(defn screen [stack]
  (oget stack :Screen))

(defn navigator [stack]
  (oget stack :Navigator))

(defn navigate
  ([navigation target]
   (ocall navigation :navigate target))
  ([navigation target params]
   (ocall navigation :navigate target params)))