import java.util.ArrayList;

/**
 * <p>
 * Abstract class for the implementation of player logic. Cannot be directly
 * implemented.
 * </p>
 */
public abstract class Player {
    /**
     * <p>
     * Declaration of name variable.
     * </p>
     */
    String name;
    /**
     * <p>
     * Declaration of colour variable.
     * </p>
     */
    char colour;
    /**
     * <p>
     * Declaration of board variable.
     * </p>
     */
    Board board;
    /**
     * <p>
     * Declaration of the last move variable.
     * </p>
     */
    Integer[] lastMove;

    /**
     * <p>
     * <Constructor that initialises the board instance and colour of the player./p>
     * 
     * @param board  A board instance.
     * @param colour A character r or y representing red and yellow.
     */
    public Player(Board board, char colour) {
        this.board = board;
        this.colour = colour;
    }

    /**
     * <p>
     * Method to set the name of the player.
     * </p>
     * 
     * @param name A String representing the name of the player.
     * @return The string of the name.
     */
    public String setName(String name) {
        this.name = name;
        return this.name;
    }

    /**
     * <p>
     * Method to to get the name of the player.
     * </p>
     * 
     * @return name String.
     */
    public String getName() {
        return this.name;
    }

    /**
     * <p>
     * Method to get the colour that the player is playing.
     * </p>
     * 
     * @return Colour character.
     */
    public char getColour() {
        return this.colour;
    }

    /**
     * <p>
     * Method that gets the last move the player made.
     * </p>
     * 
     * @return Coordinate Integer array in format [row, column]
     */
    public Integer[] getLastMove() {
        return this.lastMove;
    }

    /**
     * <p>
     * Abstract method that must be implemented. Takes an ArrayList of valid moves
     * and should update this.lastMove with an Integer[].
     * </p>
     * 
     * @param validMoves ArrayList of valid coordinates.
     */
    public abstract void takeTurn(ArrayList<Integer[]> validMoves);
}
