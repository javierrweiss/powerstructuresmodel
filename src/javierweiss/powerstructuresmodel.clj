(ns javierweiss.powerstructuresmodel
  (:require [nextjournal.clerk :as clerk]
            [clojure.data.csv :as csv]
            [tablecloth.api :as tc]
            [tech.v3.datatype.functional :as dfn])
  (:gen-class))

^:clerk/hide
(comment
  (clerk/serve! {:browse? true :watch-paths ["src"]})
  (clerk/show! "src/javierweiss/powerstructuresmodel.clj"))

(def experiment-1 "resources/power structure emergence model experimento-test-spreadsheet.csv")

(defn read-data
  "Receives a string representing a file path and returns vectors of string"
  [file]
  (->> file
      slurp
      csv/read-csv
      (drop 6)))

(defn to-clerk-table
  [str]
   (-> str 
       clerk/use-headers
       clerk/table))

(def data-exp-1 (read-data experiment-1))

(to-clerk-table data-exp-1)

(take 10 (to-clerk-table data-exp-1))

(def ds (tc/dataset experiment-1))
(tc/select-rows ds (vec (range 12 19)))




(defn -main
  "I don't do a whole lot ... yet."
  []
  {:name "X"})

;; TODO  
;;1. Crear un experimento con nuestro modelo y crear un dataset para explorarlo 
;; esta notebook. 
;;2. Bajar el dataset y explorarlo.
;;3. Crear algunas visualizaciones.
