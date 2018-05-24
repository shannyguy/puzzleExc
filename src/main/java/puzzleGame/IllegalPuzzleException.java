package puzzleGame;

public class IllegalPuzzleException extends Exception{

    public static String MISSING_PUZZLE_ELEMENTS = "Missing puzzle element(s) with the following IDs: %s";
    public static String WRONG_ELEMENT_IDS = "Puzzle of size <%s> cannot have the following IDs: %s";
    public static String WRONG_ELEMENT_FORMAT = "Puzzle ID <%s> has wrong data: <%s>";

    public IllegalPuzzleException() {}

    // Constructor that accepts a message
    public IllegalPuzzleException(String message)
    {
        super(message);
    }

}
