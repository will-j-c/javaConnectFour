import java.util.ArrayList;

/**
 * 
 */
public abstract class Player {
    /**
     * 
     */
    String name;
    /**
     * 
     */
    char colour;
    /**
     * 
     */
    Board board;
    /**
     * 
     */
    Integer[] lastMove;

    /**
     * 
     * @param board
     * @param colour
     */
    public Player(Board board, char colour) {
        this.board = board;
        this.colour = colour;
    }

    /**
     * 
     * @param name
     * @return
     */
    public String setName(String name) {
        this.name = name;
        return this.name;
    }

    /**
     * 
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     * 
     * @return
     */
    public char getColour() {
        return this.colour;
    }

    /**
     * 
     * @return
     */
    public Integer[] getLastMove() {
        return this.lastMove;
    }

    /**
     * 
     * @param validMoves
     */
    public abstract void takeTurn(ArrayList<Integer[]> validMoves);
}
