import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ComputerPlayer extends Player {
    Display display;

    // Constructor
    ComputerPlayer(Display display, Board board, char colour) {
        super(board, colour);
        this.display = display;
        setName("The Computer");
    }

    @Override
    public void takeTurn(ArrayList<Integer[]> validMoves) {
        Random rand = new Random();
        int idx = rand.nextInt(validMoves.size());
        Integer[] move = validMoves.get(idx);
        this.board.updateBoard(move, this.colour);
        this.display.displayInfoMessage(getName() + " chose column " + move);
    }
}
