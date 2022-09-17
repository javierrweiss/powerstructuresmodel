;;# EXPLORANDO ESTRUCTURAS DE PODER
;;
;; ¿Cómo emerge el poder? Existen muchas teorías sobre el poder y muchas teorías sobre los orígenes sociales y antropológicos del poder.
;;El problema, desde un punto de vista de la filosofía de la ciencia, es que no existe una manera de contradecir o falsear estas teorías.
;;En efecto, todas ellas, en mayor o menor medida, se informan en la experiencia histórica y cada cual subraya algún aspecto determinante, 
;;como por ejemplo, la estructura social o cualidades individuales como el liderazgo, el carisma, etc. Pero no existe un criterio que nos 
;;permita decidir sobre la adecuación y veracidad de estas teorías más que la predisposición hermenéutica del investigador. ¿Qué tal si 
;;pudiéramos crear un modelo computarizado de nuestras teorías sobre la emergencia del poder y de esta forma demostrar qué rasgos o aspectos 
;;son en verdad determinantes para la emergencia de una estructura de poder? ¿Qué tal si a partir de un modelo basado en una simple regla 
;;pudiésemos (re)descubrir qué clase de estructuras de poder son posibles, cuáles son sus características, cómo evolucionan, entre otros? 
;; 
;; Con el propósito de tratar de responder algunas de estas interrogantes vamos a explorar un modelo basado en agentes, cuyo objetivo es
;;ilustrar cómo se forma cierto tipo de estructura de poder, a saber, aquellas que emergen a partir del establecimiento de una relación 
;;recíproca en donde se intercambia lealtad por algún tipo de gratificación simbólica y/o material.
;;
;; Hemos armado un modelo en NetLogo con este fin. Los agentes siguen un regla básica: si en su entorno inmediato existe un agente popular, le
;;ofrecen su lealtad; si no existe ningún agente popular pero existe alguno con muchos recursos, se la ofrecen a éste. Sólo cuando no existe
;;ningún agente a su alrededor va a decidir explotar los recursos de su entorno por sí mismo. Con este simple algoritmo hemos tratado de 
;;codificar la conducta individual que lleva al tipo de relaciones descritas arriba. El entorno y los recursos disponibles en este, vienen a 
;;representar las oportunidades y recursos materiales y simbólicos que los agentes pueden explotar en su beneficio y, en consecuencia, redistribuir
;;en intercambio por lealtad.
;;
;;Podemos intervenir en el comportamiento del modelo manipulando las siguientes variables:
;;1. El número de agentes que pueblan nuestro modelo *population*.
;;2. El porcentaje de ganancias que cada agente obtiene al explotar un nicho de su entorno *profit-percentage*
;;3. El porcentaje de ganancias que un agente obtiene al ofrecerle su lealtad a otro *share*.
;;4. El porcentaje de casillas del modelo que tendrán recursos *%_of_resourceful_patches*
;;5. La cantidad máxima de recursos que podrá tener cada casilla *world_wealth*
;;
;;Adicionalmente tenemos un botón que nos permitirá apagar esta conducta por defecto, con lo cual los agentes sólo se dedicarán a extraer los 
;;recursos de su entorno. Esto es útil para poder contrastar los patrones que veremos emerger a partir de la acción de los agentes. 


(ns javierweiss.powerstructuresmodel
  (:require [nextjournal.clerk :as clerk]
            [clojure.data.csv :as csv]
            [tablecloth.api :as tc]
            [tech.v3.datatype.functional :as dfn])
  (:gen-class))

^:clerk/hide
(comment
  (clerk/serve! {:browse? true :watch-paths ["src"]})
  (clerk/show! "src/javierweiss/powerstructuresmodel.clj")
  )
(clerk/md (slurp "src/javierweiss/powerstructuresmodel.clj"))
(clerk/md "### Holaaaa")
(clerk/md "(def valor \"Z\")")


(def experiment-1 "resources/power_structure_emergence_model_experimento_popularidad.csv")

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

(->  ds 
     (tc/select-rows (vec (range 11 18)))
     (tc/select-columns ["column-1" "column-2" "column-3" "column-4" "column-5" "column-6"]))


(defn -main
  "I don't do a whole lot ... yet."
  []
  {:name "X"})

;; TODO  
;;1. Crear un experimento con nuestro modelo y crear un dataset para explorarlo 
;; esta notebook. [LISTO]
;;2. Bajar el dataset y explorarlo. [LISTO]
;;3. Crear algunas visualizaciones. (El dataset tiene un formato inusual)
;;4. Buscar una forma de representar los datos de forma adecuada.
;;5. Insertar el modelo de ABM en la notebook.
