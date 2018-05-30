package puzzleGame;

import java.util.*;

public class PuzzleBoard {

    public Map<Integer, PuzzlePiece> input;

    public  Map<Integer, PuzzlePiece> piecesInUse;

    private List<int[]> possibleDimensions;

    private int currentRowsAmount;

    private int[][] board;





    // Added for TestPuzzleValidator unit tests
    public PuzzleBoard(Map<Integer, PuzzlePiece> pieces)  {
        input = pieces;
    }

    public int[][] getBoard() throws IllegalPuzzleException {
        validateInput();
        if(!PuzzleErrors.getErrorsList().isEmpty()){
            StringBuilder builder = new StringBuilder();
            for (String error : PuzzleErrors.getErrorsList()){
                builder.append(error + '\n');
            }
            PuzzleErrors.clearErrors();
            throw new IllegalPuzzleException(builder.toString());
        }
        if(findSolution()){
            return board;
        }else{
            throw new IllegalPuzzleException(PuzzleErrors.CANNOT_SOLVE_PUZZLE);
        }

    }


    private boolean findSolution(){
        boolean solved = false;
        for(int[] possibleDimension: possibleDimensions){
            currentRowsAmount = possibleDimension[0];
                board = new int [possibleDimension[0]] [possibleDimension[1]];
                piecesInUse = new HashMap<>();
                boolean success = solve(0, 0, piecesInUse);
                if(success){
                    solved = success;
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

    private  boolean solve(int row, int column, Map<Integer, PuzzlePiece> piecesInUse){
        int id = 1;
        int requiredLeft = column == 0  ? 0 : Integer.compare(0, input.get( board[row][column - 1]).getRight());;
        int requiredTop = row == 0 ? 0 : Integer.compare(0, input.get( board[row - 1][column]).getBottom());
        boolean requiredFlatRight = column + 1 == board[0].length;
        boolean requiredFlatBottom = row + 1 == currentRowsAmount;
        for (id = 1; id <= input.size(); id++){
            PuzzlePiece piece = piecesInUse.get(id);
            if(piece == null){
                piece = input.get(id);
                if((piece.getLeft() == requiredLeft) &&
                        (piece.getTop() == requiredTop) &&
                        (requiredFlatRight ? piece.getRight() == 0: true) &&
                        (requiredFlatBottom ? piece.getBottom() == 0: true)) {
                    board[row][column] = id;
                    piecesInUse.put(id, piece);
                    System.out.println("set " + id + " in place " + row + " " + column);
                    if (piecesInUse.size() == input.size()) {
                        return true;
                    }
                    if(!solve(column + 1 == board[0].length ? row + 1 : row, column+ 1 == board[0].length ? 0 : column + 1, piecesInUse)){
                        piecesInUse.remove(id);
                        continue;
                    }else{
                        return true;
                    }


                }
            }
        }

        return false;
    }


    private int findMatch(int row, int column){
        int currentValue = board[row][column];
        clearRemoved(row, column);
        int requiredLeft = column == 0  ? 0 : defineRequiredLeftSide(row, column);
        int requiredTop = row == 0 ? 0 : defineRequiredToptSide(row, column);
        boolean requiredFlatRight = column + 1 == board[0].length;
        boolean requiredFlatBottom = row + 1 == currentRowsAmount;
        for (int i = currentValue + 1 ; i <= input.size() + piecesInUse.size(); i++){
            PuzzlePiece piece = input.get(i);
            if(piece != null){
                if(piece.getLeft() == requiredLeft && piece.getTop() == requiredTop && requiredFlatRight ? piece.getRight() == 0: true && requiredFlatBottom ? piece.getBottom() == 0: true){
                    piecesInUse.put(i, input.remove(i));
                    return i;
                }
            }

        }
        return -1;
    }

    private int defineRequiredLeftSide(int rowIndex, int columnIndex){
        int neighbourRight = piecesInUse.get(board[rowIndex][columnIndex - 1]).getRight();
        return Integer.compare(0, neighbourRight);
    }

    private int defineRequiredToptSide(int rowIndex, int columnIndex){
        int neighbourBottom = piecesInUse.get(board[rowIndex - 1][columnIndex]).getBottom();
        return Integer.compare(0, neighbourBottom);
    }

    private void clearRemoved(int row, int column){
        for(int i = column; i < board[row].length; i++){
            int currentValue = board[row][i];
            if(currentValue != 0) {
                input.put(currentValue, piecesInUse.remove(currentValue));
                board[row][i] = 0;
            }else{
                break;
            }
        }

    }

    private boolean validateInput() {
        return (validateStraightEdgesAmount() && validateAllCornersExist() && validateSumOfEdgesIsZero());
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
                if(edgesAmount[2] >= i && edgesAmount[3] >= i && edgesAmount[0] >= j && edgesAmount[1] >= j){
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
        boolean isValid = (sumOfTop + sumOfBottom + sumOfLeft + sumOfRight) == 0;
        if(!isValid){
            PuzzleErrors.addError(PuzzleErrors.SUM_OF_EDGES_NOT_ZERO);
        }

        return isValid;
    }






    // Added for TestPuzzleValidator unit tests
    public void addPiece(int id, int left, int top, int right, int bottom) {

        PuzzlePiece piece = new PuzzlePiece(left, top, right, bottom);
        input.put(id, piece);

    }
}
