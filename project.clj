(defproject de-moivre "0.1.0-SNAPSHOT"
  :description "Abraham de Moivre's Inclusion–exclusion principle applied to probability"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/math.combinatorics "0.1.4"]]
  :profiles {:dev {:source-paths ["dev" "src"]
                   :dependencies [[criterium "0.4.4"]]}})
