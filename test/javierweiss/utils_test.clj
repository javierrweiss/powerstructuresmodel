(ns javierweiss.utils-test
  (:require [clojure.test :refer :all]
            [javierweiss.utils :refer :all]))

(def t1 ";;Esto es un texto de prueba
         ;;Que prueba que la prueba
         ;;esta probada")
(def t2 "(+ 2 3 3)")
(def t3 ";;Aqui hay texto
         ;; y codigo
         (def x 1221)
         (reduce * [1 2 43 23])
         ;;Y mas texto")

(deftest clj-to-md
  (testing "Comments are turned to bare text"
    (is (= "Esto es un texto de prueba\n Que prueba que la prueba\n esta probada" (clj->md t1))))
  (testing "Clojure code is turned into fenced markdown code blocks"
    (is (= "```clojure
                         (+ 2 3 3)
                         ```" (clj->md t2))))
  (testing "Function converts clj to correct markdown"
         (is (= "Aqui hay texto\n y codigo\n
                              ```clojure\n
                              (def x 1221)\n
                              (reduce * [1 2 43 23])\n
                              Y mas texto" (clj->md t3)))))