/**
 * 
 */
public class Display implements Displayable {
    /**
     * 
     */
    public static final String ANSI_RESET = "\u001B[0m";
    /**
     * 
     */
    public static final String ANSI_BOARD_BACKGROUND = "\u001B[46m";
    /**
     * 
     */
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    /**
     * 
     */
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    /**
     * 
     */
    public static final String ANSI_RED_TEXT = "\u001B[31m";
    /**
     * 
     */
    public static final String ANSI_GREEN_TEXT = "\u001B[32m";

    /**
     * 
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
                    String toPrint = spaceString(board[i][j], true);
                    System.out.print(toPrint);
                } else {
                    String toPrint = spaceString(board[i][j], false);
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
     * 
     * @param c
     * @param terminal
     * @return
     */
    private String spaceString(char c, boolean terminal) {
        if (c == 'y') {
            return ANSI_YELLOW_BACKGROUND + " " + c + " " + ANSI_RESET + ANSI_BOARD_BACKGROUND + "|" + ANSI_RESET;
        }
        if (c == 'r') {
            return ANSI_RED_BACKGROUND + " " + c + " " + ANSI_RESET + ANSI_BOARD_BACKGROUND + "|" + ANSI_RESET;
        }
        return ANSI_BOARD_BACKGROUND + " " + c + " " + ANSI_RESET + ANSI_BOARD_BACKGROUND + "|" + ANSI_RESET;
    }

    /**
     * 
     */
    @Override
    public void displayInfoMessage(String message) {
        System.out.println(ANSI_GREEN_TEXT + message + ANSI_RESET);
    }

    /**
     * 
     */
    @Override
    public void displayErrorMessage(String message) {
        System.out.println(ANSI_RED_TEXT + message + ANSI_RESET);
    }
}
