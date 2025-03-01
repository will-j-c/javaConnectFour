import java.util.Scanner;

/**
 * <p>
 * Implements input from the keyboard on the command line.
 * </p>
 */
public class Input implements Inputtable {
    /**
     * <p>
     * Declaration of the scanner to stream user input.
     * </p>
     */
    private Scanner scanner;

    /**
     * <p>
     * Constructor class that initialises a Scanner to read user input stream.
     * </p>
     */
    public Input() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * <p>
     * Method that collects an integer input from the user.
     * </p>
     * 
     * @return Returns an integer input.
     */
    @Override
    public int intInput() {
        int input = this.scanner.nextInt();
        next();
        return input;
    }

    /**
     * <p>
     * Method that collects a string input from the user.
     * </p>
     * 
     * @return Returns an String input.
     */
    @Override
    public String stringInput() {
        String input = this.scanner.nextLine();
        return input;
    }

    /**
     * <p>
     * Method that collects a character input from the user.
     * </p>
     * 
     * @return Returns an char input.
     */
    @Override
    public char charInput() {
        char input = this.scanner.next().charAt(0);
        next();
        return input;
    }

    /**
     * <p>
     * Method that moves the carriage to the next line.
     * </p>
     */
    @Override
    public void next() {
        this.scanner.nextLine();
    }

    /**
     * <p>
     * Method to close the stream.
     * </p>
     */
    @Override
    public void close() {
        this.scanner.close();
    }
}
