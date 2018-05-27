package puzzleGame;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class PuzzleBoard {

    public Map<Integer, PuzzlePiece> input;

    public  Map<Integer, PuzzlePiece> piecesInUse;

    private List<int[]> possibleDimensions;

    private int currentRowsAmount;

    private int[][] board;



    private String outputFileName = "puzzleResult.txt";

    // Added for TestPuzzleValidator unit tests
    public PuzzleBoard(Map<Integer, PuzzlePiece> pieces)  {
        input = pieces;
    }

    public int[][] getBoard() throws IllegalPuzzleException {
        validateStraightEdgesAmount();
        validateAllCornersExist();
        validateSumOfEdgesIsZero();
        if(!PuzzleErrors.getErrorsList().isEmpty()){
            StringBuilder builder = new StringBuilder();
            for (String error : PuzzleErrors.getErrorsList()){
                builder.append(error + '\n');
            }
            PuzzleErrors.clearErrors();
            throw new IllegalPuzzleException(builder.toString());
        }
        boolean solved = findSolution();
        return board;
    }


    private boolean findSolution(){
        boolean solved = false;
        piecesInUse = new HashMap<Integer, PuzzlePiece>();
        for(int[] possibleDimension: possibleDimensions){
            for (Map.Entry<Integer, PuzzlePiece> entry : piecesInUse.entrySet()) {
                input.put(entry.getKey(), entry.getValue());
                piecesInUse.remove(entry);
            }
            currentRowsAmount = possibleDimension[0];
                board = new int [possibleDimension[0]] [possibleDimension[1]];
                solve(0,0);
                if(board != null){
                    solved = true;
                    break;
                }
        }
        return solved;
    }

    private void solve(int row, int column){
        int match = findMatch(row, column);
        if(match == -1){
            if(row == 0 && column == 0){
                board = null;
                return;
            }else{

                solve(column == 0 ? row - 1: row, column == 0 ? 0 : column - 1);
            }
        }else{
            board[row][column] = match;
            boolean lastColumn = board[0].length == column + 1;
            boolean lastRow = row + 1 == currentRowsAmount;

            if(lastColumn && lastRow){
                return;
            }else{
                solve(lastColumn ? row + 1 : row, lastColumn ? 0 : column + 1);
            }
        }


    }

    private int defineRequiredLeftSide(int rowIndex, int columnIndex){
        int neighbourRight = piecesInUse.get(board[rowIndex][columnIndex - 1]).getRight();
        return Integer.compare(0, neighbourRight);
    }

    private int findMatch(int row, int column){
        int currentValue = board[row][column];
        clearRemoved(row, column);
        int requiredLeft = column == 0  ? 0 : defineRequiredLeftSide(row, column);
        int requiredTop = row == 0 ? 0 : defineRequiredToptSide(row, column);
        for (int i = currentValue + 1 ; i <= input.size() + piecesInUse.size(); i++){
            PuzzlePiece piece = input.get(i);
            if(piece != null){
                if(piece.getLeft() == requiredLeft && piece.getTop() == requiredTop){
                    piecesInUse.put(i, input.remove(i));
                    return i;
                }
            }

        }
        return -1;
    }

    private void clearRemoved(int row, int column){
        for(int i = column; i < board[row].length; i++){
            int currentValue = board[row][i];
            input.put(currentValue, piecesInUse.remove(currentValue));
            board[row][i] = 0;
        }

    }

    private int defineRequiredToptSide(int rowIndex, int columnIndex){
        int neighbourBottom = piecesInUse.get(board[rowIndex - 1][columnIndex]).getBottom();
        return Integer.compare(0, neighbourBottom);
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
        possibleDimensions = new ArrayList<int[]>();
        int[] edgesAmount = countStraightEdges();
        for (int i = 1; i <= piecesAmount; i++) {
            if (piecesAmount % i == 0) {
                int j = piecesAmount / i;
                if(edgesAmount[0] >= i && edgesAmount[1] >= i && edgesAmount[2] >= j && edgesAmount[3] >= j){
                    possibleDimensions.add(new int[]{i,j});
                }
            }
        }
        if(possibleDimensions.isEmpty()){
            PuzzleErrors.addError(PuzzleErrors.WRONG_NUMBER_OF_STRAIGHT_EDGES);
            return false;
        }
        return true;
    }

    private int[] countStraightEdges(){
        int straightTop = 0;
        int straightBottom = 0;
        int straightRight = 0;
        int straightLeft = 0;
        for(Map.Entry<Integer, PuzzlePiece> entry : input.entrySet()){
            PuzzlePiece piece = entry.getValue();
            straightTop = piece.getTop() == 0 ? straightTop + 1 : straightTop;
            straightBottom = piece.getBottom() == 0 ? straightBottom + 1 : straightBottom;
            straightLeft = piece.getLeft() == 0 ? straightLeft + 1 : straightLeft;
            straightRight = piece.getRight() == 0 ? straightRight + 1 : straightRight;
        }
        return new int[] {straightTop, straightBottom, straightRight, straightLeft};
    }

    private boolean validateAllCornersExist(){
        boolean topLeftExist = false;
        boolean topRightExist = false;
        boolean bottomLeftExist = false;
        boolean bottomRightExist = false;
        for (Map.Entry<Integer, PuzzlePiece> entry : input.entrySet())
        {
            PuzzlePiece piece = entry.getValue();
            topLeftExist = topLeftExist || (piece.getLeft() == 0 && piece.getTop() == 0) ? true : false;
            topRightExist = topRightExist || (piece.getRight() == 0 && piece.getTop() == 0) ? true : false;
            bottomLeftExist = bottomLeftExist || (piece.getLeft() == 0 && piece.getBottom() == 0) ? true : false;
            bottomRightExist = bottomRightExist || (piece.getRight() == 0 && piece.getBottom() == 0) ? true : false;
        }
        StringBuilder builder = new StringBuilder();
        if(!topLeftExist){
            builder.append("<TL>");
        }
        if(!topRightExist){
            builder.append("<TR>");
        }
        if(!bottomLeftExist){
            builder.append("<BL>");
        }
        if(!bottomRightExist){
            builder.append("<BR>");
        }
        if(builder.length() != 0)  {
            PuzzleErrors.addError(String.format(PuzzleErrors.MISSING_CORNERS_ERROR, builder));
            return false;
        }
        return true;
    }

    private boolean validateSumOfEdgesIsZero(){
        int sumOfTop = 0;
        int sumOfBottom = 0;
        int sumOfLeft = 0;
        int sumOfRight = 0;
        for(Map.Entry<Integer, PuzzlePiece> entry : input.entrySet()){
            PuzzlePiece piece = entry.getValue();
            sumOfTop = sumOfTop + piece.getTop();
            sumOfBottom = sumOfBottom + piece.getBottom();
            sumOfLeft = sumOfLeft + piece.getLeft();
            sumOfRight = sumOfRight + piece.getRight();
        }
        return sumOfTop == 0 && sumOfBottom == 0 && sumOfLeft == 0 && sumOfRight == 0;
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
