(ns ^:figwheel-always snake.core
  (:require-macros
   [devcards.core :as cards])
  (:require
   [cljs.test :refer-macros [testing is]]
   [reagent.core :as reagent :refer [atom]]))

(enable-console-print!)

(def board-size 12)
(def framerate 5)

(def init-state
  {:frame 0
   :apple [4 4]
   :snake {:dir [1 0]
           :body (list [3 5]
                       [2 5]
                       [1 5])}})

(defonce app-state
  (atom init-state))

(defn draw [state x y]
  (let [snake (-> state :snake :body)
        apple (:apple state)]
    (cond (= [x y] apple) "*"
          ((set snake) [x y]) "O"
          :else " ")))

(defn board []
  (let [state @app-state]
    [:table
     (for [y (range 1 board-size)]
       [:tr
        (for [x (range 1 board-size)]
          [:td
           (draw state x y)])])]))

(defn move [snake]
  (let [body (:body snake)
        head (first body)
        dir (:dir snake)]
    (assoc snake :body
           (cons (mapv + head dir)
                 (butlast body)))))

(defn next-state [state]
  (update state :snake move))

(defn tick! []
  (swap! app-state next-state))

(defn timer []
  (js/setTimeout
   (fn [] (tick!) (timer))
   (quot 1000 framerate)))

(def keycode->dir
  {38 [0 -1]
   40 [0 1]
   37 [-1 0]
   39 [1 0]})

(defn on-keydown [event]
  (let [code (.-keyCode event)]
    (when-let [dir (keycode->dir code)]
      (swap! app-state assoc-in [:snake :dir] dir))))

;; Put side-effects in an init function to not run them on each reload
(defn ^:export init []
  (timer)
  (js/addEventListener
   "keydown"
   (fn [event] (on-keydown event)))
  (reagent/render-component
   [board]
   (.getElementById js/document "app")))

;; Devcards
(cards/defcard state app-state)

(cards/defcard controls
  (cards/reagent
   [:div
    [:button {:on-click #(reset! app-state init-state)} "reset"]
    [:button {:on-click #(tick!)} "tick"]
    [:button {:on-click #(on-keydown #js{:keyCode 37})} "<"]
    [:button {:on-click #(on-keydown #js{:keyCode 38})} "^"]
    [:button {:on-click #(on-keydown #js{:keyCode 39})} ">"]
    [:button {:on-click #(on-keydown #js{:keyCode 40})} "v"]]))

(cards/defcard board
  (cards/reagent [board]))

(cards/deftest some-testing
  (testing "draw"
    (let [state {:apple [1 3]
                 :snake {:body (list [1 2]
                                     [2 2]
                                     [3 2])
                         :dir [1 0]}}]
      (is (= "O" (draw state 1 2)))
      (is (= "*" (draw state 1 3)))
      (is (= " " (draw state 2 3)))))

  (testing "move"
    (let [snake {:body (list [1 1] [1 2])
                 :dir [1 0]}]
      (is (= {:body (list [2 1] [1 1])
              :dir [1 0]}
             (move snake))))))

;; figwheel helper
(defn on-js-reload []
  (swap! app-state
    update-in [:frame] inc))
