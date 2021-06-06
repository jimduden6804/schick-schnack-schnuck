(ns schnick-schnack-schnuck.core
  (:require [org.httpkit.server :as server]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer :all]
            [clojure.data.json :as json]
            [clojure.java.io :as io])
  (:gen-class)
  (:import (java.io PushbackReader)
           (java.util List)))

(def config
  (-> (io/resource (str "config.edn"))
      (io/reader)
      (PushbackReader.)
      (read)))

(defn rand-value-from [value-list]
  (->> (count value-list)
       (rand-int)
       (nth value-list)))

(defn extract-json-data [request]
  (-> (:body request)
      (io/reader :encoding "UTF-8")
      (json/read :key-fn keyword)))

(defn beats? [hand computer-hand game-beating-map]
  (-> ^List (get game-beating-map hand)
      (.contains computer-hand)))

(defn calc-result [{:keys [hand computer-hand] :as input-map} game-beating-map]
  (->> (cond
         (= hand computer-hand) "DRAW"
         (beats? hand computer-hand game-beating-map) "WON"
         :else "LOST")
       (assoc input-map :result)))

(defn build-response [result]
  {:status  200
   :headers {"Content-Type" "application/json"}
   :body    (json/write-str result)})

(defn validate-hand [{:keys [hand] :as body} game-values]
  (when (.contains game-values hand)
    body))

(def unknown-response
  {:status  200
     :headers {"Content-Type" "application/json"}
     :body    (json/write-str {:result "UNKNOWN"})})

(defn game-handler [game-conf request]
  (or (some-> (extract-json-data request)
              (validate-hand (:values game-conf))
              (assoc :computer-hand (rand-value-from (:values game-conf)))
              (calc-result (:beating game-conf))
              (build-response))
      unknown-response))

(defroutes app-routes
           (POST "/game" [] (partial game-handler (:rock-paper-scissors (:games config))))
           (POST "/rpsls" [] (partial game-handler (:rock-paper-scissors-lizard-spock (:games config))))
           (route/not-found {:status 404}))

(defn middleware-config []
  (-> site-defaults
      (assoc-in [:security :anti-forgery] false)))

(defn -main [& args]
  (let [port   (Integer/parseInt (or (System/getenv "PORT") (:default-port config)))]

    (println (middleware-config))
    (server/run-server (wrap-defaults #'app-routes (middleware-config)) {:port port})
    (println (str "Running webserver at http:/127.0.0.1:" port "/"))))