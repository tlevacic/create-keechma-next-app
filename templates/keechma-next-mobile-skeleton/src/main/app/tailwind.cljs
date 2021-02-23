(ns app.tailwind
  (:require ["tailwind-rn" :as tailwind]
            [clojure.string :as str]))

(def tw-styles (js/require "../styles.json"))

(let [exported (tailwind/create tw-styles)]
  (def tw* (.-tailwind exported))
  (def get-color* (.-getColor exported))

  (defn get-color [color-name]
    (get-color* (name color-name)))

  (defn tw [& args]
    (->> args
         flatten
         (filter identity)
         (map (fn [c] (name c)))
         (str/join " ")
         tw*)))