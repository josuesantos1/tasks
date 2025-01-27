(ns tasks.controller 
  (:require
   [schema.core :as s]
   [tasks.adapter]
   [tasks.database.tasks :as database.tasks]
   [tasks.wire.in :as wire.in]))

(s/defn create-task
  [task :- wire.in/Task
   datomic]
  (-> task
      tasks.adapter/in->schema
      (database.tasks/insert datomic)))
