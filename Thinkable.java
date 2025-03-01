import java.util.ArrayList;
/**
 * <p>
 * AI moves within a class are implemented through the Thinkable interface.
 * Classes that do not implement this interface cannot select moves.
 * </p>
 */
public interface Thinkable {
    /**
     * <p>
     * A method that selects a valid move from a list of valid moves.
     * </p>
     * 
     * @param validMoves ArrayList of valid coordinates.
     * @return A integer array in the format [row, column]
     */
    public Integer[] selectMove(ArrayList<Integer[]> validMoves);
}
