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

(s/defn find-task-by-id :- schema.model/Task
  [task-id :- s/Uuid
   db]
  (ffirst (d/q '[:find (pull ?t [*])
                 :in $ ?id
                 :where [?t :task/id ?id]] db task-id)))

(s/defn find-all-tasks :- schema.model/Tasks
  [db]
  (->> db 
       (d/q '[:find (pull ?t [*])
                 :where [?t :task/id _]])
       (map first)))

(s/defn update-task :- schema.model/Task
  [task :- schema.model/Task
   datomic]
  (d/transact datomic [task])
  task)

(s/defn delete-task
  [task-id :- s/Int
   datomic]
  (d/transact
   datomic
   [[:db.fn/retractEntity task-id]]))
