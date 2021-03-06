import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

class State {
	String move;
	String moveType;
	char[][] boardState;
	
	public State(char[][] boardState, String move, String moveType) {
		this.boardState = boardState;
		this.move = move;
		this.moveType = moveType;
	}
	
	public String getMove() {
		return move;
	}
	public void setMove(String move) {
		this.move = move;
	}
	public String getMoveType() {
		return moveType;
	}
	public void setMoveType(String moveType) {
		this.moveType = moveType;
	}
	public char[][] getBoardState() {
		return boardState;
	}
	public void setBoardState(char[][] boardState) {
		this.boardState = boardState;
	}
	
}

public class homework {
	private int boardSize;
	char player;						// youPlay player
	String mode;
	int depth;
	private int[][] boardValues;
	private char[][] boardState;		//initial board state; locations can have values : 'X', 'O' or '.'
	
	public static final String MODE_MINIMAX 	= "MINIMAX";
	public static final String MOVE_TYPE_STAKE 	= "Stake";
	public static final String MOVE_TYPE_RAID 	= "Raid";

	private State nextMoveState;

	public homework(int boardSize, String mode, char player, int depth, int[][] boardValues, char[][] boardState) {
		this.boardSize = boardSize;
		this.player = player;
		this.mode = mode;
		this.depth = depth;
		this.boardValues = boardValues;
		this.boardState = boardState;
	}
	
	public State getNextMoveState() {
		return nextMoveState;
	}

	public void setNextMoveState(State nextMoveState) {
		this.nextMoveState = nextMoveState;
	}
	
	public char[][] getBoardState() {
		return boardState;
	}

