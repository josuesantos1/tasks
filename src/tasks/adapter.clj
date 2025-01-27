(ns tasks.adapter 
  (:require
   [schema.core :as s]
   [tasks.schema.model :as schema.model]
   [tasks.schema.in :as schema.in]
   [tasks.schema.out :as schema.out]))

(s/defn in->model :- schema.model/Task
  [{:keys [title description]} :- schema.in/Task]
  {:task/id          (random-uuid)
   :task/title       title 
   :task/description description
   :task/status      :task.status/todo})

(s/defn model->out :- schema.out/Task
  [{:task/keys [id title description status]} :- schema.model/Task]
  {:id          id
   :title       title
   :description description
   :status      (name status)})

(s/defn models->out :- schema.out/Tasks
  [tasks :- schema.model/Tasks]
  (map model->out tasks))
