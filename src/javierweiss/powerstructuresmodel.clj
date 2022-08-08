(ns javierweiss.powerstructuresmodel
  (:require [nextjournal.clerk :as clerk])
  (:gen-class))

(clerk/serve!{:browse? true})
(clerk/show! "src/javierweiss/powerstructuresmodel.clj")

(defn greet
  "Callable entry point to the application."
  [data]
  (println (str "Hello, " (or (:name data) "World") "!")))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (greet {:name (first args)}))
