import SedmaDatatypes

instance Eq Rank where
    (==) R7 R7 = True
    (==) R8 R8 = True
    (==) R9 R9 = True
    (==) R10 R10 = True
    (==) RJ RJ = True
    (==) RQ RQ = True
    (==) RK RK = True
    (==) RA RA = True
    (==) _ _ = False

instance Eq Suit where
    (==) Heart Heart = True
    (==) Diamond Diamond = True
    (==) Spade Spade = True
    (==) Club Club = True
    (==) _ _ = False

instance Eq Card where
    (==) a b = ((rank a == rank b) && (suit a == suit b))

suit :: Card -> Suit
suit (Card s r) = s

rank :: Card -> Rank
rank (Card s r) = r


evalLeader :: Int -> Cards -> Int
evalLeader curr cards = if rank(cards !! 3) == R7 || rank(cards !! 0) == rank(cards !! 3) then ((curr + 3) `mod` 4)
				else if rank(cards !! 2) == R7 || rank(cards !! 0) == rank(cards !! 2) then ((curr + 2) `mod` 4)
				else if rank(cards !! 1) == R7 || rank(cards !! 0) == rank(cards !! 1) then ((curr + 1) `mod` 4)
				else curr

win :: Int -> Int -> Bool -> Bool -> Maybe Winner
win ac bd acb bdb = if ac == 90 && bdb == False then Just (AC, Three)
					else if bd == 90 && acb == False then Just (BD, Three)
                    else if ac == 90 && bdb == True then Just (AC, Two)
					else if bd == 90 && acb == True then Just (BD, Two)
                    else if ac > bd then Just (AC, One)
                    else Just (BD, One)

evalAC :: Int -> Int -> Cards -> Int
evalAC 0 ac cards = winnerWon cards ac
evalAC 2 ac cards = winnerWon cards ac
evalAC _ ac cards = ac

evalBD :: Int -> Int -> Cards -> Int
evalBD 1 bd cards = winnerWon cards bd
evalBD 3 bd cards = winnerWon cards bd
evalBD _ bd cards = bd

winnerWon :: Cards -> Int -> Int
winnerWon cards points = if length cards == 0 then points
						 else if rank(cards !! 0) == R10 || rank(cards !! 0) == RA then
								winnerWon (drop 1 cards) points+10
						 else winnerWon (drop 1 cards) points

wrongPack :: Cards -> Bool
wrongPack [] = False
wrongPack (card:pack) = if elem card pack then True
					else wrongPack pack


roundA :: Int -> Int -> Int -> Cards -> Bool -> Bool -> Maybe Winner
roundA leader ac bd cards acb bdb | length cards == 0 = if leader == 0 || leader == 2 then (win(ac+10) bd acb bdb) else (win ac (bd+10) acb bdb)
								  | otherwise = roundA leaderN (evalAC leaderN ac (take 4 cards)) (evalBD leaderN bd (take 4 cards)) cardsN ((acb == False && leaderN == 0 || leaderN == 2) || acb) ((bdb == False && leaderN == 1 || leaderN == 3) || bdb)
						     	 		where
								      		leaderN = evalLeader leader (take 4 cards)
								        	cardsN = (drop 4 cards)

replay :: Cards -> Maybe Winner
replay cards = if length cards > 32 || length cards < 32 then Nothing
          else if wrongPack cards then Nothing
          else roundA 0 0 0 cards False False