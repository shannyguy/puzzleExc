package puzzleGame;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Please enter file name: ");
            FileParser fileParser = new FileParser(scanner.next());
            fileParser.parse();
        }
    }

}