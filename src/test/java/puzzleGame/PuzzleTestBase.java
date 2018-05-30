package puzzleGame;

import org.junit.jupiter.api.BeforeAll;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PuzzleTestBase {

    protected static int id = 1;
    protected static Map<Integer, PuzzlePiece> firstInput;
    protected static Map<Integer, PuzzlePiece> secondInput;
    protected static Map<Integer, PuzzlePiece> thirdInput;
    protected static Map<Integer, PuzzlePiece> forthInput;
    protected static Map<Integer, PuzzlePiece> firstInvalidInput;
    protected static Map<Integer, PuzzlePiece> secondInvalidInput;

    protected static Map<Integer, PuzzlePiece> validInputForValidator;
    protected static Map<Integer, PuzzlePiece> invalidValidInputForValidator;

    @BeforeAll
    public static void initTest() {
        String firstPieces = "1,0,-1,0#0,0,-1,1#0,0,1,1#0,0,1,-1#0,0,0,-1#0,1,0,1#-1,0,-1,0#0,0,1,0#0,1,0,-1#-1,1,0,1#-1,-1,0,1#-1,0,-1,-1#0,1,-1,0#-1,1,1,0#-1,1,1,0#1,-1,0,1#1,0,0,1#0,-1,1,-1#1,1,-1,-1#-1,-1,-1,0";
        //String secondPieces = "0,-1,0,0#1,1,0,-1#-1,0,0,1#0,-1,0,0#1,1,1,-1#-1,0,-1,1#0,1,0,0#-1,0,1,0#0,0,-1,0";
        String secondPieces = "0,0,-1,0#-1,0,0,1#1,1,0,-1#1,1,1,-1#0,-1,0,0#-1,0,-1,1#0,1,0,0#-1,0,1,0#0,-1,0,0";
        String thirdPieces = "0,1,0,1#0,0,0,-1#1,0,0,-1#0,1,-1,-1#-1,-1,1,-1#1,0,1,-1#0,0,1,1#0,1,1,-1#-1,0,1,0#0,1,-1,-1#-1,0,1,-1#-1,-1,0,-1#0,0,1,0#0,0,1,0#0,0,1,0";
        String forthPieces = "0,-1,0,0#1,0,0,0#0,-1,0,0#1,0,0,0";


        String firstInvalidPieces = "0,0,-1,0#-1,0,0,1#0,-1,0,0#1,1,1,-1#-1,0,-1,1#0,1,0,0#-1,0,1,0#0,-1,0,0";
        String secondInvalidPieces = "0,0,0,1#0,-1,0,-1#1,1,0,0#-1,0,0,-1#0,-1,1,0#1,0,0,-1#0,0,-1,0#0,-1,1,1#1,-1,0,0#1,1,1,-1#-1,-1,0,1#1,0,1,-1#0,1,0,-1#-1,0,-1,0#0,-1,0,-1#1,-1,1,-1#1,0,-1,-1#0,0,1,0#0,0,1,-1#0,1,0,1#-1,0,1,1#0,0,1,1#0,1,1,-1#-1,0,0,0#0,1,1,0#-1,0,-1,0#0,1,-1,0#-1,1,-1,0#-1,-1,1,0#1,0,0,0";

        firstInput = new HashMap<Integer, PuzzlePiece>();
        secondInput = new HashMap<Integer, PuzzlePiece>();
        thirdInput = new HashMap<Integer, PuzzlePiece>();
        forthInput = new HashMap<Integer, PuzzlePiece>();
        firstInvalidInput = new HashMap<Integer, PuzzlePiece>();
        secondInvalidInput = new HashMap<Integer, PuzzlePiece>();
        String[] firstPiecesArr = firstPieces.split("#");
        Arrays.stream(firstPiecesArr).forEach(piece -> addPiece(piece, firstInput));
        id = 1;
        String[] secondPiecesArr = secondPieces.split("#");
        Arrays.stream(secondPiecesArr).forEach(piece -> addPiece(piece, secondInput));
        id = 1;
        String[] thirdPiecesArr = thirdPieces.split("#");
        Arrays.stream(thirdPiecesArr).forEach(piece -> addPiece(piece, thirdInput));
        id = 1;
        String[] forthPiecesArr = forthPieces.split("#");
        Arrays.stream(forthPiecesArr).forEach(piece -> addPiece(piece, forthInput));
        id = 1;
        String[] firstInvalidPiecesArr = firstInvalidPieces.split("#");
        Arrays.stream(firstInvalidPiecesArr).forEach(piece -> addPiece(piece, firstInvalidInput));
        id = 1;
        String[] secondInvalidPiecesArr = secondInvalidPieces.split("#");
        Arrays.stream(secondInvalidPiecesArr).forEach(piece -> addPiece(piece, secondInvalidInput));

    }

    private static void addPiece(String inputText, Map<Integer, PuzzlePiece> input) {
        inputText = inputText.replace(" ", "");
        String[] inputSides = inputText.split(",");
        PuzzlePiece piece = new PuzzlePiece(Integer.valueOf(inputSides[0]), Integer.valueOf(inputSides[1]), Integer.valueOf(inputSides[2]), Integer.valueOf(inputSides[3]));
        input.put(id, piece);
        id++;
    }


}
