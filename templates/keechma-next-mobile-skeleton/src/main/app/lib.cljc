(ns app.lib
  #?(:clj (:require [helix.core :as helix]
                    [helix.impl.analyzer :as hana]
                    [helix.impl.props :as impl.props]))
  #?(:cljs (:require-macros [app.lib]))
  #?(:cljs (:require ["react" :as react]
                     [camel-snake-kebab.core :refer [->camelCaseString]]
                     [camel-snake-kebab.extras :refer [transform-keys]]
                     [oops.core :refer [oget oset!]])))

#?(:cljs
   (defn ^js/React get-react [] react))

#?(:clj
   (defmacro defnc [type params & body]
     (let [opts? (map? (first body)) ;; whether an opts map was passed in
           opts (if opts?
                  (first body)
                  {})
           body (if opts?
                  (rest body)
                  body)
           ;; feature flags to enable by default
           default-opts {:helix/features {:fast-refresh true}}]
       `(helix.core/defnc ~type ~params
          ;; we use `merge` here to allow indidivual consumers to override feature
          ;; flags in special cases
          ~(merge default-opts opts)
          ~@body))))

#?(:cljs
   (defn convert-style [m]
     (let [style (oget m :?style)]
       (if style
         (let [converted (clj->js (transform-keys ->camelCaseString style))]
           (oset! m :!style converted)
           m)
         m))))

#?(:clj
   (defmacro $
     "Create a new React element from a valid React type.
     Will try to statically convert props to a JS object. Falls back to `$$` when
     ambiguous.
     Example:
     ```
     ($ MyComponent
      \"child1\"
      ($ \"span\"
        {:style {:color \"green\"}}
        \"child2\" ))
     ```"
     [type & args]
     (when (and (symbol? (first args))
                (= (hana/inferred-type &env (first args))
                   'cljs.core/IMap))
       (hana/warn hana/warning-inferred-map-props
                  &env
                  {:form (cons type args)
                   :props-form (first args)}))
     (let [inferred (hana/inferred-type &env type)
           native? (or (keyword? type)
                       (string? type)
                       (= inferred 'string)
                       (= inferred 'cljs.core/Keyword)
                       (:native (meta type)))
           type (if (keyword? type)
                  (name type)
                  type)]
       (cond
         (map? (first args))
         `^js/React.Element (.createElement
                              (get-react)
                              ~type
                              ~(if native?
                                 `(impl.props/native-props ~(first args))
                                 `(convert-style ~`(impl.props/props ~(first args))))
                              ~@(rest args))

         :else `^js/React.Element (.createElement (get-react) ~type nil ~@args)))))