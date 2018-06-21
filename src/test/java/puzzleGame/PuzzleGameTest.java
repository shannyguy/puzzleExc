package puzzleGame;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;

public class PuzzleGameTest {

    @Test
    public void validPuzzleTest() throws IllegalPuzzleException, IOException {
        //PuzzleGame game = new PuzzleGame("validPuzzle20.txt", "validPuzzle20Results.txt");
        //String resultFileName = game.solve();
        PuzzleValidatorUtil validator = new PuzzleValidatorUtil();
        //Assertions.assertTrue(validator.isValidPuzzle(game.getInput(), resultFileName));
    }

    @Test
    public void sumOfEdgesNotZeroPuzzleTest() throws IllegalPuzzleException, IOException {
       // PuzzleGame game = new PuzzleGame("invalidPuzzle36.txt", "validPuzzle36Results.txt");
        //String resultFileName = game.solve();
        //BufferedReader reader = new BufferedReader(new FileReader(resultFileName));
        //Assertions.assertEquals("Cannot solve puzzle: wrong number of straight edges", reader.readLine());
        //Assertions.assertEquals("Cannot solve puzzle: missing corner element: <TL><TR>", reader.readLine());
        //Assertions.assertEquals("Cannot solve puzzle: sum of edges is not zero", reader.readLine());
        //Assertions.assertEquals("Cannot solve puzzle: sum of edges is not zero", reader.readLine());
    }

}
