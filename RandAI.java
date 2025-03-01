import java.util.Random;
import java.util.ArrayList;

public class RandAI implements Thinkable {
    /**
     * <p>
     * Declaration of a variable that will implement the Random java util class.
     * </p>
     */
    private Random rand;

    /**
     * <p>
     * Constructor that initialises the AI instance.
     * </p>
     */
    public RandAI() {
        this.rand = new Random();
    }

    /**
     * <p>
     * A method that selects a valid move from a list of valid moves using random
     * logic.
     * </p>
     * 
     * @param validMoves ArrayList of valid coordinates.
     * @return A integer array in the format [row, column].
     */
    @Override
    public Integer[] selectMove(ArrayList<Integer[]> validMoves) {
        int idx = this.rand.nextInt(validMoves.size());
        Integer[] move = validMoves.get(idx);
        return move;
    }
}
