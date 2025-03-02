import java.util.Random;
import java.util.ArrayList;
import java.util.InputMismatchException;

/**
 * <p>
 * Command line implementation of the Connect 4 game by Hasbro.
 * </p>
 * <p>
 * This class handles all the game logic, including initialising 1 player and 2
 * player games, running the game loop and checking win and draw states. It has
 * the main entry point for the program.
 * </p>
 */
public class ConnectFour {
    /**
     * <p>
     * Declaration of the Board.
     * </p>
     */
    private Board board;
    /**
     * <p>
     * Declaration of the display.
     * </p>
     */
    private Display display;
    /**
     * <p>
     * Declaration of Input for player one
     * </p>
     */
    private Input playerOneInput;
    /**
     * <p>
     * Declaration of Input for player two
     * </p>
     */
    private Input playerTwoInput;
    /**
     * <p>
     * Declaration of variable to track win state
     * </p>
     */
    private boolean isWin;
    /**
     * <p>
     * Declaration of variable to track draw state
     * </p>
     */
    private boolean isDraw;
    /**
     * <p>
     * Declaration of player one
     * </p>
     */
    private Player playerOne;
    /**
     * <p>
     * Declaration of player two
     * </p>
     */
    private Player playerTwo;
    /**
     * <p>
     * Declaration of the active player
     * </p>
     */
    private Player activePlayer;

    /**
     * <p>
     * Constructs the game and initialises the board, display, input of first player
     * and win/draw states.
     * </p>
     */
    public ConnectFour() {
        this.board = new Board();
        this.display = new Display();
        this.isWin = false;
        this.isDraw = false;
        this.playerOneInput = new Input();
    }

    /**
     * <p>
     * Method that starts the games and instructs the player on the initial setup.
     * Options include either a one player or two player game.
     * </p>
     * 
     * @param repeat
     */
    private void playGame(boolean repeat) {
        try {
            if (!repeat) {
                this.display.displayInfoMessage("Welcome to Connect 4!!");
                this.display.displayInfoMessage("Hit Ctrl + c to quit at any time\n");
                this.display.displayInfoMessage("Please select a game mode:");
                this.display.displayInfoMessage("1 - 1 Player (against the computer)");
                this.display.displayInfoMessage("2 - 2 Player");
            }

            int selection = this.playerOneInput.intInput();
            if (selection == 1) {
                onePlayerGame();
            } else if (selection == 2) {
                this.playerTwoInput = new Input();
                twoPlayerGame();
            } else {
                this.display.displayErrorMessage("Please enter 1 or 2 only.");
                playGame(true);
            }
        } catch (InputMismatchException e) {
            this.display.displayErrorMessage("Please enter 1 or 2 only.");
            this.playerOneInput.next();
            playGame(true);
        }
    }

    /**
     * <p>
     * Methods that starts a one player game. Handles logic for selecting a colour,
     * initialising the human and computer players and setting player name.
     * </p>
     */
    private void onePlayerGame() {
        this.display.displayInfoMessage("Player 1, please choose your colour (\"r\" or \"y\"):");
        char playerOneColour = getColourSelection();
        char playerTwoColour = playerOneColour == 'r' ? 'y' : 'r';
        this.playerOne = new HumanPlayer(this.playerOneInput, this.display, this.board, playerOneColour);
        this.playerTwo = new ComputerPlayer(this.display, this.board, playerTwoColour);

        this.display.displayInfoMessage("Player 1, please enter your name:");
        String name = this.playerOneInput.stringInput();
        this.playerOne.setName(name);

        Random rand = new Random();
        int startPlayer = rand.nextInt(2);
        this.display.displayInfoMessage("Welcome, " + this.playerOne.getName());
        this.activePlayer = startPlayer == 0 ? this.playerOne : this.playerTwo;
        this.display.displayInfoMessage(this.activePlayer.getName() + " will go first.");
        gameLoop();
    }

