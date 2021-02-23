(ns app.workspaces.component-cards
  (:require [nubank.workspaces.core :as ws]
            [nubank.workspaces.model :as wsm]
            [nubank.workspaces.card-types.react :refer [react-card]]
            [helix.dom :as d]
            ["@testing-library/react" :refer [render cleanup fireEvent]]
            [helix.core :as hx :refer [$ <>]]
            [keechma.next.helix.lib :refer [defnc]]
            [helix.hooks :as hooks]
            [app.ui.components.hello :refer [Hello click-counter]]
            [cljs.test :refer [is]]))

(defn testing-container
  "The container that should be used to render testing-library react components.
  We want to provide our own container so that the rendered cards aren't used."
  []
  (let [app-div (js/document.createElement "div")]
    (.setAttribute app-div "id" "testing-lib")
    (js/document.body.appendChild app-div)))

(ws/defcard button-teal
            {::wsm/card-width 2, ::wsm/card-height 3}
            (react-card (d/button {:class "btn btn-teal"} " Button ")))

(defnc ClickCounterRenderer []
  (let [[count set-count] (hooks/use-state 0)]
    (<> (click-counter count set-count))))

(ws/deftest hello-tests-card
  (let [tr (render ($ Hello) #js {:container (testing-container)})]
    (is (.queryByText tr #"Hello") "Should say 'Hello'")
    (cleanup)))

(ws/deftest click-counter-tests-card
  (let [element ($ ClickCounterRenderer)
        tr (render element #js {:container (testing-container)})]
    (is (.queryByText tr #"has value: 0")
        "Should show the initial value as '0'")
    (.click fireEvent (.queryByText tr #"(?i)click me"))
    (is (.queryByText tr #"has value: 1")
        "Should show the value as '1' after click")
    (.click fireEvent (.queryByText tr #"(?i)click me"))
    (is (.queryByText tr #"has value: 2")
        "Should show the value as '2' after two clicks")
    (cleanup)))