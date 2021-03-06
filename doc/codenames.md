# Gameplay

There are three players.  One of the players we call the Guesser.  The
other two Selectors are competing to win.

A list of 25 words is randomly created. Six are allocated to each of
the two Selector players.  Only they know which of the 25 words are
"theirs".    These two players dont share any words.

On their turn, a player announces a "hint" word and how many of the 25
words this hint applies to.  The hint is a single word and must be
different from any of the displayed 25 words.

Lets demonstrate with an example. Image one of my 6 words was "boat".
I could then announce that my hint is "float" and that this hint is
only for one of my words.

When the guesser correctly guesses my word, "boat", then I only have 5
words left.  The objective of the game is to be the first player to
have all their words guessed.

The turn order alternates between the Guesser and one of the two Selectors, e.g. Guesser, Selector 1, Guesser, Selector 2.

# Strategy

A player can announce a hint that applies to more than one of their
words.  So if we extend the example above, and say the player has both
the words "boat" and "river".  They could announce a hint like: "My
hint is 'Water' and it applies to two words."

This way on a single turn they have the chance of getting two of their
words guessed in one turn.

# Design

We have a 5x5 board.  Each cell contains one of the 25 words.

The game will be played on one laptop shared by the three players.

We have buttons on the bottom that say: "Player 1" "Player 2"
"Everyone".

When button "Player 1" is pressed their 6 words highlight.  When
"Everyone" is clicked neither of the players words are highlighted. 
