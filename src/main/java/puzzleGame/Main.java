package puzzleGame;

import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Map<Integer, PuzzlePiece> piecesMap;
        
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Please enter file name: ");
            FileParser fileParser = new FileParser(scanner.next());
            piecesMap = fileParser.parse();
            System.out.println(piecesMap);
        }
    }

}