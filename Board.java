/**
 * <p>
 * This class keeps track of the state of the board and has methods to
 * initialise, get and update the board.
 * </p>
 */
public class Board {
    /**
     * <p>
     * Declare the 2D char array that will represent the board.
     * </p>
     */
    private char[][] board;

    /**
     * <p>
     * Constructor method that initialises the board.
     * </p>
     */
    public Board() {
        this.board = initBoard(6, 7);
    }

    /**
     * <p>
     * Method that generates the board with empty spaces.
     * </p>
     * 
     * @param cols Number of cols.
     * @param rows Number of rows.
     * @return Return a 2D char array to represent the board.
     */
    private char[][] initBoard(int cols, int rows) {
        char[][] board = new char[cols][rows];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = ' ';
            }
        }
        return board;
    }

    /**
     * <p>
     * Getter method that returns the current board.
     * </p>
     * 
     * @return 2D array of the current board.
     */
    public char[][] getBoard() {
        return this.board;
    }

    /**
     * <p>
     * Setter method that updates the board based on a given colour and coordinate.
     * </p>
     * 
     * @param pos    Coordinate array in format [row, column].
     * @param colour Char r or y.
     */
    public void updateBoard(Integer[] pos, char colour) {
        this.board[pos[0]][pos[1]] = colour;
    }

    /**
     * <p>
     * Method to return the colour of a given position.
     * </p>
     * 
     * @param position Coordinate array in format [row, column].
     * @return Char r or y (or an empty char).
     */
    public char getPieceAtPosition(Integer[] position) {
        return this.board[position[0]][position[1]];
    }
}
