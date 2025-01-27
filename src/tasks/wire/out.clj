(ns tasks.wire.out 
  (:require
   [schema.core :as s]))

(s/defschema Task
  {(s/required-key :id)              s/Uuid
   :title             s/Str
   (s/optional-key :description) s/Str
   :status            s/Str})

(s/defschema Tasks
  [Task])
