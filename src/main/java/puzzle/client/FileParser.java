package puzzle.client;

import com.google.gson.Gson;
import puzzleGame.PuzzleErrors;
import puzzleGame.PuzzlePiece;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FileParser {
    private String fileName;
    private int numberOfElements;
    private PuzzleErrors puzzleErrors;
    private Map<Integer, PuzzlePiece> piecesMap = new HashMap<>();
    int[] lineValues;
    ArrayList<Integer> idList = new ArrayList<>();
    String[] contentArr;

    // Constructor
    public FileParser(String fileName, PuzzleErrors puzzleErrors) {
        this.fileName = fileName;
        this.puzzleErrors = puzzleErrors;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    // Parse file
    public String parse() {
        String contentArr[] = writeFileToString();
        int nextLineAfternumberOfElements = readAndValidateNumberOfElements(contentArr);
        if (nextLineAfternumberOfElements < 0) {
            puzzleErrors.addError(String.format(PuzzleErrors.WRONG_NUMBER_OF_ELEMEMNTS_VALUE, numberOfElements));
        }else{
            for (int line = nextLineAfternumberOfElements; line < contentArr.length; line++) {
                boolean lineWithElement = convertStringToNumbersAndVerifyValue(contentArr[line]);
                if (!lineWithElement)
                    continue;
                boolean elementValidated = validateIdAndElement(lineValues);
                if (!elementValidated)
                    continue;
                createPieceAndSaveToMap();
            }
            verifyAllIdsExist();
        }
        Puzzle puzzle = new Puzzle();
        int index = 0;
        Piece [] pieces = new Piece [piecesMap.size()];
        for (Map.Entry<Integer, PuzzlePiece> entry : piecesMap.entrySet()) {
            Piece piece = new Piece();
            piece.setId(entry.getKey());
            piece.setPiece(entry.getValue().getEdges());
            pieces[index] = piece;
            index ++;
        }
        puzzle.setPieces(pieces);
        Gson gson = new Gson();
        return gson.toJson(puzzle);
    }

    private String[] writeFileToString() {
        contentArr = null;
        try {
            String content = new String(Files.readAllBytes(Paths.get(fileName)));
            contentArr = content.split("\\r?\\n");
        } catch (IOException e) {
            System.out.println("File doesn't exist!");
            e.printStackTrace();
        }

        return contentArr;
    }

    // Number of elements can be from 0 to 10,000
    private int readAndValidateNumberOfElements(String[] contentArr) {
        int line;
        int numberOfElementsNotexist = 0;
        int numberOfElementsOutOfRange = -1;

        for (line = 0; line < contentArr.length; line++) {
            contentArr[line] = contentArr[line].trim();
            if (contentArr[line].equals("") || contentArr[line].startsWith("#")) {
                continue;
            }
            numberOfElements = Integer.parseInt(contentArr[line].split("=")[1]);
            if (numberOfElements < 1 || numberOfElements > 10_000) {
                return numberOfElementsOutOfRange;
            } else {
                return line + 1;
            }
        }
        return numberOfElementsNotexist;
    }

    // Skip lines with spaces or that start with #
    private boolean convertStringToNumbersAndVerifyValue(String string) {
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

    // Validate the int array: 1. Wrong id 2.missing pieces 3. extra pieces 4. corrupted piece (value or number of properties)
    private boolean validateIdAndElement(int[] lineValues) {
        int id = lineValues[0];
        if (id < 1 || id > numberOfElements || idList.contains(id)) {
            puzzleErrors.addError(String.format(PuzzleErrors.WRONG_ELEMENT_IDS, numberOfElements, id));
            return false;
        } else {
            idList.add(id);
        }
        for (int outline = 1; outline < 5; outline++) {
            if (lineValues[outline] > 1 || lineValues[outline] < -1) {
                puzzleErrors.addError(String.format(PuzzleErrors.WRONG_ELEMENT_FORMAT, id, lineValues[outline]));
                return false;
            }
        }
        return true;
    }

    private void verifyAllIdsExist() {
        for (int id = 1; id < contentArr.length; id++) {
            if (!idList.contains(id)) {
                puzzleErrors.addError(String.format(PuzzleErrors.MISSING_PUZZLE_ELEMENTS, id));
            }
        }
    }

    // Create piece and save to Map
    private void createPieceAndSaveToMap() {
        piecesMap.put(lineValues[0], new PuzzlePiece(lineValues[1], lineValues[2], lineValues[3], lineValues[4]));
    }
}