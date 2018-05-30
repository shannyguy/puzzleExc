package puzzleGame;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class PuzzleGame {

    private String inputFile;
    public Map<Integer, PuzzlePiece> input;
    private String outputFileName = "puzzleResult5.txt";
    private int[][] solution;


    public PuzzleGame(String inputFile){
        this.inputFile = inputFile;
    }



    public String solve() throws IllegalPuzzleException, IOException {
        FileParser fileParser = new FileParser(inputFile);
        input = fileParser.parse();
        PuzzleBoard puzzleBoard = new PuzzleBoard(input);
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
