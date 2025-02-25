public class Board {
    private char[][] board;

    // Default constructor
    public Board() {
        this.board = initBoard(6, 7);
    }

    private char[][] initBoard(int cols, int rows) {
        char[][] board = new char[cols][rows];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = ' ';
            }
        }
        return board;
    }

    public char[][] getBoard() {
        return this.board;
    }

    public void updateBoard(Integer[] pos, char colour) {
        if (colour == 'x') {
            if (this.board[pos[0]][pos[1]] == ' ') {
                this.board[pos[0]][pos[1]] = 'x';
            }
        } else {
            this.board[pos[0]][pos[1]] = colour;
        }

    }

    public char getPieceAtPosition(Integer[] position) {
        return this.board[position[0]][position[1]];
    }

    public void removeX() {
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[i].length; j++) {
                if (board[i][j] == 'x') {
                    board[i][j] = ' ';
                }
            }
        }
    }
}
