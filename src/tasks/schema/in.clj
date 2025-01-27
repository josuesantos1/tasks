(ns tasks.schema.in 
  (:require
   [schema.core :as s]))

(s/defschema Task 
  {:title s/Str
   (s/optional-key :description) s/Str})
