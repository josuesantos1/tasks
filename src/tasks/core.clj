(ns tasks.core
  (:gen-class)
  (:require
   [muuntaja.core :as m]
   [pokeapi.controller]
   [reitit.coercion.malli]
   [reitit.ring :as ring]
   [reitit.ring.middleware.muuntaja :as muuntaja]
   [ring.adapter.jetty :as ring.jetty]))

(defonce server (atom nil))

(def app
  (ring/ring-handler
   (ring/router
    [["/tasks"
      {:get {:handler (fn [_] {:body "task" :status 200})}}]]
    {:data {:muuntaja m/instance
            :middleware [muuntaja/format-middleware]}})))

(defn start []
  (ring.jetty/run-jetty #'app {:port  3000
                               :join? false}))

(defn -main
  [& _]
  (prn "[+] starting server...")
  (reset! server (start)))

(comment
  (def server (start))

  (.stop server))