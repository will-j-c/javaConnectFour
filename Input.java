import java.util.Scanner;

/**
 * 
 */
public class Input implements Inputtable {
    /**
     * 
     */
    Scanner scanner;

    /**
     * 
     */
    public Input() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * 
     */
    @Override
    public int intInput() {
        int input = this.scanner.nextInt();
        next();
        return input;
    }

    /**
     * 
     */
    @Override
    public String stringInput() {
        String input = this.scanner.nextLine();
        return input;
    }

    /**
     * 
     */
    @Override
    public char charInput() {
        char input = this.scanner.next().charAt(0);
        next();
        return input;
    }

    /**
     * 
     */
    @Override
    public void next() {
        this.scanner.nextLine();
    }

    /**
     * 
     */
    @Override
    public void close() {
        this.scanner.close();
    }
}
