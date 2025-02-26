import java.util.ArrayList;
import java.util.Random;

/**
 * A class to represent the computer player. Currently takes a random move.
 */
public class ComputerPlayer extends Player {
    /**
     * <p>
     * Declaration of the display variable.
     * </p>
     */
    Display display;

    /**
     * <p>
     * Constructor method that initialises the computer player using an instance of
     * the board and display classes and is assigned a colour.
     * </p>
     * 
     * @param display An instance of the Display class initialised by the
     *                ConnectFour game.
     * @param board   An instance of the Board class initialised by the ConnectFour
     *                game.
     * @param colour  A char representing yellow or red.
     */
    ComputerPlayer(Display display, Board board, char colour) {
        super(board, colour);
        this.display = display;
        setName("The Computer");
    }

    /**
     * <p>
     * Implementation of the abstract takeTurn method inherited from Player. Defines
     * the logic on taking a turn. Records output in lastTurn.
     * </p>
     */
    @Override
    public void takeTurn(ArrayList<Integer[]> validMoves) {
        Random rand = new Random();
        int idx = rand.nextInt(validMoves.size());
        Integer[] move = validMoves.get(idx);
        this.lastMove = move;
        this.board.updateBoard(move, this.colour);
        this.display.displayInfoMessage(getName() + " chose column " + (move[1] + 1));
    }
}
