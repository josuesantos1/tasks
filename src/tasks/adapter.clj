(ns tasks.adapter 
  (:require
   [schema.core :as s]
   [tasks.schema.model :as schema.model]
   [tasks.schema.in :as schema.in]
   [tasks.schema.out :as schema.out]))

(s/defn in->schema :- schema.model/Task
  [{:keys [title description]} :- schema.in/Task]
  {:id          (random-uuid)
   :title       title 
   :description description
   :status      :task/todo})

(s/defn schema->out :- schema.out/Task
  [{:task/keys [id title description status]} :- schema.model/Task]
  {:id          id
   :title       title
   :description description
   :status      (name status)})

(s/defn schemas->out :- schema.out/Tasks
  [tasks :- schema.model/Tasks]
  (map schema->out tasks))
