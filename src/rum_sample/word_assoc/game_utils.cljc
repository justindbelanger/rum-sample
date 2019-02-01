(ns rum-sample.word-assoc.game-utils)

(defn get-cell-origin [x-num y-num
                       {:keys [board-width
                               board-height
                               num-cells-x
                               num-cells-y]
                        :as game-state}]
  "cells count from bottom up (y-coord), first cell is index
  0. x-coord is from left to right."
  (let [x-seg-len (/ board-width num-cells-x)
        y-seg-len (/ board-height num-cells-y)
        x-origin (* x-num x-seg-len)
        y-origin (* y-num y-seg-len)]
    [x-origin y-origin]))

(defn convert-cell-num-to-cell-coord
  [cell-num
   {:keys [board-width
           board-height
           num-cells-x
           num-cells-y]
    :as game-state}]
  "given a number between 0 and then number of cells in the game
  return an [x y] coord"
  (let [x (rem cell-num num-cells-x)
        y (quot cell-num num-cells-x)]
    [x y]))

(defn render-game-cells [{:keys [num-cells-x
                                 num-cells-y
                                 board-width
                                 board-height]
                          :as game-state}]
  (let [num-cells (* num-cells-x num-cells-y)
        width (/ board-width num-cells-x)
        height (/ board-height num-cells-y)]
    (for [x (range num-cells-x)
          y (range num-cells-y)
          :let [[x-origin y-origin] (get-cell-origin x y game-state)]]
      [[:fill {:color "blue"}
        [:rect {:x x-origin
                :y y-origin
                :width width
                :height height}]]
       [:fill {:color "white"}
        [:text {:value "Hello"
                :x (+ 10 x-origin)
                :y (+ 30 y-origin)
                :size 16
                :font "Georgia"
                :style :italic}]]])))

(comment
  (render-game-cells {:board-width 250
                      :board-height 250
                      :num-cells-x 5
                      :num-cells-y 5})
  )
