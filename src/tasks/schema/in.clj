(ns tasks.schema.in 
  (:require
   [schema.core :as s]))

(s/def Status
  (s/enum "todo" "closed" "doing" "done"))

(s/defschema Task 
  {:title s/Str
   (s/optional-key :description) s/Str})

(s/defschema UpdateTask
  {(s/optional-key :title)       s/Str
   (s/optional-key :description) s/Str
   (s/optional-key :status)      Status})
