(ns javierweiss.utils
  (:require [clojure.string :as s]
            [clojure.java.io :as io]))

(defn file->md
  "Given a clj file as an input turns symbolic expressions and clojure comments to markdown"
  [file path]
  (letfn [(remove-comment-tokens [st]
            (s/replace st #";;" ""))
          (count-parens [strs]
            (-> {:opening-parens 0
                 :closing-parens 0}
                (update  :opening-parens + (->> (char-array strs)
                                                (filter #(= % \())
                                                count))
                (update  :closing-parens + (->> (char-array strs)
                                                (filter #(= % \)))
                                                count))))]
    (loop [opening 0
           closing 0
           lines (with-open [rdr (io/reader file)]
                   (into [] (line-seq rdr)))
           st ""]
      (if (empty? lines)
        (spit (str path ".md") st)
        (let [f (first lines)
              op (.contains f "(")
              clo (.contains f ")")
              {:keys [opening-parens closing-parens]} (count-parens f)
              parens-match? (== (+ opening-parens opening) (+ closing-parens closing))]
          (recur (cond
                   op (+ opening-parens opening)
                   parens-match? 0
                   :else opening)
                 (cond
                   clo (+ closing-parens closing)
                   parens-match? 0
                   :else closing)
                 (rest lines)
                 (cond
                   (.contains f ";;") (str st (remove-comment-tokens f) "\n")
                   (and op clo (== opening-parens closing-parens)) (str st " ```clojure " f " ``` \n")
                   (and op (== (+ opening opening-parens) 1)) (str st " ```clojure " f)
                   (and (not= opening 0) parens-match?)  (str st f " ``` \n")
                   (> (+ opening opening-parens) 0) (str st f "\n")
                   :else st))))))) 
  
(comment

  (def t1 ";;RegExr was created by gskinner.com, and is proudly hosted by Media Temple.

(defn- clj->md
  [st]
  (let [sq (line-seq (BufferedReader. (StringReader. st)))]
    (for [ln sq] (-> ln
                     code->md
                     remove-comment-tokens))))

  ;;Edit the Expression & Text to see matches. Roll over matches or the expression for details. PCRE & JavaScript 
  ;;flavors of RegEx are supported. Validate your expression with Tests mode.

  (ns javierweiss.utils
  (:require [clojure.string :as s]))
  (import '(java.io BufferedReader StringReader))

  ;;The side bar includes a Cheatsheet, full Reference, and Help. You can also Save & Share with the Community, and 
  ;;view patterns you create or favorite in My Patterns.
           
           (remove-comment-tokens \"(defn qd
                         [x]
                         (* x x))
                          ;;Esto es un comentario
                          ;;Que se comenta
                          ;;comentado
                        (def a (atom 0))\")
  
  ;;Explore results with the Tools below. Replace & List output custom results. Details lists capture groups. 
  ;;Explain describes your expression in plain English.")

  (def function "(defn- clj->md
  [st]
  (let [sq (line-seq (BufferedReader. (StringReader. st)))]
    (for [ln sq] (-> ln
                     code->md
                     remove-comment-tokens))))")

  (def texto "(def x 133)
                         (reduce + [23 32 2])
                         ;;Esto es un comentario
                         (fn [x] (* 2 x))
                         ;;Otro comentario
                     (defn funcion-del-pedo
                       [x]
                       (let [z 32]
                         (*(* x x) z)))
                     ;;Más comentarios")

  (def st "(def x 133)
            (reduce + [23 32 2])
            ;;Esto es un comentario
            (fn [x] (* 2 x))
            ;;Otro comentario") 

  (-> {:opening-parens 0
       :closing-parens 0}
      (update  :opening-parens + (->> (char-array texto)
                                      (filter #(= % \())
                                      count))
      (update  :closing-parens + (->> (char-array texto)
                                      (filter #(= % \)))
                                      count)))

  (import '(java.io BufferedReader StringReader))

  (defn str->md 
    [strng]
    (letfn [(remove-comment-tokens [st]
              (s/replace st #";;" ""))
            (count-parens [strs]
              (-> {:opening-parens 0
                   :closing-parens 0}
                  (update  :opening-parens + (->> (char-array strs)
                                                  (filter #(= % \())
                                                  count))
                  (update  :closing-parens + (->> (char-array strs)
                                                  (filter #(= % \)))
                                                  count))))]
      (loop [opening 0
             closing 0
             lines (line-seq (BufferedReader. (StringReader. strng)))
             st ""]
        (if (empty? lines)
          st
          (let [f (first lines)
                op (.contains f "(")
                clo (.contains f ")")
                {:keys [opening-parens closing-parens]} (count-parens f)
                parens-match? (== (+ opening-parens opening) (+ closing-parens closing))]
            (recur (cond
                     op (+ opening-parens opening)
                     parens-match? 0
                     :else opening)
                   (cond
                     clo (+ closing-parens closing)
                     parens-match? 0
                     :else closing)
                   (rest lines)
                   (cond
                     (.contains f ";;") (str st (remove-comment-tokens f) "\n")
                     (and op clo (== opening-parens closing-parens)) (str st " ```clojure " f " ``` \n")
                     (and op (== (+ opening opening-parens) 1)) (str st " ```clojure " f)
                     (and (not= opening 0) parens-match?)  (str st f " ``` \n")
                     (> (+ opening opening-parens) 0) (str st f "\n")
                     :else st)))))))
  
  (str->md " (defn funcion-del-pedo
                       [x]
                       (let [z 32]
                         (*(* x x) z)))")
  (str->md texto)
  (str->md function)
  ;;Problemático
  (str->md t1)
  (str->md "(ns javierweiss.utils
  (:require [clojure.string :as s]))
(import '(java.io BufferedReader StringReader))")
  ;;Problemático
  (str->md "(remove-comment-tokens \"(defn qd
                         [x]
                         (* x x))
                          ;;Esto es un comentario
                          ;;Que se comenta
                          ;;comentado
                        (def a (atom 0))\")")
  (str->md "(defn -main
  \"I don't do a whole lot ... yet.\"
  []
  {:name \"X\"})")

  (def file "src/javierweiss/powerstructuresmodel.clj")
  (def path "doc/powerstructures") 
  (file->md file path)
   
  )



