(ns rum-sample.word-assoc.game-utils
  (:require [tilakone.core :as tk]
            [rum-sample.word-assoc.game-state :as gs]))

(defn get-cell-origin [x-num y-num
                       {:keys [num-cells-x
                               num-cells-y]
                        :as game-state}]
  "cells count from bottom up (y-coord), first cell is index
  0. x-coord is from left to right."
  (let [{:keys [board-width board-height]} gs/const
        x-seg-len (/ board-width num-cells-x)
        y-seg-len (/ board-height num-cells-y)
        x-origin (* x-num x-seg-len)
        y-origin (* y-num y-seg-len)]
    [x-origin y-origin]))

(defn convert-coord-to-index [num-cells-y x y]
  (+ x (* y num-cells-y)))

(defn color-for-word [word {:keys [p1-words
                                   p2-words
                                   current-turn]}
                      player-colors]
  (cond
    (and (p1-words word) (= current-turn :p1)) (first player-colors)
    (and (p2-words word) (= current-turn :p2)) (second player-colors)
    :else (nth player-colors 2)))

(defn render-game-cells [{:keys [num-cells-x
                                 num-cells-y
                                 player-colors]
                          :as game-state}]
  (let [{:keys [board-width board-height]} gs/const]
    (if (= (::tk/state game-state) :before-start)
     [:text {:value "Waiting for the game to begin."
             :y (/ board-height 2)}]
     (let [num-cells (* num-cells-x num-cells-y)
           width (/ board-width num-cells-x)
           height (/ board-height num-cells-y)]
       (for [x (range num-cells-x)
             y (range num-cells-y)
             :let [[x-origin y-origin] (get-cell-origin x y game-state)
                   index (convert-coord-to-index num-cells-y x y)
                   state game-state
                   word (nth (vec (:game-words state)) index)
                   word-color (color-for-word word state player-colors)
                   is-guessed (contains? (apply clojure.set/union (:player-guessed-words state)) word)]]
         [[:fill {:color word-color}
           [:rect {:x x-origin
                   :y y-origin
                   :width width
                   :height height}]]
          [:fill {:color (if is-guessed "black" "white")}
           [:text {:value word
                   :x (+ 10 x-origin)
                   :y (+ 30 y-origin)
                   :size 16
                   :font "Georgia"
                   :style :italic}]]])))))
