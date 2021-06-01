(ns schnick-schnack-schnuck.core
  (:require [org.httpkit.server :as server]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [clojure.data.json :as json]
            [clojure.java.io :as io])
  (:gen-class)
  (:import (java.io PushbackReader)))

(def rps-values ["ROCK" "PAPER" "SCISSORS"])


(defn config []
  (-> (io/resource (str "config.edn"))
      (io/reader)
      (PushbackReader.)
      (read)))


(defn rand-value-from [value-list]
  (->> (count value-list)
       (rand-int)
       (nth value-list)))

(defn dummy-request [req]
  {:status  200
   :headers {"Content-Type" "application/json"}
   :body    (json/write-str {:computer-hand (rand-value-from rps-values)})} )

(defroutes app-routes
           (GET "/" [] dummy-request)
           (route/not-found {:status 404}))

(defn -main [& args]
  (let [config (config)
        port   (Integer/parseInt (or (System/getenv "PORT") (:default-port config)))]

    (server/run-server #'app-routes {:port port})
    (println (str "Running webserver at http:/127.0.0.1:" port "/"))))