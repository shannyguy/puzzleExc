package puzzleGame;

import org.junit.jupiter.api.Test;

import java.io.IOException;

public class PuzzleGameTest {

    @Test
    public void fff() throws IllegalPuzzleException {
        PuzzleGame game = new PuzzleGame("");
        try {
            game.solve();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
