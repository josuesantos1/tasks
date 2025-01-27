(ns tasks.controller 
  (:require
   [datomic.api :as d]
   [schema.core :as s]
   [tasks.adapter]
   [tasks.database.tasks :as database.tasks]
   [tasks.schema.in :as schema.in]
   [tasks.schema.out :as schema.out]))

(s/defn create-task :- schema.out/Task
  [task :- schema.in/Task
   datomic]
  (-> task
      tasks.adapter/in->schema
      (database.tasks/insert datomic)))

(s/defn all-tasks :- schema.out/Tasks
  [datomic]
  (-> datomic 
      d/db
      database.tasks/find-all-tasks
      tasks.adapter/schemas->out))
