(ns schnick-schnack-schnuck.core-test
  (:require [clojure.test :refer :all]
            [schnick-schnack-schnuck.core :refer :all]))


(deftest rand-rock-paper-scissors-test
  (testing "should return random value from list "
    (with-redefs [rand-int (constantly 2)]                  ;fix rand-int for testing

      (is (= (rand-value-from ["PAPER" "SCISSORS" "ROCK"])
             "ROCK")))))

(deftest beats?-test
  (testing "Win"
    (is (beats? "rock" "siss" {"rock" ["siss"]})))
  (testing "No win"
    (is (not (beats? "a" "b" {"a" ["c"]})))
    (is (not (beats? "a" "a" {"a" ["c"]})))))
