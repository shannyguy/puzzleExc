package puzzleGame;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IllegalPuzzleException, IOException {
        Map<Integer, PuzzlePiece> piecesMap;
        
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Please enter file name: ");
            PuzzleGame puzzleGame = new PuzzleGame(scanner.next());
            String resultFilePath = puzzleGame.solve();

        }
    }

}