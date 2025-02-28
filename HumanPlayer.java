import java.util.ArrayList;
import java.util.InputMismatchException;

/**
 * 
 */
public class HumanPlayer extends Player {
    /**
     * 
     */
    Input playerInput;
    /**
     * 
     */
    Display display;

    /**
     * 
     * @param playerInput
     * @param display
     * @param board
     * @param colour
     */
    HumanPlayer(Input playerInput, Display display, Board board, char colour) {
        super(board, colour);
        this.playerInput = playerInput;
        this.display = display;
    }

    /**
     * 
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
     * 
     * @param validMoves
     * @return
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
