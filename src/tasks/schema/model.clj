(ns tasks.schema.model
  (:require
   [schema.core :as s]))

(s/def Status
  (s/enum :task.status/todo :task.status/closed :task.status/doing :task.status/done))

(s/defschema Task
  {(s/required-key :task/id)          s/Uuid
   (s/required-key :task/title)       s/Str
   (s/optional-key :task/description) s/Str
   (s/required-key :task/status)      Status})

(s/defschema Tasks
  [Task])
