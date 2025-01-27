(ns tasks.database.tasks 
  (:require
   [datomic.api :as d]
   [schema.core :as s]
   [tasks.schema.model :as schema.model]))

(s/defn insert :- schema.model/Task
  [task :- schema.model/Task
   datomic]
  (d/transact datomic [task])
  task)

(s/defn find-all-tasks :- schema.model/Tasks
  [db]
  (->> db 
       (d/q '[:find (pull ?t [*])
                 :where [?t :task/id _]])
       (map first)))
