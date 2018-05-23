package puzzleGame;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PuzzleBoard {

    private Map<Integer, PuzzlePiece> input;

    private PuzzlePiece[][] board;

    private String outputFileName = "puzzleResult.txt";

    private void initBoard(){
        int piecesAmount = input.size();
        for(int i = 1; i <= piecesAmount/2; i++){
            if(piecesAmount%i == 0){
                board = new PuzzlePiece[i][piecesAmount/i];
                if(solve()){
                    break;
                }
            }
        }
    }

    public boolean solve(){
        return true;
    }

    private boolean validateInput(){
        StringBuilder errors = new StringBuilder();
        List<Integer> missingIds = validatePartsIds();
        return true;
    }

    private List<Integer> validatePartsIds(){
        List<Integer> missingIds = new ArrayList<Integer>();
        for (int i = 1; i< input.size(); i++){
            if(input.get(i) == null){
                missingIds.add(i);
            }
        }
        return missingIds;
    }

    private List<Integer> validateIdsInRange(){
        List<Integer> notInRangeIds = new ArrayList<Integer>();
        for (Map.Entry<Integer, PuzzlePiece> entry : input.entrySet())
        {
            int id = entry.getKey();
            if(id < 0 || id > input.size()){
                notInRangeIds.add(id);
            }
        }
        return notInRangeIds;
    }

    private List<Integer> validateFormat(){
        List<Integer> invalidIds = new ArrayList<Integer>();
        for (Map.Entry<Integer, PuzzlePiece> entry : input.entrySet())
        {
            PuzzlePiece piece = entry.getValue();
            if(!piece.isValid()){
                invalidIds.add(entry.getKey());
            }
        }
        return invalidIds;
    }

    private void fillOutputFile(String content) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName, true));
        writer.append(content);
        writer.close();
    }
}
