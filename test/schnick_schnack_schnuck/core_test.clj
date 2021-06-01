(ns schnick-schnack-schnuck.core-test
  (:require [clojure.test :refer :all]
            [schnick-schnack-schnuck.core :refer :all]))


(deftest rand-rock-paper-scissors-test
  (testing "should return random value from list "
    (with-redefs [rand-int (constantly 2)]                  ;fix rand-int for testing

      (is (= (rand-value-from ["PAPER" "SCISSORS" "ROCK"])
             "ROCK")))))
