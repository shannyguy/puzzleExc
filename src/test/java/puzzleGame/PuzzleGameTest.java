package puzzleGame;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;

public class PuzzleGameTest {

    @Test
    public void validPuzzleTest() throws IllegalPuzzleException, IOException {
        PuzzleGame game = new PuzzleGame("validPuzzle4.txt");
        String resultFileName = game.solve();
        PuzzleValidatorUtil validator = new PuzzleValidatorUtil();
        Assertions.assertTrue(validator.isValidPuzzle(game.getInput(), resultFileName));
    }

    @Test
    public void sumOfEdgesNotZeroPuzzleTest() throws IllegalPuzzleException, IOException {
        PuzzleGame game = new PuzzleGame("invalidPuzzle36.txt");
        String resultFileName = game.solve();
        BufferedReader reader = new BufferedReader(new FileReader(resultFileName));
        Assertions.assertEquals("Cannot solve puzzle: sum of edges is not zero", reader.readLine());
        Assertions.assertEquals("Cannot solve puzzle: sum of edges is not zero", reader.readLine());
    }

}
