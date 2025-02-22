import java.util.Scanner;

public class Input implements Inputtable {
    Scanner scanner;

    // Constructor
    public Input() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public int intInput() {
        int input = this.scanner.nextInt();
        return input;
    }

    @Override
    public String stringInput() {
        String input = this.scanner.nextLine();
        return input;
    }

    @Override
    public void next() {
        this.scanner.nextLine();
    }

    @Override
    public void close() {
        this.scanner.close();
    }
}
