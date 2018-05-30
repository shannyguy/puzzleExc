package puzzleGame;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class PuzzleGameTest {

    @Test
    public void fff() throws IllegalPuzzleException, IOException {
        PuzzleGame game = new PuzzleGame("validPuzzle36.txt");
        String resultFileName = game.solve();
        PuzzleValidatorUtil validator = new PuzzleValidatorUtil();
        Assertions.assertTrue(validator.isValidPuzzle(game.getInput(), resultFileName));
    }
}
