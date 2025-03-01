import java.util.Random;
import java.util.ArrayList;

public class RandAI implements Thinkable {
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
        Random rand = new Random();
        int idx = rand.nextInt(validMoves.size());
        Integer[] move = validMoves.get(idx);
        return move;
    }
}
