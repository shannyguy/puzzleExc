package puzzleGame;

import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class PuzzleBoard implements Runnable{

    private Map<Integer, PuzzlePiece> input;
    private PuzzleErrors puzzleErrors;
    private  Map<Integer, PuzzlePiece> piecesInUse;
    private List<int[]> possibleDimensions;
    private int currentRowsAmount;
    private int[][] board;
    PuzzleGame puzzleGame;
    Socket socket;

    // Added for TestPuzzleValidator unit tests
    public PuzzleBoard(Map<Integer, PuzzlePiece> pieces, Socket socket)  {
        input = pieces;
        this.socket = socket;
    }

    private PuzzleSolution getBoard() {
        PuzzleSolution puzzleSolution = null;
        validateInput();
        if(!puzzleErrors.getErrorsList().isEmpty()){
            puzzleSolution = new PuzzleSolution(false);
            int index = 0;
            String [] errors = new String[puzzleErrors.getErrorsList().size()];
            for(String error : puzzleErrors.getErrorsList()){
                errors[index] = error;
                index ++;
            }
            puzzleSolution.setErrors(errors);
            return puzzleSolution;
        }
        if(findSolution()){
            puzzleSolution = new PuzzleSolution(true);
            int index = 0;
            int [] solutionPieces = new int [input.size()];
            for (int i = 0; i< board.length; i++){
                for (int j = 0; j< board[0].length; j++){
                    solutionPieces[index] = board[i][j];
                    index ++;
                }
            }
            Solution solution = new Solution(board.length, solutionPieces);
            puzzleSolution.setSolution(solution);
            return puzzleSolution;

        }else{
            puzzleSolution = new PuzzleSolution(false);
            puzzleSolution.setErrors(new String[]{PuzzleErrors.CANNOT_SOLVE_PUZZLE});
            return puzzleSolution;
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
        boolean isStraightEdgesAmountCorrect  = validateStraightEdgesAmount();
        boolean allCornersExist = validateAllCornersExist();
        boolean sumOfEdgesZero = validateSumOfEdgesIsZero();
        return (isStraightEdgesAmountCorrect && allCornersExist && sumOfEdgesZero);
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
        validatePossibleDimensions();
        if(possibleDimensions.isEmpty()){
            puzzleErrors.addError(PuzzleErrors.WRONG_NUMBER_OF_STRAIGHT_EDGES);
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
            puzzleErrors.addError(String.format(PuzzleErrors.MISSING_CORNERS_ERROR, builder));
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
            puzzleErrors.addError(PuzzleErrors.SUM_OF_EDGES_NOT_ZERO);
        }

        return isValid;
    }

    /**
     * Checks if dimensions of one row/one column are valid considering the existing corners.
     */
    private void validatePossibleDimensions(){
        boolean foundLeft = false;
        boolean foundRight = false;
        boolean foundTop = false;
        boolean foundBottom = false;
        if(possibleDimensions.get(0)[0] == 1){
            while(!foundLeft || !foundRight) {
                for (Map.Entry<Integer, PuzzlePiece> entry : input.entrySet()) {
                    if (foundLeft || entry.getValue().getTop() == 0 && entry.getValue().getLeft() == 0 && entry.getValue().getBottom() == 0) {
                        foundLeft = true;
                    }else{
                        if(foundRight || entry.getValue().getTop() == 0 && entry.getValue().getRight() == 0 && entry.getValue().getBottom() == 0){
                            foundRight = true;
                        }
                    }
                }
            }
            if(!foundLeft || !foundRight){
                possibleDimensions.remove(0);
            }
        }

        if(possibleDimensions.get(possibleDimensions.size() - 1)[0] == input.size()){
            while(!foundTop || !foundBottom) {
                for (Map.Entry<Integer, PuzzlePiece> entry : input.entrySet()) {
                    if (foundTop || entry.getValue().getTop() == 0 && entry.getValue().getLeft() == 0 && entry.getValue().getRight() == 0) {
                        foundTop = true;
                    }else{
                        if(foundBottom || entry.getValue().getLeft()== 0 && entry.getValue().getRight() == 0 && entry.getValue().getBottom() == 0){
                            foundBottom = true;
                        }
                    }
                }
            }
            if(!foundTop || !foundBottom){
                possibleDimensions.remove(possibleDimensions.size() - 1);
            }
        }
    }

    @Override
    public void run() {
        sendSolution(getBoard());

    }

    public void sendSolution(PuzzleSolution puzzleSolution) {
        try (PrintStream clientOutput = new PrintStream(socket.getOutputStream(), /* autoflush */ true, "UTF8");) {
            Gson gson = new Gson();
            clientOutput.print(gson.toJson(puzzleSolution));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(socket != null && !socket.isClosed()){
                try {
                    socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        }
    }
}
