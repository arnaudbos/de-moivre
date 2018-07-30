(ns user
  (:require [de-moivre.prob :as prob]
            [criterium.core :refer [bench quick-bench with-progress-reporting]]))

(comment let [seed (doall (map #(/ % 10) (range 1 4)))]
  (bench (apply prob/∪-v1 seed))
  (bench (apply prob/∪-v2 seed))
  (bench (apply prob/∪-v3 seed))
  (bench (apply prob/∪-v4 seed))
  (bench (apply prob/∪-v5 seed))
  (bench (apply prob/∪-v6 seed)))