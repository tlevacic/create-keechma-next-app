(ns app.controllers.router
  (:require [keechma.next.controller :as ctrl]
            [goog.object :as gobj]))

(derive :router :keechma/controller)

(defonce router-state* (atom nil))

(defn collect-routes
  ([node] (collect-routes node {} []))
  ([node state] (collect-routes node state []))
  ([node state path]
   (if node
     (let [routes (gobj/get node "routes")
           active-index (gobj/get node "index")
           active-key (gobj/getValueByKeys node "routes" active-index "key")]
       (if routes
         (reduce
           (fn [acc route]
             (let [name       (keyword (gobj/get route "name"))
                   key        (gobj/get route "key")
                   params     (js->clj (gobj/get route "params") :keywordize-keys true)
                   route-path (conj path name)
                   acc'       (assoc acc route-path {:name name :params params :is-active (= key active-key)})]
               (collect-routes (gobj/get route "state") acc' route-path)))
           state
           routes)
         state))
     state)))

(defn get-routes [payload]
  (println (with-out-str (cljs.pprint/pprint (collect-routes payload))))
  (collect-routes payload))


(defmethod ctrl/start :router [_ _ _ _]
  @router-state*)

(defmethod ctrl/handle :router [{:keys [state*]} cmd payload]
  (case cmd
    :route-change
    (let [routes (get-routes payload)]
      (reset! router-state* routes)
      (reset! state* routes))
    nil))
