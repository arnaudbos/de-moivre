(ns de-moivre.prob
  (:require [clojure.math.combinatorics :refer [combinations]]))

(defn ∩
  [a & more]
  (reduce * a more))

;; "Here be dragons"
;; I implemented several versions of the union fn from the most straightforward
;; to bizarre transducer attempts, all of them are probably terrible in terms of
;; complexities (Big O) but I won't experiment nor improve further.

(defn ∪-v1
  [a & more]
  (let [ps (concat [a] more)
        n (count ps)]
    (loop [k 1 prev 0]
      (if (> k n)
        prev
        (let [mult (Math/pow -1 (+ k 1))
              cs (combinations ps k)
              intersections (map #(* mult (apply ∩ %)) cs)]
          (recur (inc k) (reduce + prev intersections)))))))

(defn ∪-v2
  [a & more]
  (let [ps (concat [a] more)
        n (count ps)]
    (->> (range 1 (inc n))
         (map #(combinations ps %))
         (map (fn [cs] (->> cs (map #(apply ∩ %)) (apply +))))
         (map-indexed #(* %2 (Math/pow -1 (+ 2 %1))))
         (reduce +)
         )))

(defn ∪-v3
  [a & more]
  (let [ps (concat [a] more)
        n (count ps)]
    (->> (range 1 (inc n))
         (map #(list (Math/pow -1 (inc %)) (combinations ps %)))
         (mapcat (fn [[m cs]] (map #(list m %) cs)))
         (map (fn [[m c]] (list m (apply ∩ c))))
         (map #(apply * %))
         (reduce +)
         )))

(defn ∪-v4
  [a & more]
  (let [ps (concat [a] more)
        n (count ps)]
    (transduce (comp (map #(combinations ps %))
                     (map (fn [cs] (->> cs (map #(apply ∩ %)) (apply +))))
                     (map-indexed #(* %2 (Math/pow -1 (+ 2 %1)))))
               +
               (range 1 (inc n))
               )))

(defn ∪-v5
  [a & more]
  (let [ps (concat [a] more)
        n (count ps)]
    (transduce (comp (map #(list (Math/pow -1 (inc %)) (combinations ps %)))
                     (mapcat (fn [[m cs]] (map #(list m %) cs)))
                     (map (fn [[m c]] (list m (apply ∩ c))))
                     (map #(apply * %)))
               +
               (range 1 (inc n))
               )))

(defn ∪-v6
  [a & more]
  (let [ps (concat [a] more)
        n (count ps)]
    (transduce (comp (map #(combinations ps %))
                     (map (fn [cs]
                            (transduce
                              (map #(apply ∩ %))
                              +
                              cs)))
                     (map-indexed #(* %2 (Math/pow -1 (+ 2 %1)))))
               +
               (range 1 (inc n))
               )))

;; ∪-v1 seems the fastest according to benchmarks in dev/user.clj
;; Which is funny because it's the most legible and first version
;; I came up with.
(def ∪ ∪-v1)