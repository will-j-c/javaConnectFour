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
            this.display.displayInfoMessage("Hit Ctrl + c to quit at any time");
            this.display.displayErrorMessage("");
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
                System.out.println(Arrays.deepToString(this.activePlayer.getLastMove()));
                // checkWinState(this.activePlayer.getColour());
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
    private void checkWinState(char colour) {

    }

    /**
     * 
     * @param colPos
     * @param rowPos
     * @return
     */
    private int[][][] toCheck(int colPos, int rowPos) {
        int[][][] toCheck = new int[4][3][2];
        for (int i = 1; i <= 4; i++) {
            if (i == 1) {
                for (int j = 1; j <= 3; j++) {
                    int x = colPos + j;
                    int y = rowPos + j;
                    toCheck[i - 1][j - 1][0] = y;
                    toCheck[i - 1][j - 1][1] = x;
                }
            }
            if (i == 2) {
                for (int j = 1; j <= 3; j++) {
                    int x = colPos + j;
                    int y = rowPos - j;
                    toCheck[i - 1][j - 1][0] = y;
                    toCheck[i - 1][j - 1][1] = x;
                }
            }
            if (i == 3) {
                for (int j = 1; j <= 3; j++) {
                    int x = colPos - j;
                    int y = rowPos + j;
                    toCheck[i - 1][j - 1][0] = y;
                    toCheck[i - 1][j - 1][1] = x;
                }
            }
            if (i == 4) {
                for (int j = 1; j <= 3; j++) {
                    int x = colPos - j;
                    int y = rowPos - j;
                    toCheck[i - 1][j - 1][0] = y;
                    toCheck[i - 1][j - 1][1] = x;
                }
            }
        }
        return toCheck;
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
