(ns x-announcer.store
  (:require [matchbox.core :as match]
            [goog.string :as gstring]
            [goog.string.format]))

(def base-uri (atom ""))

(defn set-url [url]
  (reset! base-uri url))

(defn- get-date []
  (let [now (js/Date.)]
    (gstring/format "%4d-%02d-%02d",
                    (.getFullYear now)
                    (.getUTCMonth now)
                    (.getDate now))))

(defn save [data]
  (let
    [root (match/connect @base-uri)
     date (get-date)]

    (match/auth-anon root)

    (def today-reports (match/get-in root [:reports date]))
    (match/conj! today-reports data)

    (match/unauth root)))