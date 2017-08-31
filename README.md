# Gang Wars
AI game playing agent based on minimax algorithm and alpha-beta pruning.
- AI game playing agent for a two-player strategy board game with N x N squares.
- Each square has a score associated with it and the player with the maximum score at the end of the game (all squares conquered by either player) wins the game.
- Given a board state, the agent calculates the next best possible move by searching to a pre-configured depth of future moves.
- Implemented minimax algorithm and alpha-beta pruning for searching the best move.

Core Technology: Java

# Input Format
Input is provided via the file *input.txt*.

```
<N>
<MODE>
<YOUPLAY>
<DEPTH>
<… CELL VALUES …>
<… BOARD STATE …>
```
where

- *N* is the board width and height, e.g., N=5 for the 5x5 board shown in the figures above.
- *MODE* is “MINIMAX” or “ALPHABETA”.
- *YOUPLAY* is either “X” or “O” and is the player which the agent will play on this turn.
- *DEPTH* is the depth of the search.
- *CELL VALUES* contains N lines with, in each line, N positive integer numbers each separated by a single space. These numbers represent the value/score of each location.
- *BOARD STATE* contains N lines, each with N characters “X” or ”O” or “.” to represent the state of each cell as occupied by X, occupied by O, or free.

# Output Format
Output is written to the file *output.txt*.
```
<MOVE> <MOVETYPE>
<… NEXT BOARD STATE …>
```
where

- *MOVE* is the square location of the next best possible move.
- *MOVETYPE* is the type of move to be performed.
- *NEXT BOARD STATE* is the new board state after the move. Same format as *BOARD STATE* in *input.txt* above.
