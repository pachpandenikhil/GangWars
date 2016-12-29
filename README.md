# GangWars
AI game playing agent based on minimax algorithm and alpha-beta pruning.
<br/>
<br/>
•	AI game playing agent for a two-player strategy board game with N x N squares.<br/>
•	Each square has a score associated with it and the player with the maximum score at the end of the game (all squares conquered by either player) wins the game.<br/>
•	Given a board state, the agent calculates the next best possible move by searching to a pre-configured depth of future moves.<br/>
•	Implemented minimax algorithm and alpha-beta pruning for searching the best move.<br/>
<br/>
<br/>
Core Technology: Java
<br/>
<br/>

# Input Format
<br/>
Input is provided via the file 'input.txt'.<br/>
<br/>
&lt;N&gt;<br/>
&lt;MODE&gt;<br/>
&lt;YOUPLAY&gt;<br/>
&lt;DEPTH&gt;<br/>
&lt;… CELL VALUES …&gt;<br/>
&lt;… BOARD STATE …&gt;<br/>
where<br/>
•	&lt;N&gt; is the board width and height, e.g., N=5 for the 5x5 board shown in the figures above.<br/>
•	&lt;MODE&gt; is “MINIMAX” or “ALPHABETA”.<br/>
•	&lt;YOUPLAY&gt; is either “X” or “O” and is the player which the agent will play on this turn.<br/>
•	&lt;DEPTH&gt; is the depth of the search.<br/>
•	&lt;… CELL VALUES …&gt; contains N lines with, in each line, N positive integer numbers each separated by a single space. These numbers represent the value/score of each location.
•	&lt;… BOARD STATE …&gt; contains N lines, each with N characters “X” or ”O” or “.” to represent the state of each cell as occupied by X, occupied by O, or free.<br/>


# Output Format
<br/>
Output is written to the file 'output.txt'.<br/>
<br/>
&lt;MOVE&gt; &lt;MOVETYPE&gt;<br/>
&lt;… NEXT BOARD STATE …&gt;<br/>
where<br/>
&lt;MOVE&gt; is the square location of the next best possible move.<br/>
&lt;MOVETYPE&gt; is the type of move to be performed.<br/>
&lt;… NEXT BOARD STATE …&gt; is the new board state after the move. Same format as &lt;… BOARD STATE …&gt; in input.txt above.
<br/>
<br/>
