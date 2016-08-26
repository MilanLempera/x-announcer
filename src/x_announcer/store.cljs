(ns x-announcer.store
  (:require [clojure.string :as str]
            [matchbox.core :as match]
            [goog.string.format]))

(def firebase (atom {}))

(defn firebase-settings! [url path]
  (let [set-new-values (fn []
                         {:url  url
                          :path (str/split path "/")})]

    (swap! firebase set-new-values)))

(defn- get-iso-date []
  (.toISOString (js/Date.)))

(defn- save-with-date [root path data]
  (let [reports (match/get-in root path)
        data (assoc data :date (get-iso-date))]
    (match/conj! reports data)))

(defn save [data]
  (let
    [root (match/connect (:url @firebase))
     path (:path @firebase)]

    (match/auth-anon root)
    (save-with-date root path data)
    (match/unauth root)))