    /**
     * <p>
     * Methods that starts a two player game. Handles logic for selecting a colour
     * for player 1,
     * initialising two human players and setting players' names. The method selects
     * a player at random to go first.
     * </p>
     */
    private void twoPlayerGame() {
        this.display.displayInfoMessage("Player 1, please choose your colour (\"r\" or \"y\"):");
        char playerOneColour = getColourSelection();
        char playerTwoColour = playerOneColour == 'r' ? 'y' : 'r';
        this.playerOne = new HumanPlayer(this.playerOneInput, this.display, this.board, playerOneColour);
        this.playerTwo = new HumanPlayer(this.playerTwoInput, this.display, this.board, playerTwoColour);

        this.display.displayInfoMessage("Player 1, please enter your name:");
        String name = this.playerOneInput.stringInput();
        this.playerOne.setName(name);

        this.display.displayInfoMessage("Player 2, please enter your name:");
        name = this.playerTwoInput.stringInput();
        this.playerTwo.setName(name);

        Random rand = new Random();
        int startPlayer = rand.nextInt(2);
        this.display.displayInfoMessage("Welcome, " + this.playerOne.getName() + " and " + this.playerTwo.getName());
        this.activePlayer = startPlayer == 0 ? this.playerOne : this.playerTwo;
        this.display.displayInfoMessage(this.activePlayer.getName() + " will go first.");
        gameLoop();
    }

    /**
     * <p>
     * Method that handles errors in player colour selection. Loops until the player
     * selects a correct piece.
     * </p>
     * 
     * @return char r or y representing red and yellow
     */
    private char getColourSelection() {
        while (true) {
            char colChar = this.playerOneInput.charInput();
            if (colChar == 'r' || colChar == 'y') {
                return colChar;
            } else {
                this.display.displayErrorMessage("Please enter a valid colour");
            }
        }
    }

    /**
     * Method that starts the game loop, displays the board and checks win/draw
     * states, The methods loops until there is a winner, it is a draw or the game
     * is exited.
     */
    private void gameLoop() {
        try {
            while (!this.isWin && !this.isDraw) {
                this.display.displayBoard(this.board.getBoard());
                ArrayList<Integer[]> validMoves = getValidMoves();
                this.activePlayer.takeTurn(validMoves);
                checkWinState(this.activePlayer.getColour(), this.activePlayer.getLastMove());
                checkDrawState();
                if (!this.isWin && !this.isDraw) {
                    this.toggleActivePlayer();
                }
            }
            // Messages to display on win.
            if (this.isWin) {
                this.display.displayBoard(this.board.getBoard());
                if (!this.activePlayer.getName().equals("The Computer")) {
                    this.display.displayInfoMessage(this.activePlayer.getName() + " wins!!!!");
                    this.display.displayInfoMessage("Congratulations!!!");
                } else {
                    this.display.displayInfoMessage(this.activePlayer.getName() + " wins!!!!");
                    this.display.displayInfoMessage("Better luck next time!!!");
                }
            }
            // MEssages to display on draw
            if (this.isDraw) {
                this.display.displayBoard(this.board.getBoard());
                this.display.displayInfoMessage("The game is a draw");
            }
        } finally {
            // Close the input streams
            this.playerOneInput.close();
            if (!this.playerTwo.getName().equals("The Computer")) {
                this.playerTwoInput.close();
            }
        }
    }

    /**
     * <p>
     * Method to toggle the active player when a turn is played.
     * </p>
     */
    private void toggleActivePlayer() {
        if (this.activePlayer.getName().equals(this.playerOne.getName())) {
            this.activePlayer = this.playerTwo;
        } else {
            this.activePlayer = this.playerOne;
        }
    }

