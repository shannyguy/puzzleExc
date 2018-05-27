package puzzleGame;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class FileParser {
    private String fileName;
    private int numberOfElements;
    private Map<Integer, PuzzlePiece> piecesMap = new HashMap<>();
    int[] lineValues;

    // Constructor
    public FileParser(String fileName) {
        this.fileName = fileName;
    }

    // Parse file
    public Map<Integer, PuzzlePiece> parse() {
        try {
            String content = new String(Files.readAllBytes(Paths.get(fileName)));
            String contentArr[] = content.split("\\r?\\n");
            numberOfElements = Integer.parseInt(contentArr[0].split("=")[1]);
            for (int i = 1; i < contentArr.length; i++) {
                boolean lineWithValue = convertStringToNumbers(contentArr[i]);
                if (!lineWithValue)
                    continue;
                boolean validated = validateNumbers(lineValues);
                if (!validated)
                    continue;
                createPieceAndSaveToMap();
            }
        }
        catch (IOException e) {
            System.out.println("File doesn't exist!");
            e.printStackTrace();
        }
        return piecesMap;
    }

    // Convert string (line) to an int array return true if it's a line with element
    private boolean convertStringToNumbers(String string) {
        string = string.trim();
        if (string.equals("") || string.startsWith("#")) {
            return false;
        }
        String[] lineValueString = string.split(" ");
        lineValues = new int[lineValueString.length];
        for (int i = 0; i < lineValueString.length; i++) {
            lineValues[i] = Integer.parseInt(lineValueString[i].trim());
        }
        return true;
    }

    // Validate the int array: 1.missing pieces 2. extra pieces 3. corrupted piece (value or number of properties)
    private boolean validateNumbers(int[] lineValues) {
        // TODO Auto-generated method stub
        return true;
    }

    // Create piece and save to Map
    private void createPieceAndSaveToMap() {
        piecesMap.put(lineValues[0], new PuzzlePiece(lineValues[1], lineValues[2], lineValues[3], lineValues[4]));
    }
}