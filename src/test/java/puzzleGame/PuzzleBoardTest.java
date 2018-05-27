package puzzleGame;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class PuzzleBoardTest {

    private static int id = 1;
    private static Map<Integer, PuzzlePiece> firstInput;
    private static Map<Integer, PuzzlePiece> secondInput;
    private static Map<Integer, PuzzlePiece> invalidInput;

    @BeforeAll
    public static void initTest(){
        String firstPieces = "0,0,0,1#0,-1,0,-1#1,1,0,0#-1,0,0,-1#0,-1,0,0#1,0,0,-1#0,0,-1,0#0,-1,1,1#1,-1,0,0#1,1,1,-1#-1,-1,0,1#1,0,1,-1#0,1,0,-1#-1,0,-1,0#0,-1,0,-1#1,-1,1,-1#1,0,-1,-1#0,0,1,0#0,0,1,-1#0,1,0,1#-1,0,1,1#0,0,1,1#0,1,1,-1#-1,0,0,0#0,1,1,0#-1,0,-1,0#0,1,-1,0#-1,1,-1,0#-1,-1,1,0#1,0,0,0";
        //String secondPieces = "0,-1,0,0#1,1,0,-1#-1,0,0,1#0,-1,0,0#1,1,1,-1#-1,0,-1,1#0,1,0,0#-1,0,1,0#0,0,-1,0";
        String secondPieces = "0,0,-1,0#-1,0,0,1#1,1,0,-1#0,-1,0,0#1,1,1,-1#-1,0,-1,1#0,1,0,0#-1,0,1,0#0,-1,0,0";
        String invalidPieces = "0,0,-1,0#-1,0,0,1#0,-1,0,0#1,1,1,-1#-1,0,-1,1#0,1,0,0#-1,0,1,0#0,-1,0,0";
        firstInput = new HashMap<Integer, PuzzlePiece>();
        secondInput = new HashMap<Integer, PuzzlePiece>();
        invalidInput = new HashMap<Integer, PuzzlePiece>();
        String [] firstPiecesArr = firstPieces.split("#");
        Arrays.stream(firstPiecesArr).forEach(piece-> addPiece(piece, firstInput));
        id = 1;
        String [] secondPiecesArr = secondPieces.split("#");
        Arrays.stream(secondPiecesArr).forEach(piece-> addPiece(piece, secondInput));
        id = 1;
        String [] invalidPiecesArr = invalidPieces.split("#");
        Arrays.stream(invalidPiecesArr).forEach(piece-> addPiece(piece, invalidInput));


    }

    private static void addPiece(String inputText, Map<Integer, PuzzlePiece> input){
        inputText = inputText.replace(" ", "");
        String[] inputSides = inputText.split(",");
        PuzzlePiece piece = new PuzzlePiece(Integer.valueOf(inputSides[0]), Integer.valueOf(inputSides[1]), Integer.valueOf(inputSides[2]), Integer.valueOf(inputSides[3]));
        input.put(id, piece);
        id++;
    }

    private static Stream<Map<Integer, PuzzlePiece>> listProvider() {
        return Stream.of(firstInput, secondInput);
    }
    private static Stream<Map<Integer, PuzzlePiece>> invalidListProvider() {
        return Stream.of(invalidInput);
    }

    @ParameterizedTest
    @MethodSource("listProvider")
    public void validPuzzleInputTest(Map<Integer, PuzzlePiece> input) throws IllegalPuzzleException {
        PuzzleBoard board = new PuzzleBoard(input);
        Assertions.assertNotNull(board.getBoard());

    }

    @ParameterizedTest
    @MethodSource("invalidListProvider")
    public void invalidPuzzleInputTest(Map<Integer, PuzzlePiece> input) throws IllegalPuzzleException {
        PuzzleBoard board = new PuzzleBoard(input);
        Assertions.assertNull(board.getBoard());

    }
}
