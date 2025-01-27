(ns tasks.adapter 
  (:require
    [schema.core :as s]
   [tasks.schema.model :as schema.model]
   [tasks.wire.in :as wire.in]))

(s/defn in->schema :- schema.model/Task
  [{:keys [title description]} :- wire.in/Task]
  {:id          (random-uuid)
   :title       title 
   :description description
   :status      :task/todo})
