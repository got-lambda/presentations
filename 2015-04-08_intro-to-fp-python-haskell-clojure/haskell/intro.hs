-- use C-c C-l in emacs
-- :load intro from ghci
import qualified Data.Map as Map


a $> b = b $ a

a = Map.insert "a" "2" $ Map.insert "a" "1" $ Map.empty

b = Map.empty $> (Map.insert "a" "1") $> (Map.insert "a" "2")

c = if True then "hello" else "goodbye"

hello = do
  putStr "what is your name?\n"
  a <- getLine
  putStr $ "\nhello, " ++ a ++ "!\n"

inc = \x -> x + 1
inc2 = (+) 1

fib 1 = 1
fib n = n + fib n - 1


mapped = map inc [1, 2, 3]

reduced = foldr (+) 0 [1, 2, 3, 4]

filtered = filter even [1, 2, 3, 4]

comprehended = [x * y | x <- [1..10],
                        y <- [1..10],
                        even x,
                        odd y]

factorial 0 = 1
factorial n = n * factorial (n - 1)


twice = (*) 2
thrice = (*) 3
sixtimes = twice . thrice


data Duck = Duck | DaffyDuck
data Horse = Horse

class Walker a where
  walk :: a -> String

instance Walker Duck where
  walk Duck = "wobble"
  walk DaffyDuck = "run"

instance Walker Horse where
  walk horse = "gallop"

walk_a_lot :: Walker a => a -> [String]
walk_a_lot walker = take 3 $ repeat (walk walker)
