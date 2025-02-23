import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;

public class ConnectFour {
    Board board;
    Display display;
    Input playerOneInput;
    Input playerTwoInput;
    boolean isWin;
    boolean isDraw;
    Player playerOne;
    Player playerTwo;
    Player activePlayer;

    // Constructor
    public ConnectFour() {
        this.board = new Board();
        this.display = new Display();
        this.isWin = false;
        this.isDraw = false;
        this.playerOneInput = new Input();
    }

    private void playGame(boolean repeat) {
        if (!repeat) {
            this.display.displayInfoMessage("Welcome to Connect 4!!");
            this.display.displayInfoMessage("Hit Ctrl + c to quit at any time");
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

    private void onePlayerGame() {
        // this.playerOne = new HumanPlayer(this.playerOneInput, this.display,
        // this.board);
        // this.playerTwo = new ComputerPlayer();
    }

    private void twoPlayerGame() {
        this.display.displayInfoMessage("Player 1, please choose your colour (\"r\" or \"y\"):");
        char playerOneColour = this.playerOneInput.charInput();
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

    private void gameLoop() {
        try {
            while (!this.isWin && !this.isDraw) {
                this.display.displayBoard(this.board.getBoard());
                ArrayList<Integer> validMoves = getValidMoves();
                this.activePlayer.takeTurn(validMoves);
                checkWinState(this.activePlayer.getColour());
                checkDrawState();
                if (!this.isWin && !this.isDraw) {
                    this.toggleActivePlayer();
                }
            }
            if (this.isWin) {
                this.display.displayBoard(this.board.getBoard());
                this.display.displayInfoMessage(this.activePlayer.getName() + " wins!!!!");
            }
            if (this.isDraw) {
                this.display.displayBoard(this.board.getBoard());
                this.display.displayInfoMessage("The game is a draw");
            }
        } finally {
            this.playerOneInput.close();
            this.playerTwoInput.close();
        }
    }

    private void toggleActivePlayer() {
        if (this.activePlayer.getName().equals(this.playerOne.getName())) {
            this.activePlayer = this.playerTwo;
        } else {
            this.activePlayer = this.playerOne;
        }
    }

    private ArrayList<Integer> getValidMoves() {
        ArrayList<Integer> validMoves = new ArrayList<Integer>();
        char[][] board = this.board.getBoard();
        // Loop backwards over array to get lowest row first
        for (int i = board.length - 1; i >= 0; i--) {
            for (int j = 0; j < board[i].length; j++) {
                // If the column is empty and not already in the list
                if (board[i][j] == ' ') {
                    if (!validMoves.contains(j + 1)) {
                        validMoves.add(j + 1);
                    }
                }
            }
        }
        return validMoves;
    }

    private void checkWinState(char colour) {
        checkFourInARow(colour);
        checkFourInACol(colour);
        checkFourInADiag(colour);
    }

    private void checkFourInARow(char colour) {
        char[][] board = this.board.getBoard();
        int count = 0;
        for (int i = board.length - 1; i >= 0; i--) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == colour) {
                    count += 1;
                    if (count == 4) {
                        this.isWin = true;
                        return;
                    }
                } else {
                    count = 0;
                }
            }
        }
    }

    private void checkFourInACol(char colour) {
        char[][] board = this.board.getBoard();
        int count = 0;
        // Loop through the rows, from end (i.e. bottom of board)
        for (int i = board.length - 1; i >= 0; i--) {
            // Loop through the columns
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == colour) {
                    count += 1;
                    int[][] toCheck = toCheckFourInACol(j, i);
                    // Loop through the coordinates to check if they are all the same colour
                    for (int[] coords : toCheck) {
                        try {
                            if (board[coords[0]][coords[1]] == colour) {
                                count += 1;
                            } else {
                                count = 0;
                                break;
                            }
                        } catch (IndexOutOfBoundsException e) {
                            // If the index is out of range, there is not 4 so exit the loop of toCheck and
                            // continue
                            break;
                        }
                    }
                    if (count == 4) {
                        this.isWin = true;
                        return;
                    }
                } else {
                    count = 0;
                }
            }
        }
    }

    private int[][] toCheckFourInACol(int colPos, int rowPos) {
        int[][] toCheck = new int[3][2];
        for (int i = 1; i <= 3; i++) {
            int x = colPos;
            int y = rowPos - i;
            toCheck[i - 1][0] = y;
            toCheck[i - 1][1] = x;
        }
        return toCheck;
    }

    private void checkFourInADiag(char colour) {
        char[][] board = this.board.getBoard();
        int count = 0;
        for (int i = board.length - 1; i >= 0; i--) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == colour) {
                    count += 1;
                    int[][][] toCheck = toCheckFourInADiag(j, i);
                    System.out.println(count);
                    // Loop through each check to make
                    for (int k = 0; k < toCheck.length; k++) {
                        for (int[] coords : toCheck[k]) {
                            try {
                                if (board[coords[0]][coords[1]] == colour) {
                                    count += 1;
                                } else {
                                    count = 0;
                                    break;
                                }
                            } catch (IndexOutOfBoundsException e) {
                                // Do nothing, continue to check
                            }
                        }
                    }

                    if (count == 4) {
                        this.isWin = true;
                        return;
                    }
                } else {
                    count = 0;
                }
            }
        }
    }

    private int[][][] toCheckFourInADiag(int colPos, int rowPos) {
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

    public static void main(String[] args) {
        ConnectFour game = new ConnectFour();
        game.playGame(false);
    }
}
