/**
 * <p>
 * User input within a class is enabled by the class implementing Inputtable.
 * Classes that do not implement this interface cannot implement user input.
 * </p>
 */
public interface Inputtable {
    /**
     * <p>
     * Governs input of integers.
     * </p>
     * 
     * @return int input
     */
    public int intInput();

    /**
     * <p>
     * Governs input of Stings.
     * </p>
     * 
     * @return String input
     */
    public String stringInput();

    /**
     * <p>
     * Governs input of characters.
     * </p>
     * 
     * @return char input
     */
    public char charInput();

    /**
     * <p>
     * Calls the next line of the stream.
     * </p>
     */
    public void next();

    /**
     * <p>
     * Closes the stream.
     * </p>
     */
    public void close();
}
