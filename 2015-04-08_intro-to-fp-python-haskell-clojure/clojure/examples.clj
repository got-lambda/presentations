(+ 1 1)
(def inc (fn [x] (+ x 1)))

(inc 6)
(defn inc [x] (+ x 1))

(inc 7)

(+ 1 2 3 4)
(defn add [x y] (+ x y))

(add 1 2)

(def add1 (partial add 1))

(add1 4)

(defn part [f & args]
  (fn [& more]
    (apply f (concat args more))))

(def add1 (part add 1))

(add1 2)


((partial +) 1 2 3)
((partial + 1) 2 3)
((partial + 1 2) 3)
((partial + 1 2 3))


(defn part [f & args]
  (fn [& rest]
    (apply f (concat args rest))))

((part + 1 2) 3 4)

(map inc [1 2 3])

(reduce + 0 [1 2 3])

(filter even? [1 2 3 4])


(for [x (range 1 10) :when (even? x)
      y (range 1 10) :when (odd? y)]
  (* x y))

(defn factorial [n]
  (if (zero? n)
    1
    (* n (factorial (- n 1)))))

(factorial 10)

(def twice (partial * 2))
(twice 2)

(def thrice (partial * 3))
(thrice 3)

(def six-times (comp twice thrice))
(six-times 2)

((comp twice twice thrice) 5)

(-> 5 thrice twice twice)

(twice (twice (thrice 5)))


(defn mycomp [f g]
  (fn [x] (f (g x))))

((mycomp twice thrice) 3)

(-> 4 (* 3) (* 2))

(* 2 (* 3 4))







(defrecord Cow [spotted?])
(defrecord Ostrich [height])
(defrecord Duck [daffy?])
(defrecord Goose [silly?])
(defrecord Dog [grumpy?])


(def cow #user.Cow{:spotted? true})
cow


(type #user.Cow{:spotted? true})
(type 1)

(defmulti talk type)

(defmethod talk Cow [_] "Muuu")
(defmethod talk Duck [_] "Quack Quack")


(talk #user.Cow{:spotted? true})




(def h
  (-> (make-hierarchy)
      (derive ::bird ::animal)
      (derive Dog ::animal)
      (derive Cow ::animal)
      (derive Duck ::bird)
      (derive Goose ::bird)
      (derive Ostrich ::bird)))

(defn dispatch [v] (type v))

(defmulti flies? #'dispatch :hierarchy #'h)

(defmethod flies? ::animal  [_] false)
(defmethod flies? ::bird    [_] true)
(defmethod flies? Ostrich [_] false)
(defmethod flies? Duck [duck] (not (:daffy? duck)))

(flies? #user.Cow{:spotted? true})
(flies? #user.Goose{:silly? true})
(flies? #user.Ostrich{:height 100})
(flies? #user.Duck{:daffy? true})
(flies? #user.Duck{:daffy? false})





(defn multi-dispatch [& args]
  (mapv type args))

(defmulti likes? #'multi-dispatch :hierarchy #'h)



(defmethod likes? [Dog ::animal] [dog animal]
  (not (:grumpy? dog)))


(defmethod likes? [Goose Cow] [goose cow]
  (:spotted? cow))

(defmethod likes? [Goose ::animal] [goose animal]
  true)


(defmethod likes? [::animal Goose] [animal goose]
  (:silly? goose))

(prefer-method likes? [::animal Goose] [Dog ::animal])




(likes? #user.Dog{:grumpy? false} #user.Cow{:spotted? true})
(likes? #user.Goose{:silly? true} #user.Cow{:spotted? true})
(likes? #user.Dog{:grumpy? true} #user.Goose{:silly? true})

(defmulti fibonacci identity)
(defmethod fibonacci 0 [_] 1)
(defmethod fibonacci 1 [_] 1)
(defmethod fibonacci :default [n]
  (+ (fibonacci (- n 1))
     (fibonacci (- n 2))))




(defn size->category [size]
  (condp > size
    10 :small
    50 :medium
    100 :large
    :ginormous))

(defmulti shipping-price #'size->category)

(defmethod shipping-price :small [size] "2$")
(defmethod shipping-price :medium [size]
  (str (/ size 5.0) "$"))
(defmethod shipping-price :large [size]
  (str (/ size 4.0) "$"))
(defmethod shipping-price :ginormous [size]
  "pick it up yourself!")

(shipping-price 50)


(defn walk [animal]
  ((get animal :walk) animal))

(walk {:species :Duck,
       :daffy? false,
       :walk (fn [duck]
               (if (:daffy? duck) "run" "wobble"))})
(walk {:species :Horse, :walk (fn [_] "gallop")})

(defn walk-a-lot [animal]
  (take 3 (repeatedly (partial walk animal))))

(walk-a-lot {:species :Duck,
             :daffy? false,
             :walk (fn [duck]
                     (if (:daffy? duck) "run" "wobble"))})
(walk-a-lot {:species :Horse,
             :walk (fn [_] "gallop")})
