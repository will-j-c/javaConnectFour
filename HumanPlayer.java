import java.util.ArrayList;
import java.util.InputMismatchException;

/**
 * <p>
 * A class to represent a human player. Inherits from Player and adds additional
 * functionality around inputs and feedback on validity of moves.
 * </p>
 */
public class HumanPlayer extends Player {
    /**
     * <p>
     * Declaration of a variable to hold the player Input instance.
     * </p>
     */
    Input playerInput;
    /**
     * <p>
     * Declaration of a variable to hold the Display instance.
     * </p>
     */
    Display display;

    /**
     * <p>
     * Constructor to initialise a new player.
     * </p>
     * 
     * @param playerInput An instance of the Input class.
     * @param display     The Display instance.
     * @param board       The board Instance.
     * @param colour      The player colour character.
     */
    HumanPlayer(Input playerInput, Display display, Board board, char colour) {
        super(board, colour);
        this.playerInput = playerInput;
        this.display = display;
    }

    /**
     * <p>
     * A method that takes in a list of valid moves, validates user input of a valid
     * column and sets this.lastMove.
     * </p>
     * 
     * @param validMoves ArrayList of valid coordinates.
     */
    @Override
    public void takeTurn(ArrayList<Integer[]> validMoves) {
        boolean validMove = false;
        ArrayList<Integer> validCols = validCols(validMoves);
        String colour = getColour() == 'r' ? "red" : "yellow";
        this.display.displayInfoMessage(getName() + " , you are " + colour);
        this.display.displayInfoMessage("Please enter your move:");

        try {
            while (!validMove) {
                int selection = this.playerInput.intInput();
                if (validCols.contains(selection - 1)) {
                    validMoves.forEach(coord -> {
                        if (coord[1] == (selection - 1)) {
                            this.lastMove = coord;
                            this.board.updateBoard(this.lastMove, getColour());
                        }
                    });
                    validMove = true;
                } else {
                    this.display.displayErrorMessage("Please enter a valid column number to play");
                }
            }
        } catch (InputMismatchException e) {
            this.display.displayErrorMessage("Please enter a valid column number to play");
            this.playerInput.next();
            takeTurn(validMoves);
        }
    }

    /**
     * <p>
     * Method to establish the valid columns from a list of valid positions.
     * </p>
     * 
     * @param validMoves ArrayList of valid coordinates.
     * @return An ArrayList of Integers representing the valid columns.
     */
    private ArrayList<Integer> validCols(ArrayList<Integer[]> validMoves) {
        ArrayList<Integer> validCols = new ArrayList<Integer>();
        for (int i = 0; i < validMoves.size(); i++) {
            validCols.add(validMoves.get(i)[1]);
        }
        validCols.sort(null);
        return validCols;
    }
}
