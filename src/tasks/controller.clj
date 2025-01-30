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
      tasks.adapter/in->model
      (database.tasks/insert datomic)
      tasks.adapter/model->out))

(s/defn all-tasks :- schema.out/Tasks
  [datomic]
  (some-> datomic
          d/db
          database.tasks/find-all-tasks
          tasks.adapter/models->out))

(s/defn get-tasks-by-id :- schema.out/Tasks
  [task-id :- s/Uuid
   datomic]
  (let [task (database.tasks/find-by-id task-id (d/db datomic))]
    (if (:task/id task)
      {:status 200
       :body   (tasks.adapter/model->out task)}
      {:status 404
       :body   {:error "Not Found"}})))

(s/defn update->task :- schema.out/Task
  [task-id :- s/Uuid
   task :- schema.in/Task
   datomic]
  (let [{id :db/id} (database.tasks/find-by-id task-id (d/db datomic))]
    (if id
      {:status 200
       :body   (-> task
                   (tasks.adapter/in-update->model task-id)
                   (database.tasks/update-task datomic)
                   (tasks.adapter/model->out))}
      {:body   {:error "Not Found"}
       :status 404})))

(s/defn delete-task :- s/Str
  [task-id :- s/Uuid
   datomic]
  (let [{id :db/id} (database.tasks/find-by-id task-id (d/db datomic))]
    (if id
      (do (database.tasks/delete-task id datomic)
          {:body   {:message "Deleted"}
           :status 200})
      {:body   {:error "Not Found"}
       :status 404})))
