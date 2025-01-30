(ns tasks.adapter-test
  (:require
   [clojure.test :refer [deftest is testing]]
   [matcher-combinators.test :refer [match?]]
   [tasks.adapter]))

(def id (random-uuid))
(def title "title")
(def title-updated "title updated")
(def description "description")
(def status "todo")

(def in-task
  {:title title
   :description description})

(def model-task
  {:task/id id
   :task/title title
   :task/description description
   :task/status status})

(def in-update-task
  {:title title-updated
   :description description})

(deftest in->model-test!
  (testing "Should return valid task model data"
    (is (match?
         {:task/id uuid?
          :task/title title
          :task/description description
          :task/status :task.status/todo}
         (tasks.adapter/in->model in-task)))))

(deftest model->out-test!
  (testing "Should return valid task schema out data"
    (is (match?
         {:id id
          :title title
          :description description
          :status status}
         (tasks.adapter/model->out model-task)))))

(deftest in-update->model-test!
  (testing "Should return valid task model data"
    (is (match?
         {:task/id id
          :task/title title-updated}
         (tasks.adapter/in-update->model in-update-task id)))))

(deftest models->out-test!
  (testing "Should return valid tasks schema out data"
    (is (match?
         [{:id id
           :title title
           :description description
           :status status}]
         (tasks.adapter/models->out [model-task])))))
