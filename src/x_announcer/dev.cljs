(ns x-announcer.dev
  (:require [x-announcer.element]))

(enable-console-print!)

(defn on-js-reload []
  (.reload js/location)
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
  )