(ns javierweiss.utils
  (:require [clojure.string :as s]))
(import '(java.io BufferedReader StringReader))

(defn- remove-comment-tokens
  [st]
  (s/replace st #";;" ""))

(defn- code->md
  [st]
  (s/replace st #"\(.*\)" (str " ```clojure\n" st "\n``` ")))

#_(defn clj->md
  [st]
  (let [s (remove-comment-tokens st) 
        sq (line-seq (BufferedReader. (StringReader. s)))] 
    (for [ln sq] (code->md ln))))

(defn clj->md
  [st]
  (let [sq (line-seq (BufferedReader. (StringReader. st)))]
    (for [ln sq] (-> ln
                     code->md
                     remove-comment-tokens))))
 
(comment
  
  (clj->md st)
  (clj->md2 st)
  (remove-comment-tokens ";;Esto es un comentario
                          ;;Que se comenta
                          ;;comentado")
  (code->md ";;Esto es un comentario
                          ;;Que se comenta
                          ;;comentado")
  
  (re-matches #"\(.*\)" "(def x 133)
                         (reduce + [23 32 2])
                         ;;Esto es un comentario
                         (fn [x] (* 2 x))
                         ;;Otro comentario")
  
  (re-seq #"\(.*\)" "(def x 133)
                         (reduce + [23 32 2])
                         ;;Esto es un comentario
                         (fn [x] (* 2 x))
                         ;;Otro comentario")
  (re-find #"\(.*\)" "(def x 133)
                         (reduce + [23 32 2])
                         ;;Esto es un comentario
                         (fn [x] (* 2 x))
                         ;;Otro comentario")
  (interpose "```" (re-seq #"\(.*\)" "(def x 133)
                         (reduce + [23 32 2])
                         ;;Esto es un comentario
                         (fn [x] (* 2 x))
                         ;;Otro comentario"))
  (apply str (map (fn [st] (str " ```clojure\n" st "\n``` ")) (re-seq #"\(.*\)" "(def x 133)
                         (reduce + [23 32 2])
                         ;;Esto es un comentario
                         (fn [x] (* 2 x))
                         ;;Otro comentario")))
  (code->md "(def x 133)
            (reduce + [23 32 2])
            ;;Esto es un comentario
            (fn [x] (* 2 x))
            ;;Otro comentario")

  (import '(java.io BufferedReader StringReader))
  (line-seq (BufferedReader. (StringReader. st)))
  (def st "(def x 133)
            (reduce + [23 32 2])
            ;;Esto es un comentario
            (fn [x] (* 2 x))
            ;;Otro comentario")
  (remove-comment-tokens "(def x 133)
                         (reduce + [23 32 2])")
  (remove-comment-tokens st)
  (if ""
    true
    false)
  
(s/replace st #"\(.*\)" (str " ```clojure\n" st "\n``` "))

 (for [ln (line-seq (BufferedReader. (StringReader. st)))] (s/replace ln #"\(.*\)" (str " ```clojure\n" ln "\n``` "))) 
  )

