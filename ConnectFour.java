import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * 
 */
public class ConnectFour {
    /**
     * 
     */
    Board board;
    /**
     * 
     */
    Display display;
    /**
     * 
     */
    Input playerOneInput;
    /**
     * 
     */
    Input playerTwoInput;
    /**
     * 
     */
    boolean isWin;
    /**
     * 
     */
    boolean isDraw;
    /**
     * 
     */
    Player playerOne;
    /**
     * 
     */
    Player playerTwo;
    /**
     * 
     */
    Player activePlayer;

    /**
     * 
     */
    public ConnectFour() {
        this.board = new Board();
        this.display = new Display();
        this.isWin = false;
        this.isDraw = false;
        this.playerOneInput = new Input();
    }

    /**
     * 
     * @param repeat
     */
    private void playGame(boolean repeat) {
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
    }

    /**
     * 
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
     * 
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
     * 
     * @return
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
     * 
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

            if (this.isDraw) {
                this.display.displayBoard(this.board.getBoard());
                this.display.displayInfoMessage("The game is a draw");
            }
        } finally {
            this.playerOneInput.close();
            if (!this.playerTwo.getName().equals("The Computer")) {
                this.playerTwoInput.close();
            }
        }
    }

    /**
     * 
     */
    private void toggleActivePlayer() {
        if (this.activePlayer.getName().equals(this.playerOne.getName())) {
            this.activePlayer = this.playerTwo;
        } else {
            this.activePlayer = this.playerOne;
        }
    }

    /**
     * 
     * @return
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
     * 
     * @param colour
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
     * 
     * @param colPos
     * @param rowPos
     * @return
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
     * 
     * @param line
     * @return
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
     * 
     * @param rowPos
     * @param colPos
     * @param increaseX
     * @param increaseY
     * @return
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
     * 
     * @param rowPos
     * @param colPos
     * @param advancing
     * @return
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
            Integer[] coord = new Integer[2];
            lineToCheck[j - 1][0] = y;
            lineToCheck[j - 1][1] = x;
        }
        return lineToCheck;
    }

    /**
     * 
     * @param rowPos
     * @param colPos
     * @param advancing
     * @return
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
            Integer[] coord = new Integer[2];
            lineToCheck[j - 1][0] = y;
            lineToCheck[j - 1][1] = x;
        }
        return lineToCheck;
    }

    /**
     * 
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
     * 
     * @param args
     */
    public static void main(String[] args) {
        ConnectFour game = new ConnectFour();
        game.playGame(false);
    }
}
