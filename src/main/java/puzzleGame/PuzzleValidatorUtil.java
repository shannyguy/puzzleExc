package puzzleGame;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;

public class PuzzleValidatorUtil {

    private ArrayList<PuzzlePiece> solution = new ArrayList<>();
    private int rowNumber = 0;
    private int columnNumber = 0;


    public boolean isValidPuzzle(Map<Integer, PuzzlePiece> pieceList, String outputFilePath) throws IOException {

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(outputFilePath)))) {

            String puzzleRow;

            while ((puzzleRow = br.readLine()) != null) {
                rowNumber++;
                String[] piecesLine = puzzleRow.split(" ");

                for (columnNumber = 0; columnNumber < piecesLine.length; columnNumber++) {
                    //Get piece id
                    int pieceId = Integer.parseInt(piecesLine[columnNumber].trim());
                    //Get piece shape from Map and add it to the puzzle solution (sorted array list)
                    solution.add(pieceList.get(pieceId));
                }
            }
        }

        return validateSolution();
    }

    private boolean validateSolution() {

        for (int index = 0; index < solution.size(); index++) {

            int row = index / columnNumber;
            int column = index % columnNumber;

            if (row == 0) { //First row, top should be 0
                if (solution.get(index).getTop() != 0)
                    return false;
            }

            if (column == 0) //First column, left should be 0
            {
                if (solution.get(index).getLeft() != 0)
                    return false;
            }

            if (column == columnNumber - 1) {
                if (solution.get(index).getRight() != 0) //Last column, right should be 0
                    return false;
            } else if (-solution.get(index).getRight() != solution.get(index + 1).getLeft()) {
                return false;
            }

            if (row == rowNumber - 1) {
                if (solution.get(index).getBottom() != 0)
                    return false;
            } else if (-solution.get(index).getBottom() != solution.get(index + columnNumber).getTop()) {
                return false;
            }
        }

        return true;
    }


}