	public void setBoardState(char[][] boardState) {
		this.boardState = boardState;
	}
	
	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}
	
	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public int getBoardSize() {
		return boardSize;
	}

	public void setBoardSize(int boardSize) {
		this.boardSize = boardSize;
	}

	public int[][] getBoardValues() {
		return boardValues;
	}

	public void setBoardValues(int[][] boardValues) {
		this.boardValues = boardValues;
	}
	
	public char getPlayer() {
		return player;
	}

	public void setPlayer(char player) {
		this.player = player;
	}
	
	private static homework readInputParameters(String inputFile) {
		homework hw = null;
		if(inputFile != null) {
			BufferedReader br = null;
			File f = null;
			try {
				f = new File(inputFile);
				br = new BufferedReader(new FileReader(f));
				if(br != null) {
					int size = Integer.parseInt(br.readLine());
					String mode = br.readLine();
					char youPlay = br.readLine().toCharArray()[0];
					int depth = Integer.parseInt(br.readLine());
					
					//reading board values
					int[][] boardValues = new int[size][size];
					for(int i = 0; i < size; i++) {
						String rowValues = br.readLine();
						String[] values = rowValues.split(" ");
						for(int j = 0; j < values.length; j++) {
							boardValues[i][j] = Integer.parseInt(values[j]);
						}
					}
					
					//reading initial board state
					char[][] boardState = new char[size][size];
					for(int i = 0; i < size; i++) {
						String rowState =  br.readLine();
						char[] states = rowState.toCharArray();
						for(int j = 0; j < states.length; j++) {
							boardState[i][j] = states[j];
						}
					}
					
					//storing the global information
					hw = new homework(size, mode, youPlay, depth, boardValues, boardState);
				}
				
			} catch (FileNotFoundException e) {
				System.err.println("Exception occured while reading input file : " + e.getMessage());
			} catch (NumberFormatException e) {
				System.err.println("Exception occured while reading input file : " + e.getMessage());
			} catch (IOException e) {
				System.err.println("Exception occured while reading input file : " + e.getMessage());
			}
			finally {
				if(br != null) {
					try {
						br.close();
					} catch (IOException e) {
						System.err.println("Exception occured while reading input file : " + e.getMessage());
					}
				}
			}
		}
		return hw;
	}
	
	private static void writeToFile(List<String> outputLines, String outputFilePath) {
		if((outputLines != null) && (outputFilePath != null)) {
			File f = null;
			FileOutputStream fos = null;
			BufferedWriter bw = null;
			try {
				f = new File(outputFilePath);
				fos = new FileOutputStream(f);
				bw = new BufferedWriter(new OutputStreamWriter(fos));
				int noOutputLines = outputLines.size();
				for (int i = 0; i < noOutputLines; i++) {
					bw.write(outputLines.get(i));
					if(i < (noOutputLines - 1))
						bw.newLine();
				}
			} catch (FileNotFoundException e) {
				System.err.println("FileNotFoundException occured. Exception : " + e.getMessage());
			} catch (IOException e) {
				System.err.println("IOException occured. Exception : " + e.getMessage());
			}
			finally {
				if(bw != null ) {
					try {
						bw.close();
					} catch (IOException e) {
						System.err.println("IOException occured. Exception : " + e.getMessage());
					}
				}
				if(fos != null ) {
					try {
						fos.close();
					} catch (IOException e) {
						System.err.println("IOException occured. Exception : " + e.getMessage());
					}
				}
			}
			
		}
	}
	
	private State createInitialState() {
		String move = "intial";
		String moveType = "initial";
		char[][] initialBoardState = this.getBoardState();
		State initial = new State(initialBoardState, move, moveType);
		return initial;
	}
	
	private boolean isTerminalState(State state) {
		boolean retVal = true;
		if(state != null) {
			char[][] boardState = state.getBoardState();
			int size = this.getBoardSize();
			for(int i = 0; i < size; i++) {
				for(int j = 0; j < size; j++) {
					if(boardState[i][j] == '.') {
						retVal = false;
						break;
					}
				}
				if(retVal == false) {
					break;
				}
			}
				
		}
		return retVal;
	}
	
	private char getOpponent(char player) {
		char opponent;
		if(player == 'X') {
			opponent = 'O';
		}
		else {
			opponent = 'X';
		}
		return opponent;
	}
	
	private int getScore(State state, char player) {
		int score = 0;
		if(state != null) {
			int[][] boardValues = this.getBoardValues();
			char[][] boardState = state.getBoardState();
			int size = this.getBoardSize();
			for(int i = 0; i < size; i++) {
				for(int j = 0; j < size; j++) {
					if(boardState[i][j] == player) {
						score += boardValues[i][j];
					}
				}
			}
		}
		return score;
	}

	
	private int gameScore(State state) {
		int score = -1;
		if(state != null) {
			char player = this.getPlayer();
			char opponent = getOpponent(player);
			int playerScore = getScore(state, player);
			int opponentScore = getScore(state, opponent);
			score = playerScore - opponentScore;
		}
		return score;
	}
	
	private boolean isValidLocation(char[][] boardState, int x, int y, int size, char player) {
		boolean retVal = false;
		if( (x < size) && (x > -1) )
			if( (y < size) && (y > -1) )
				if(boardState[x][y] == player) 
					retVal = true;
		return retVal;
	}
	
	private List<Integer> getAdjacentLocations(char[][] boardState, int size, int i, int j, char player) {
		List<Integer> adjacentLocations = new ArrayList<>();
		if(size > 1) {
			
			//calculating the top location
			int x = i - 1;
			int y = j;
			if(isValidLocation(boardState, x, y, size, player)) {
				adjacentLocations.add(x);
				adjacentLocations.add(y);
			}
			
			//calculating the bottom location
			x = i + 1;
			y = j;
			if(isValidLocation(boardState, x, y, size, player)) {
				adjacentLocations.add(x);
				adjacentLocations.add(y);
			}
			
			//calculating the left location
			x = i;
			y = j - 1;
			if(isValidLocation(boardState, x, y, size, player)) {
				adjacentLocations.add(x);
				adjacentLocations.add(y);
			}
			
			//calculating the right location
			x = i;
			y = j + 1;
			if(isValidLocation(boardState, x, y, size, player)) {
				adjacentLocations.add(x);
				adjacentLocations.add(y);
			}
		}
		return adjacentLocations;
	}
	
	

	private List<State> getRaidMoves(State state, char currPlayer) {
		List<State> raidMoves = new ArrayList<>();
		if(state != null) {
			char[][] boardState = state.getBoardState();
			int size = this.getBoardSize();
			for(int i = 0; i < size; i++) {
				for(int j = 0; j < size; j++) {
					//searching for possible raid moves
					if(boardState[i][j] == '.') {
						//checking if this location can be raided
						List<Integer> adjacentLocations = getAdjacentLocations(boardState, size, i, j, currPlayer);
						
						//location can be raided
						if(adjacentLocations.size() > 0) {
							//checking if the raided location has opponents
							List<Integer> adjacentOpponentLocations = getAdjacentLocations(boardState, size, i, j, getOpponent(currPlayer));
							if(adjacentOpponentLocations.size() > 0) {
								//adding raid location
								State conqueredLocation = createState(boardState, i, j, currPlayer, MOVE_TYPE_RAID);
								for(int l = 0; l < adjacentOpponentLocations.size() - 1; l++) {
									int opponentX = adjacentOpponentLocations.get(l++);
									int opponentY = adjacentOpponentLocations.get(l);									
									//adding conquered locations
									conqueredLocation.getBoardState()[opponentX][opponentY] = currPlayer;									
								}
								raidMoves.add(conqueredLocation);
							}
							else {
								State raidLocation = createState(boardState, i, j, currPlayer, MOVE_TYPE_RAID);
								raidMoves.add(raidLocation);								
							}
						}
					}
				}
			}
		}
		return raidMoves;
	}
	
	

	private List<State> getChildren(State state, boolean maximizingPlayer) {
		List<State> children = new ArrayList<>();
		if(state != null) {
			char currPlayer;
			if(maximizingPlayer) {
				currPlayer = this.getPlayer();
			}
			else {
				currPlayer = getOpponent(this.getPlayer());
			}
			
			List<State> stakeMoves = getStakeMoves(state, currPlayer);
			List<State> raidMoves = getRaidMoves(state, currPlayer);
			children.addAll(stakeMoves);
			children.addAll(raidMoves);
		}
		return children;
	}
	
	

	private String getMove(int x, int y) {
		char column = (char)(y+65);
		String row = (x+1) + "";
		String move = column + "" + row;
		return move;
	}
	
	
	private State createState(char[][] boardState, int x, int y, char player, String moveType) {
		int boardSize = this.getBoardSize();
		
		//creating new board state 
		char[][] newBoardState = new char[boardSize][boardSize];
		for(int i = 0; i < boardSize; i++) {
			for(int j = 0; j < boardSize; j++) {
				newBoardState[i][j] = boardState[i][j];
			}
		}
		
		//updating the move
		newBoardState[x][y] = player;
		
		//calculating the move
		String move = getMove(x, y);
		
		//creating the state
		State newState = new State(newBoardState, move, moveType);

		return newState;
	}
	
	private List<State> getStakeMoves(State state, char player) {
		List<State> stakeMoves = new ArrayList<>();
		if(state != null) {
			char[][] boardState = state.getBoardState();
			int size = this.getBoardSize();
			for(int i = 0; i < size; i++) {
				for(int j = 0; j < size; j++) {
					if(boardState[i][j] == '.') {
						State stakeMove = createState(boardState, i, j, player, MOVE_TYPE_STAKE);
						stakeMoves.add(stakeMove);
					}
				}
			}
		}
		return stakeMoves;
	}

	

	private int minimax(State state, int depth, boolean maximizingPlayer) {
		int score = -1;
		if(state != null) {
			if( (depth == 0) || (isTerminalState(state)) ) {
				return gameScore(state);
			}
			
			if(maximizingPlayer) {
				int bestValue = Integer.MIN_VALUE;
				List<State> children = getChildren(state, maximizingPlayer);
				for (State child : children) {
					int value = minimax(child, depth - 1, false);
					if(value > bestValue) {
						if(depth == this.getDepth())
							this.setNextMoveState(child);
						bestValue = value;
					}
				}
				return bestValue;
			}
			else {
				int bestValue = Integer.MAX_VALUE;
				List<State> children = getChildren(state, maximizingPlayer);
				for (State child : children) {
					int value = minimax(child, depth - 1, true);
					if(value < bestValue) {
						bestValue = value;
					}
				}
				return bestValue;
			}
		}
		return score;
	}
	
	private int alphabeta(State state, int depth, int a, int b, boolean maximizingPlayer) {
		int score = -1;
		if(state != null) {
			if( (depth == 0) || (isTerminalState(state)) ) {
				return gameScore(state);
			}
			
			if(maximizingPlayer) {
				int bestValue = Integer.MIN_VALUE;
				List<State> children = getChildren(state, maximizingPlayer);
				for (State child : children) {
					int value = alphabeta(child, depth - 1, a, b, false);
					if(value > bestValue) {
						if(depth == this.getDepth())
							this.setNextMoveState(child);
						bestValue = value;
					}
					
					if(value > a) {
						a = value;
					}
					
					if(b <= a) {
						//System.out.println("pruning");
						break;
					}
						
				}
				return bestValue;
			}
			else {
				int bestValue = Integer.MAX_VALUE;
				List<State> children = getChildren(state, maximizingPlayer);
				for (State child : children) {
					int value = alphabeta(child, depth - 1, a, b, true);
					if(value < bestValue) {
						bestValue = value;
					}
					
					if(value <  b) {
						b = value;
					}
					
					if(b <= a) {
						//System.out.println("pruning");
						break;
					}					
				}
				return bestValue;
			}
		}
		return score;
	}

	private List<String> execute() {
		List<String> outputLines = new ArrayList<>();
		State initial = createInitialState();
		int depth = this.getDepth();
		String mode = this.getMode();
		if(mode.equalsIgnoreCase(MODE_MINIMAX)) {
			minimax(initial, depth, true);
		}
		else {
			alphabeta(initial, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
		}

		State nextMoveState = this.getNextMoveState();
		outputLines.add(nextMoveState.getMove() + " " + nextMoveState.getMoveType());
		char[][] nextMoveBoardState = nextMoveState.getBoardState();
		int size = this.getBoardSize();
		String stateLine = "";
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				stateLine += nextMoveBoardState[i][j];
			}
			outputLines.add(stateLine);
			stateLine = "";				
		}

		return outputLines;
	}
	
	public static void main(String[] args) {
		// Variables for parsing
		String inputFile = "input.txt";
		String outputFile = "output.txt";
		String classPath = homework.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		try {
			String decodedClassPath = URLDecoder.decode(classPath, "UTF-8");
			homework hw = readInputParameters(decodedClassPath + inputFile);
			List<String> outputLines = hw.execute();
			//System.out.println("Done final rectified");
			writeToFile(outputLines, decodedClassPath + outputFile);
		} catch (UnsupportedEncodingException e) {
			System.err.println("Exception occured : " + e.getMessage());
		}	
	}
}
