package puzzleGame;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class PuzzleGame {

    private String inputFile;
    private String outputFileName;
    private Map<Integer, PuzzlePiece> input;

    private int[][] solution;


    public PuzzleGame(String inputFile, String outputFileName){
        this.inputFile = inputFile;
        this.outputFileName = outputFileName;
    }

    public String solve() throws IOException {
        PuzzleErrors puzzleErrors = new PuzzleErrors();
        FileParser fileParser = new FileParser(inputFile, puzzleErrors);
        input = fileParser.parse();
        PuzzleBoard puzzleBoard = new PuzzleBoard(input, puzzleErrors);
        try{
            solution = puzzleBoard.getBoard();
            fillOutputFile(this.toString());

        }catch (IllegalPuzzleException e){
            fillOutputFile(e.getMessage());
        }

        return outputFileName;
    }

    // Added for TestPuzzleValidator unit tests
    public Map<Integer, PuzzlePiece> getInput() {
        return input;
    }

    private void fillOutputFile(String content) throws IOException {
        outputFileName = new Date().getTime() + outputFileName;
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName, true));
        writer.write(content);
        writer.close();
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < solution.length; i++){
            for (int j = 0; j < solution[0].length; j++){
                builder.append(solution[i][j] + " ");
            }
            builder.append("\n");
        }
        return builder.toString();
    }


}
