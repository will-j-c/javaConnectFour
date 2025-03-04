/**
 * <p>
 * Class to deal with logic of printing the board and other messages to the
 * terminal.
 * </p>
 */
public class Display implements Displayable {
    /**
     * <p>
     * Final variables of ANSI colour codes that will print colours to the terminal
     * in Unix environments. Will not work with Windows according to documentation.
     * </p>
     */
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BOARD_BACKGROUND = "\u001B[46m";
    private static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    private static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    private static final String ANSI_RED_TEXT = "\u001B[31m";
    private static final String ANSI_GREEN_TEXT = "\u001B[32m";

    /**
     * <p>
     * Main method to display the board to the terminal.
     * </p>
     */
    @Override
    public void displayBoard(char[][] board) {
        // Print the board
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (j == 0) {
                    System.out.print(ANSI_BOARD_BACKGROUND + "|" + ANSI_RESET);
                }
                if (j == board[i].length - 1) {
                    String toPrint = spaceString(board[i][j]);
                    System.out.print(toPrint);
                } else {
                    String toPrint = spaceString(board[i][j]);
                    System.out.print(toPrint);
                }
            }
            System.out.println();
        }
        // Print board bottom
        for (int i = 0; i <= board.length; i++) {
            if (i == 0) {
                System.out.print(ANSI_BOARD_BACKGROUND + " " + ANSI_RESET);
            }
            System.out.print(ANSI_BOARD_BACKGROUND + "---" + " " + ANSI_RESET);
        }
        // Print line
        System.out.println();
        // Print column numbers
        for (int i = 0; i <= board.length; i++) {
            if (i == 0) {
                System.out.print(ANSI_BOARD_BACKGROUND + "  " + ANSI_RESET);
            }
            if (i == board.length) {
                System.out.print(ANSI_BOARD_BACKGROUND + (i + 1) + "  " + ANSI_RESET);
            } else {
                System.out.print(ANSI_BOARD_BACKGROUND + (i + 1) + "   " + ANSI_RESET);
            }
        }
        System.out.println();
    }

    /**
     * <p>
     * Method that prints the character in a space with appropriate colours.
     * </p>
     * 
     * @param colour A character r or y.
     * @return A string to be printed to the display.
     */
    private String spaceString(char colour) {
        if (colour == 'y') {
            return ANSI_YELLOW_BACKGROUND + " " + colour + " " + ANSI_RESET + ANSI_BOARD_BACKGROUND + "|" + ANSI_RESET;
        }
        if (colour == 'r') {
            return ANSI_RED_BACKGROUND + " " + colour + " " + ANSI_RESET + ANSI_BOARD_BACKGROUND + "|" + ANSI_RESET;
        }
        return ANSI_BOARD_BACKGROUND + " " + colour + " " + ANSI_RESET + ANSI_BOARD_BACKGROUND + "|" + ANSI_RESET;
    }

    /**
     * <p>
     * Method to display and info message to the terminal.
     * </p>
     */
    @Override
    public void displayInfoMessage(String message) {
        System.out.println(ANSI_GREEN_TEXT + message + ANSI_RESET);
    }

    /**
     * <p>
     * Method to display an error message to the terminal.
     * </p>
     */
    @Override
    public void displayErrorMessage(String message) {
        System.out.println(ANSI_RED_TEXT + message + ANSI_RESET);
    }
}
