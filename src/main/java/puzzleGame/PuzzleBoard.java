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

    private  boolean solve(int row, int column, Map<Integer, PuzzlePiece> piecesInUse){
        int requiredLeft = column == 0  ? 0 : Integer.compare(0, input.get( board[row][column - 1]).getRight());;
        int requiredTop = row == 0 ? 0 : Integer.compare(0, input.get( board[row - 1][column]).getBottom());
        boolean requiredFlatRight = column + 1 == board[0].length;
        boolean requiredFlatBottom = row + 1 == currentRowsAmount;
        for (int id = 1; id <= input.size(); id++){
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

    private boolean validateInput() {
        return (validateStraightEdgesAmount() && validateAllCornersExist() && validateSumOfEdgesIsZero());
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
        boolean isValid = (sumOfTop + sumOfBottom) ==0 &&  (sumOfLeft + sumOfRight) == 0;
        if(!isValid){
            PuzzleErrors.addError(PuzzleErrors.SUM_OF_EDGES_NOT_ZERO);
        }

        return isValid;
    }
}
