(ns schnick-schnack-schnuck.core)


(defn rand-value-from [value-list]
  (->> (count value-list)
       (rand-int)
       (nth value-list)))


(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))