    /**
     * <p>
     * Method to build a list of valid moves in the format [roe, column] by looping
     * over the board and building a list of the first column that is empty.
     * </p>
     * 
     * @return An ArrayList of coordinate arrays with valid moves.
     */
    private ArrayList<Integer[]> getValidMoves() {
        ArrayList<Integer[]> validMoves = new ArrayList<Integer[]>();
        ArrayList<Integer> colsSet = new ArrayList<Integer>();
        char[][] board = this.board.getBoard();
        // Loop backwards over array to get lowest row first
        for (int i = board.length - 1; i >= 0; i--) {
            for (int j = 0; j < board[i].length; j++) {
                // If the column is empty and not already in the list
                if (board[i][j] == ' ') {
                    Integer[] coord = { i, j };
                    if (!colsSet.contains(j - 1)) {
                        colsSet.add(j - 1);
                        validMoves.add(coord);
                    }
                }
            }
        }
        return validMoves;
    }

    /**
     * Method that checks and updates the win state by building a moving window of
     * wining moves based on the last move and checking if there are 4 in a row,
     * column or diagonal of the same colour.
     * 
     * @param colour the char r or y
     */
    private void checkWinState(char colour, Integer[] position) {
        int rowPos = position[0];
        int colPos = position[1];
        ArrayList<Integer[][]> linesToCheck = toCheck(rowPos, colPos);
        int count;
        for (Integer[][] lineToCheck : linesToCheck) {
            count = 1;
            for (Integer[] coord : lineToCheck) {
                if (this.board.getPieceAtPosition(coord) == colour) {
                    count += 1;
                } else {
                    count = 0;
                    break;
                }
            }
            if (count >= 4) {
                this.isWin = true;
                break;
            }
        }
    }

    /**
     * <p>
     * Method that builds the window of possible winning diagonals, rows and columns
     * based on the last move the player made. The 3D array returned only contains
     * valid combinations (i.e. those that do not extend outside of the confines of
     * the board).
     * </p>
     * 
     * @param colPos An integer representing the column of the last move
     * @param rowPos An integer representing the row of the last move
     * @return A 3D array of possible winning combinations
     */
    private ArrayList<Integer[][]> toCheck(int rowPos, int colPos) {
        ArrayList<Integer[][]> linesToCheck = new ArrayList<Integer[][]>();
        for (int i = 1; i <= 8; i++) {
            // Diagonal
            if (i == 1) {
                Integer[][] lineToCheck = generateDiagLine(rowPos, colPos, true, true);
                if (isValidLine(lineToCheck)) {
                    linesToCheck.add(lineToCheck);
                }
            }
            if (i == 2) {
                Integer[][] lineToCheck = generateDiagLine(rowPos, colPos, true, false);
                if (isValidLine(lineToCheck)) {
                    linesToCheck.add(lineToCheck);
                }
            }
            if (i == 3) {
                Integer[][] lineToCheck = generateDiagLine(rowPos, colPos, false, true);
                if (isValidLine(lineToCheck)) {
                    linesToCheck.add(lineToCheck);
                }
            }
            if (i == 4) {
                Integer[][] lineToCheck = generateDiagLine(rowPos, colPos, false, false);
                if (isValidLine(lineToCheck)) {
                    linesToCheck.add(lineToCheck);
                }
            }
            // Row
            if (i == 5) {
                Integer[][] lineToCheck = generateRowLine(rowPos, colPos, true);
                if (isValidLine(lineToCheck)) {
                    linesToCheck.add(lineToCheck);
                }
            }
            if (i == 6) {
                Integer[][] lineToCheck = generateRowLine(rowPos, colPos, false);
                if (isValidLine(lineToCheck)) {
                    linesToCheck.add(lineToCheck);
                }
            }
            // Column
            if (i == 7) {
                Integer[][] lineToCheck = generateColLine(rowPos, colPos, true);
                if (isValidLine(lineToCheck)) {
                    linesToCheck.add(lineToCheck);
                }
            }
            if (i == 8) {
                Integer[][] lineToCheck = generateColLine(rowPos, colPos, false);
                if (isValidLine(lineToCheck)) {
                    linesToCheck.add(lineToCheck);
                }
            }
        }
        return linesToCheck;
    }

