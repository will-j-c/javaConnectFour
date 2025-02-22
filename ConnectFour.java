public class ConnectFour {
    Board board;
    Display display;
    // Constructor
    public ConnectFour() {
        this.board = new Board();
        this.display = new Display();
    }

    public void playGame() {
        this.display.displayBoard(this.board.getBoard());
    }

    public static void main(String[] args) {
        ConnectFour game = new ConnectFour();
        game.playGame();
    }
}
