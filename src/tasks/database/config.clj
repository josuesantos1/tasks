(ns tasks.database.config
  (:require
   [datomic.api :as d]))

(def uri "datomic:mem://tasks")
(d/create-database uri)
(def datomic (d/connect uri))

(def schema
  [{:db/ident       :task/id
    :db/valueType   :db.type/uuid
    :db/cardinality :db.cardinality/one
    :db/unique      :db.unique/identity}
   {:db/ident       :task/title
    :db/valueType   :db.type/string
    :db/cardinality :db.cardinality/one}
   {:db/ident       :task/description
    :db/valueType   :db.type/string
    :db/cardinality :db.cardinality/one}
   {:db/ident       :task/status
    :db/valueType   :db.type/keyword
    :db/cardinality :db.cardinality/one}
   {:db/ident :task.status/todo}
   {:db/ident :task.status/closed}
   {:db/ident :task.status/doing}
   {:db/ident :task.status/done}])

(defn create-schema [] (d/transact datomic schema))
