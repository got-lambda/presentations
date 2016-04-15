(ns quick-roman.core-test
  (:require [clojure.test :refer :all]
            [quick-roman.core :refer :all]
            [clojure.test.check :as tc]
            [clojure.test.check.clojure-test :refer :all]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]))


(def ints-in-roman-range
  (gen/choose 1 3999))

(defspec reversible
  100
  (prop/for-all [i ints-in-roman-range]
    (= (roman->decimal (decimal->roman i))
       i)))

(defspec less-than-4-in-a-row
  100
  (prop/for-all [i ints-in-roman-range]
    (<=
     (->> (decimal->roman i)
          (partition-by identity)
          (map count)
          (apply max))
     4)))

(defspec less-than-3-in-a-row
  100
  (prop/for-all [i ints-in-roman-range]
    (<=
     (->> (decimal->roman i)
          (partition-by identity)
          (map count)
          (apply max))
     3)))

(defspec equivalent-addition
  100
  (prop/for-all* [(gen/choose 1 2000)]
    (fn [n]
      (prop/for-all* [(gen/choose 1 (- 3999 n))]
        (fn [m]
          m (gen/choose 1 1999)
          (= (roman->decimal (r+ (decimal->roman n)
                                 (decimal->roman m)))
             (+ n m)))))))
