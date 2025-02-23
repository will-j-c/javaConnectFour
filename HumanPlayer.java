import java.util.ArrayList;
import java.util.InputMismatchException;

public class HumanPlayer extends Player {
    Input playerInput;
    Display display;

    // Constructor
    HumanPlayer(Input playerInput, Display display, Board board, char colour) {
        super(board, colour);
        this.playerInput = playerInput;
        this.display = display;
    }

    @Override
    public void takeTurn(ArrayList<Integer> validMoves) {
        boolean validMove = false;
        try {
            int selection = this.playerInput.intInput();
            while (!validMove) {
                if (validMoves.contains(selection)) {
                    this.board.updateBoard(selection, this.colour);
                    break;
                } else {
                    this.display.displayErrorMessage("Column " + selection + " is not valid. Please enter a valid column.");
                }
            }
        } catch (InputMismatchException e) {
            this.display.displayErrorMessage("Please enter a valid column number");
            this.playerInput.next();
            takeTurn(validMoves);
        }

    }
}
