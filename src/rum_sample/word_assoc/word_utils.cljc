(ns rum-sample.word-assoc.word-utils
  (:require [rum-sample.word-assoc.words :as words]
            [clojure.string :as str]
            [clojure.set :as set]))

(defn get-n-random-words [words count]
  ; Don't pick from (rand-nth (seq words)), because then you may have repetitions of the same word.
  (->> words
      shuffle
      (take count)
      set))

(defn get-game-words [{:keys [word-count]}]
  (-> words/listed
      (get-n-random-words word-count)
      (into #{})))

(defn next-turn! [game-state]
  (let [state-atom (:state game-state)
        state @state-atom
        current-turn (:current-turn state)
        next (case current-turn
               :p1 :p1-guesser
               :p1-guesser :p2
               :p2 :p2-guesser
               :p2-guesser :p1)
        new-state (assoc state :current-turn next)]
    (reset! state-atom new-state)
    game-state))
