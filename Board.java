public class Board {
    private char[][] board;

    // Default constructor
    public Board() {
        this.board = initBoard(6, 7);
    }

    private char[][] initBoard (int cols, int rows) {
        char[][] board = new char[cols][rows];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j< board[i].length; j++) {
                board[i][j] = 'y';
            }
        }
        return board;
    }

    public char[][] getBoard() {
        return this.board;
    }
}
