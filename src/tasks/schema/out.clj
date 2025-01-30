(ns tasks.schema.out
  (:require
   [schema.core :as s]))

(s/defschema Task
  {(s/required-key :id)          s/Uuid
   (s/required-key :title)       s/Str
   (s/optional-key :description) s/Str
   (s/required-key :status)      s/Str})

(s/defschema Tasks
  [Task])
