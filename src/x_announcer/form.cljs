(ns x-announcer.form
  (:require [crate.core :as crate]
            [goog.dom :as gdom]
            [x-announcer.store :as store])
  (:import [goog.events EventType]))

(def component-id "x-announcer-form")

(defn- form [selection]
  [:div.overlay
   {:id component-id}
   [:form
    [:a.close-button
     {:style {
              :float "right"
              }}
     "close"]
    [:h3 "Announcer"]
    [:div.row
     [:label "Selection:"]
     [:textarea {:class    "form-control"
                 :name     "selection"
                 :readOnly true}
      selection]]
    [:div.row
     [:label "Issue:"]
     [:textarea {:class       "form-control"
                 :name        "issue"
                 :placeholder "here write your issue"}]]
    [:div.row
     [:button {:class "btn btn-primary"} "Submit"]]]])


(defn- get-form []
  (gdom/getElement component-id))

(defn- visible? []
  (boolean (get-form)))

(defn- hide-form []
  (if-let [form-element (get-form)]
    (gdom/removeNode form-element)))

(defn- on-close-click []
  (hide-form))

(defn- on-submit [event]
  (.preventDefault event)
  (.stopPropagation event)

  (let [form (.-target event)
        data {
              :url       js/window.location.href
              :selection (.-value (.querySelector form "[name=selection]"))
              :issue     (.-value (.querySelector form "[name=issue]"))
              }]

    (store/save data)
    (hide-form)))

(defn- show-form [parentElement]
  (let
    [selection (.getSelection js/window)
     component-element (crate/html (form selection))
     form-element (.querySelector component-element "form" component-element)
     close-button (.querySelector component-element ".close-button" component-element)]

    (.addEventListener form-element EventType.SUBMIT on-submit)
    (.addEventListener close-button EventType.CLICK on-close-click)
    (gdom/appendChild parentElement component-element)))

(defn toggle [parentElement]
  (if (visible?)
    (hide-form)
    (show-form parentElement)))