    /**
     * <p>
     * A method that returns if a line calculated in the above method is valid (i.e.
     * inside the board parameters).
     * </p>
     * 
     * @param line A 2D array of coordinate lines calculated.
     * @return True if it is a valid line, false otherwise.
     */
    private boolean isValidLine(Integer[][] line) {
        for (Integer[] coord : line) {
            Integer row = coord[0];
            Integer col = coord[1];
            if (row < 0 || row > 5 || col < 0 || col > 6) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>
     * Generates valid winning combinations in the diagonals based on a column and
     * row input.
     * </p>
     * 
     * @param rowPos    Integer representing the row of the last move.
     * @param colPos    Integer representing the column of the last move.
     * @param increaseX A boolean declaring that the diagonal is increasing in the X
     *                  axis
     * @param increaseY A boolean declaring that the diagonal is increasing in the Y
     *                  axis
     * @return A 2D array of Integer coordinates.
     */
    private Integer[][] generateDiagLine(int rowPos, int colPos, boolean increaseX, boolean increaseY) {
        Integer[][] lineToCheck = new Integer[3][2];
        for (int j = 1; j <= 3; j++) {
            int x;
            int y;
            if (increaseX && increaseY) {
                x = colPos + j;
                y = rowPos + j;
            } else if (!increaseX && increaseY) {
                x = colPos - j;
                y = rowPos + j;
            } else if (increaseX && !increaseY) {
                x = colPos + j;
                y = rowPos - j;
            } else {
                x = colPos - j;
                y = rowPos - j;
            }
            lineToCheck[j - 1][0] = y;
            lineToCheck[j - 1][1] = x;
        }
        return lineToCheck;
    }

    /**
     * <p>
     * Method to generate valid winning move combinations on a row basis.
     * </p>
     * 
     * @param rowPos    Integer representing the row of the last move.
     * @param colPos    Integer representing the column of the last move.
     * @param advancing Boolean representing if the window should advance along the
     *                  row.
     * @return A 2D array of Integer coordinates.
     */
    private Integer[][] generateRowLine(int rowPos, int colPos, boolean advancing) {
        Integer[][] lineToCheck = new Integer[3][2];
        for (int j = 1; j <= 3; j++) {
            int x;
            if (advancing) {
                x = colPos + j;
            } else {
                x = colPos - j;
            }
            int y = rowPos;
            lineToCheck[j - 1][0] = y;
            lineToCheck[j - 1][1] = x;
        }
        return lineToCheck;
    }

    /**
     * <p>
     * Method to generate valid winning move combinations on a column basis.
     * </p>
     * 
     * @param rowPos    Integer representing the row of the last move.
     * @param colPos    Integer representing the column of the last move.
     * @param advancing Boolean representing if the window should advance along the
     *                  column.
     * @return A 2D array of Integer coordinates.
     */
    private Integer[][] generateColLine(int rowPos, int colPos, boolean advancing) {
        Integer[][] lineToCheck = new Integer[3][2];
        for (int j = 1; j <= 3; j++) {
            int y;
            if (advancing) {
                y = rowPos + j;
            } else {
                y = rowPos - j;
            }
            int x = colPos;
            lineToCheck[j - 1][0] = y;
            lineToCheck[j - 1][1] = x;
        }
        return lineToCheck;
    }

    /**
     * <p>
     * Checks if the board is full updates the draw state to true if so.
     * </p>
     */
    private void checkDrawState() {
        char[][] board = this.board.getBoard();
        // Loop forwards over the rows looking for empty spaces
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                // If there is an empty space, there can't be a draw
                if (board[i][j] == ' ') {
                    this.isDraw = false;
                    return;
                }
            }
        }
        this.isDraw = true;
    }

    /**
     * <p>
     * Program main entry point. This should be run to play the game.
     * </p>
     * 
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        ConnectFour game = new ConnectFour();
        game.playGame(false);
    }
}
