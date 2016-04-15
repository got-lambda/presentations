(ns quick-roman.core
  (:require [clojure.string :as string]))

(def roman-factors ["I" "V" "X" "L" "C" "D" "M"])

(def roman-factorizations
  [["IIIII" "V"]
   ["VV"    "X"]
   ["XXXXX" "L"]
   ["LL"    "C"]
   ["CCCCC" "D"]
   ["DD"    "M"]])

(def roman-simplifications
  [["IIII"  "IV"]
   ["VIIII" "IX"]
   ["XXXX"  "XL"]
   ["LXXXX" "XC"]
   ["CCCC"  "CD"]
   ["DCCCC" "CM"]])

(defn defactorize [x]
  (reduce (fn [roman [long short]]
            (string/replace roman short long))
          x
          (reverse roman-factorizations)))

(defn complect [x]
  (reduce (fn [roman [long short]]
            (string/replace roman short long))
          x
          (reverse roman-simplifications)))

(defn roman->decimal [x]
  (count (defactorize (complect x))))

(defn factorize [x]
  (reduce (fn [roman [long short]]
            (string/replace roman long short))
          x
          roman-factorizations))

(defn simplify [x]
  (reduce (fn [roman [long short]]
            (string/replace roman long short))
          (factorize x)
          roman-simplifications))

(defn decimal->roman [n]
  (simplify
   (apply str (repeat n "I"))))

(defn roman-sort [x]
  (string/join
   (sort-by (fn [char] (- (.indexOf roman-factors (str char))))
            x)))

(defn r+ [n m]
  (simplify
   (str (complect n) (complect m))))
