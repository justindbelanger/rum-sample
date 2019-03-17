(ns rum-sample.word-assoc.word-assoc
  (:require [play-cljs.core :as p]
            [rum-sample.word-assoc.word-utils :as wu]
            [rum-sample.word-assoc.game-utils :as gl]
            [rum-sample.word-assoc.game-state :as gs]
            [tilakone.core :as tk]
            [rum.core :refer [defc] :as rum]))

(def const
  {:board-width 750
   :board-height 750
   :num-cells-x 5
   :num-cells-y 5})

(defonce game (p/create-game
               (:board-width const)
               (:board-height const)
               {:parent (js/document.getElementById "game")}))

(defonce state (atom {}))

(defn render-state [state]
  (gl/render-game-cells state))

(defn update-state [state]
  state)

(defn next-turn [state input]
  (tk/apply-signal state [:hint input]))

(def main-screen
  (reify p/Screen
    ;; runs when the screen is first shown
    (on-show [this]
      ;; start the state map with...
      (reset! state gs/codewords)
      #_(reset! state (wu/init-game)))

    ;; runs when the screen is hidden
    (on-hide [this])

    ;; runs every time a frame must be drawn (about 60 times per sec)
    (on-render [this]
      ;; we use `render` to display...
      (p/render game (render-state @state))
      #_(swap! state update-state))))

(defc game-controls < rum/reactive [game-atom]
  (rum/react game-atom)
  (let [input-id "player-input"
        {:keys [current-turn] :as game-state} @game-atom]
    (if (= (::tk/state game-state) :before-start)
      [:button {:on-click #(swap! game-atom (fn [gs] (tk/apply-signal gs [:start])))}
       "Start"]
      [:div
      [:p (str "Current turn: " current-turn)]
      [:input {:type :text :id input-id}]
      [:button {:on-click #(swap! game-atom
                                  next-turn
                                  (-> (js/document.getElementById input-id)
                                      .-value ))}
       "Next"]])))

(defn start-game []
  (doto game
    (p/start)
    (p/set-screen main-screen)))

(defn show-game-controls []
  (rum/mount
   (game-controls state)
   (js/document.getElementById "controls")))
