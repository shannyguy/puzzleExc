package puzzleGame;

import java.util.ArrayList;
import java.util.List;

public class PuzzleErrors {
    public static String WRONG_NUMBER_OF_ELEMEMNTS_VALUE = "Illegal number of elements value in file: %s";
    public static String MISSING_PUZZLE_ELEMENTS = "Missing puzzle element(s) with the following IDs: %s";
    public static String WRONG_ELEMENT_IDS = "Puzzle of size <%s> cannot have the following IDs: %s";
    public static String WRONG_ELEMENT_FORMAT = "Puzzle ID <%s> has wrong data: <%s>";
    public static String WRONG_NUMBER_OF_STRAIGHT_EDGES = "Cannot solve puzzle: wrong number of straight edges";
    public static String MISSING_CORNERS_ERROR = "Cannot solve puzzle: missing corner element: %s";
    public static String SUM_OF_EDGES_NOT_ZERO = "Cannot solve puzzle: sum of edges is not zero";
    public static String CANNOT_SOLVE_PUZZLE = "Cannot solve puzzle: it seems that there is no proper solution";

    private List<String> errorsList = new ArrayList<String>();

    public void addError(String error){
        errorsList.add(error);
    }

    public List<String> getErrorsList(){
        return errorsList;
    }

    public void clearErrors(){
        errorsList.clear();
    }
}
