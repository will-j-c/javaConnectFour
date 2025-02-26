/**
 * <p>
 * Displaying of a class is enabled by the class implementing Displayable.
 * Classes that do not implement this interface cannot have the board or
 * messages displayed.
 * </p>
 */
public interface Displayable {
    /**
     * <p>
     * To display the board.
     * </p>
     * 
     * @param board A 2D array representing the board.
     */
    public void displayBoard(char[][] board);

    /**
     * <p>
     * To display an info message.
     * </p>
     * 
     * @param message A string of the message to display.
     */
    public void displayInfoMessage(String message);

    /**
     * <p>
     * To display an error message.
     * </p>
     * 
     * @param message A string of the message to display.
     */
    public void displayErrorMessage(String message);
}
