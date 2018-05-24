package puzzleGame;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PuzzleBoard {


    public Map<Integer, PuzzlePiece> input;

    private PuzzlePiece[][] board;

    private List<Integer> possibleLinesAmount;

    private String outputFileName = "puzzleResult.txt";

    private void initBoard() {
        int piecesAmount = input.size();
        for (int i = 1; i <= piecesAmount / 2; i++) {
            if (piecesAmount % i == 0) {
                board = new PuzzlePiece[i][piecesAmount / i];
                if (solve()) {
                    break;
                }
            }
        }
    }

    public boolean solve() {
        return true;
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

        return true;
    }

    private void fillOutputFile(String content) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName, true));
        writer.append(content);
        writer.close();
    }


    // Added for TestPuzzleValidator unit tests
    public PuzzleBoard(){
        input = new HashMap<>();
    }


    // Added for TestPuzzleValidator unit tests
    public void addPiece(int id, int left, int top, int right, int bottom) {

        PuzzlePiece piece = new PuzzlePiece(left, top, right, bottom);
        input.put(id, piece);

    }

    // Added for TestPuzzleValidator unit tests
    public Map<Integer, PuzzlePiece> getInput() {
        return input;
    }
}
