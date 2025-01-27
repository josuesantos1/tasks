(ns tasks.schema
  (:require [schema.core :as s]))

(s/def Status 
  (s/enum :task/todo :task/closed :title/opened :task/done))

(s/defschema Tasks
  {(s/required-key :task/id)          s/Uuid 
   (s/required-key :task/title)       s/Str 
   (s/optional-key :task/description) s/Str
   (s/required-key :task/status)      Status})
