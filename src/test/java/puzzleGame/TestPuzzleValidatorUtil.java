package puzzleGame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestPuzzleValidatorUtil {

    private PuzzleValidatorUtil puzzleValidatorUtil = new PuzzleValidatorUtil();
    private static PuzzleBoard puzzleBoard = new PuzzleBoard();

    @BeforeEach
    public void init() {

        puzzleBoard.addPiece(1, 0, 0, -1, 0);
        puzzleBoard.addPiece(2, 1, 0, 0, 0);

    }

    @Test
    public static Stream<Arguments> PuzzleValidatorUtilHappyPath() {

        return Stream.of(
                Arguments.of(puzzleBoard.getInput(), "validSolution.txt")
        );
    }

    @ParameterizedTest
    @MethodSource("PuzzleValidatorUtilHappyPath")
    public void testPuzzleValidatorUtilHappyPath(Map<Integer, PuzzlePiece> piecesMap, String outputFilePath) throws IOException {
        assertTrue(puzzleValidatorUtil.isValidPuzzle(piecesMap, outputFilePath));
    }


    @Test
    public static Stream<Arguments> PuzzleValidatorUtilUnhappyPath() {

        return Stream.of(
                Arguments.of(puzzleBoard.getInput(), "invalidSolution.txt")
        );
    }

    @ParameterizedTest
    @MethodSource("PuzzleValidatorUtilUnhappyPath")
    public void testPuzzleValidatorUtilUnHappyPath(Map<Integer, PuzzlePiece> piecesMap, String outputFilePath) throws IOException {
        assertFalse(puzzleValidatorUtil.isValidPuzzle(piecesMap, outputFilePath));
    }
}
