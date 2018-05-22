package puzzleGame;

import java.util.HashMap;
import java.util.Map;

public class FileParser {

    private String fileName;

    public FileParser(String fileName){
        this.fileName = fileName;
    }

    public Map<Integer, PuzzlePiece> parse(){
        return new HashMap<Integer, PuzzlePiece>();
    }
}