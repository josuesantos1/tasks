(ns tasks.core
  (:gen-class)
  (:require
   [muuntaja.core :as m]
   [reitit.coercion.malli]
   [reitit.coercion.spec]
   [reitit.dev.pretty :as pretty]
   [reitit.openapi :as openapi]
   [reitit.ring :as ring]
   [reitit.ring.coercion :as coercion]
   [reitit.ring.middleware.exception :as exception]
   [reitit.ring.middleware.muuntaja :as muuntaja]
   [reitit.ring.middleware.parameters :as parameters]
   [reitit.swagger :as swagger]
   [reitit.swagger-ui :as swagger-ui]
   [ring.adapter.jetty :as ring.jetty]
   [schema.core :as s]
   [tasks.wire.out :as wire.out]))

(defonce server (atom nil))

(def app
  (ring/ring-handler
   (ring/router
    [["/swagger.json"
      {:get {:no-doc true
             :swagger {:info {:title "task api"}}
             :handler (swagger/create-swagger-handler)}}]
     ["/openapi.json"
      {:get {:no-doc true
             :openapi {:info {:title "task-api"
                              :description "openapi docs"
                              :version "0.0.1"}}
             :handler (openapi/create-openapi-handler)}}]
     ["/tasks"
      {:tags ["tasks"]}
      [""
       {:get {:summary "return all tasks"}
        :handler (fn [_]
                   {:status 200
                    :body "tasks"})}]]]
    {:exception pretty/exception
     :data {:muuntaja m/instance
            :middleware [swagger/swagger-feature
                         parameters/parameters-middleware
                         muuntaja/format-negotiate-middleware
                         muuntaja/format-response-middleware
                         exception/exception-middleware
                         muuntaja/format-request-middleware
                         coercion/coerce-response-middleware
                         coercion/coerce-request-middleware]}})
   (ring/routes
    (swagger-ui/create-swagger-ui-handler
     {:path "/"
      :config {:validatorUrl nil
               :urls [{:name "swagger" :url "swagger.json"}
                      {:name "openapi" :url "openapi.json"}]
               :urls.primaryName "openapi"
               :operationsSorter "alpha"}})
    (ring/create-default-handler))))


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
