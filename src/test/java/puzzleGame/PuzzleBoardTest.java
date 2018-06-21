package puzzleGame;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class PuzzleBoardTest extends PuzzleTestBase{



    @Test
    private static Stream<Map<Integer, PuzzlePiece>> listProvider() {
        return Stream.of(firstInput, secondInput, thirdInput);
    }

    @Test
    private static Stream<Map<Integer, PuzzlePiece>> invalidListProvider() {
        return Stream.of(firstInvalidInput, secondInvalidInput);
    }

    @ParameterizedTest
    @MethodSource("listProvider")
    public void validPuzzleInputTest(Map<Integer, PuzzlePiece> input) throws IllegalPuzzleException {
        //PuzzleBoard board = new PuzzleBoard(input, new PuzzleErrors());
        //int[][] solotion = board.getBoard();
        PuzzleValidatorUtil c = new PuzzleValidatorUtil();
        //Assertions.assertTrue(c.isValidPuzzle(input, solotion));
    }

    @ParameterizedTest
    @MethodSource("invalidListProvider")
    public void invalidPuzzleInputTest(Map<Integer, PuzzlePiece> input) {
      /*  Assertions.assertThrows(IllegalPuzzleException.class,
                () -> new PuzzleBoard(input, new PuzzleErrors()).getBoard()
        );*/
    }
}
