package puzzleGame;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PuzzleValidatorUtilTest extends PuzzleTestBase{

    private PuzzleValidatorUtil puzzleValidatorUtil = new PuzzleValidatorUtil();

    @Test
    public static Stream<Arguments> puzzleValidatorUtilFileHappyPath() {
        return Stream.of(
                Arguments.of(forthInput, "validSolution.txt")
        );
    }

    @ParameterizedTest
    @MethodSource("puzzleValidatorUtilFileHappyPath")
    public void testPuzzleValidatorFileHappyPath(Map<Integer, PuzzlePiece> piecesMap, String outputFilePath) throws IOException {
        assertTrue(puzzleValidatorUtil.isValidPuzzle(piecesMap, outputFilePath));
    }


    @Test
    public static Stream<Arguments> puzzleValidatorFileUnhappyPath() {

        return Stream.of(
                Arguments.of(forthInput, "invalidSolution.txt")
        );
    }

    @ParameterizedTest
    @MethodSource("puzzleValidatorFileUnhappyPath")
    public void testPuzzleValidatorFileUnHappyPath(Map<Integer, PuzzlePiece> piecesMap, String outputFilePath) throws IOException {
        assertFalse(puzzleValidatorUtil.isValidPuzzle(piecesMap, outputFilePath));
    }


    @Test
    public static Stream<Arguments> puzzleValidatorWithArrayHappyPath() {
        return Stream.of(
                Arguments.of(forthInput, new int[][]{{1,2},{3,4}})
        );
    }

    @ParameterizedTest
    @MethodSource("puzzleValidatorWithArrayHappyPath")
    public void testPuzzleValidatorWithArrayHappyPath(Map<Integer, PuzzlePiece> piecesMap, int[][] solution) {
        assertTrue(puzzleValidatorUtil.isValidPuzzle(piecesMap, solution));
    }


    public static Stream<Arguments> puzzleValidatorWithArrayUnhappyPath() {
        return Stream.of(
                Arguments.of(forthInput, new int[][]{{2,1},{3,4}})
        );
    }

    @ParameterizedTest
    @MethodSource("puzzleValidatorWithArrayUnhappyPath")
    public void testPuzzleValidatorWithArrayUnhappyPath(Map<Integer, PuzzlePiece> piecesMap, int[][] solution) {
        assertFalse(puzzleValidatorUtil.isValidPuzzle(piecesMap, solution));
    }


}
