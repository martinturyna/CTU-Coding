import SedmaBase
import SedmaGamble
import SedmaReplay


data YourState = YourState { hand :: Hand } deriving (Show)

rank :: Card -> Rank
rank (Card s r) = r

instance PlayerState YourState where
	initState player hand = (YourState hand)
	updateState trick player card maybecard state = myupdate (deleteHand (hand state) card) maybecard
myupdate :: Hand -> Maybe Card -> YourState
myupdate hand Nothing = (YourState hand)
myupdate hand (Just maybecard) = (YourState ([maybecard] ++ hand))

deleteHand :: Cards -> Card -> Cards
deleteHand cards card = filter (/= card) cards

player :: AIPlayer YourState
player [] state = ((hand state) !! 0)
player trick state = myplay (head trick) (hand state) ((length (hand state))-1)

myplay :: Card -> Cards -> Int -> Card 
myplay topcard hand n = if n == 0 then (hand !! 0)
					  else if (rank topcard) == rank (hand !! n) then (hand !! n)
					  else myplay topcard hand (n-1)