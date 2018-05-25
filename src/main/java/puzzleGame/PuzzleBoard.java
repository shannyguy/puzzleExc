package puzzleGame;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PuzzleBoard {

    public Map<Integer, PuzzlePiece> input;

    private List<int[]> possibleLinesAmount;

    private PuzzlePiece[][] board;



    private String outputFileName = "puzzleResult.txt";

    // Added for TestPuzzleValidator unit tests
    public PuzzleBoard(Map<Integer, PuzzlePiece> pieces){


    }

    public PuzzlePiece[][] getBoard() {
        return board;
    }

    private boolean validateInput() {
        StringBuilder errors = new StringBuilder();
        List<Integer> missingIds = validatePartsIds();
        return true;
    }

    private List<Integer> validatePartsIds() {
        List<Integer> missingIds = new ArrayList<Integer>();
        for (int i = 1; i < input.size(); i++) {
            if (input.get(i) == null) {
                missingIds.add(i);
            }
        }
        return missingIds;
    }

    private List<Integer> validateIdsInRange() {
        List<Integer> notInRangeIds = new ArrayList<Integer>();
        for (Map.Entry<Integer, PuzzlePiece> entry : input.entrySet()) {
            int id = entry.getKey();
            if (id < 0 || id > input.size()) {
                notInRangeIds.add(id);
            }
        }
        return notInRangeIds;
    }

    private boolean validateStraightEdgesAmount(){
        int piecesAmount = input.size();
        possibleLinesAmount = new ArrayList<int[]>();
        int[] edgesAmount = countStraightEdges();
        for (int i = 1; i <= piecesAmount; i++) {
            if (piecesAmount % i == 0) {
                int j = piecesAmount / i;
                if(edgesAmount[0] >= i && edgesAmount[1] >= i && edgesAmount[2] >= j && edgesAmount[3] >= j){
                    possibleLinesAmount.add(new int[]{i,j});
                }
            }
        }
        return !possibleLinesAmount.isEmpty();
    }

    private int[] countStraightEdges(){
        int straightTop = 0;
        int straightBottom = 0;
        int straightRight = 0;
        int straightLeft = 0;
        for(Map.Entry<Integer, PuzzlePiece> entry : input.entrySet()){
            PuzzlePiece piece = entry.getValue();
            straightTop = piece.getTop() == 0 ? straightTop ++ : straightTop;
            straightBottom = piece.getBottom() == 0 ? straightBottom ++ : straightBottom;
            straightLeft = piece.getLeft() == 0 ? straightLeft ++ : straightLeft;
            straightRight = piece.getRight() == 0 ? straightRight ++ : straightRight;
        }
        return new int[] {straightTop, straightBottom, straightRight, straightLeft};
    }





    private void fillOutputFile(String content) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName, true));
        writer.append(content);
        writer.close();
    }
    // Added for TestPuzzleValidator unit tests
    public void addPiece(int id, int left, int top, int right, int bottom) {

        PuzzlePiece piece = new PuzzlePiece(left, top, right, bottom);
        input.put(id, piece);

    }
}
