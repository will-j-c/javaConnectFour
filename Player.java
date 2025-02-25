import java.util.ArrayList;

public abstract class Player {
    String name;
    char colour;
    Board board;
    Integer[] lastMove;

    // Constructor
    public Player(Board board, char colour) {
        this.board = board;
        this.colour = colour;
    }

    public String setName(String name) {
        this.name = name;
        return this.name;
    }

    public String getName() {
        return this.name;
    }

    public char getColour() {
        return this.colour;
    }

    public Integer[] getLastMove() {
        return this.lastMove;
    }

    public abstract void takeTurn(ArrayList<Integer[]> validMoves);
}
