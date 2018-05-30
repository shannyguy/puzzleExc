package puzzleGame;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestFileParserJunit5 {
    private String fileName = "puzzleTest.txt";
    private Map<Integer, PuzzlePiece> piecesMap;

    @Test
    static Stream<Arguments> testFileParserSimpleInput() {
        return Stream.of(Arguments.of("NumElements=2", 2, 2, "2 1 -1 1 0", 1, "1 -1 -1 -1 -1", new PuzzlePiece(1, -1, 1, 0), new PuzzlePiece(-1, -1, -1, -1)), 
                         Arguments.of("NumElements=2", 2, 2, "2 1 -1 1 0", 1, "1 0 0 0 0", new PuzzlePiece(1, -1, 1, 0), new PuzzlePiece(0, 0, 0, 0))
                    );
    }

    @ParameterizedTest
    @MethodSource("testFileParserSimpleInput")
    void testFileParserSimple(String numOfElements, int ExpectednumOfElements, int elementId1, String element1, int elementId2, String element2, PuzzlePiece expectedPiece1, PuzzlePiece expectedPiece2) {
        writeElementsToFile(numOfElements, element1, element2);
        FileParser fileParser = new FileParser(fileName);
        piecesMap = fileParser.parse();

        Assertions.assertEquals(ExpectednumOfElements, fileParser.getNumberOfElements());
        Assertions.assertEquals(piecesMap.get(elementId1).getLeft(), expectedPiece1.getLeft());
        Assertions.assertEquals(piecesMap.get(elementId1).getRight(), expectedPiece1.getRight());
        Assertions.assertEquals(piecesMap.get(elementId1).getBottom(), expectedPiece1.getBottom());
        Assertions.assertEquals(piecesMap.get(elementId1).getTop(), expectedPiece1.getTop());
        Assertions.assertEquals(piecesMap.get(elementId2).getLeft(), expectedPiece2.getLeft());
        Assertions.assertEquals(piecesMap.get(elementId2).getRight(), expectedPiece2.getRight());
        Assertions.assertEquals(piecesMap.get(elementId2).getBottom(), expectedPiece2.getBottom());
        Assertions.assertEquals(piecesMap.get(elementId2).getTop(), expectedPiece2.getTop());
    }

    @ParameterizedTest
    @MethodSource("testFileParserSimpleInput")
    void testFileParserSimpleWithSpaceAndCommands(String numOfElements, int ExpectednumOfElements, int elementId1, String element1, int elementId2, String element2, PuzzlePiece expectedPiece1, PuzzlePiece expectedPiece2) {
        writeElementsToFileWithSpaceAndComments(numOfElements, element1, element2);
        FileParser fileParser = new FileParser(fileName);
        piecesMap = fileParser.parse();

        Assertions.assertEquals(ExpectednumOfElements, fileParser.getNumberOfElements());
        Assertions.assertEquals(piecesMap.get(elementId1).getLeft(), expectedPiece1.getLeft());
        Assertions.assertEquals(piecesMap.get(elementId1).getRight(), expectedPiece1.getRight());
        Assertions.assertEquals(piecesMap.get(elementId1).getBottom(), expectedPiece1.getBottom());
        Assertions.assertEquals(piecesMap.get(elementId1).getTop(), expectedPiece1.getTop());
        Assertions.assertEquals(piecesMap.get(elementId2).getLeft(), expectedPiece2.getLeft());
        Assertions.assertEquals(piecesMap.get(elementId2).getRight(), expectedPiece2.getRight());
        Assertions.assertEquals(piecesMap.get(elementId2).getBottom(), expectedPiece2.getBottom());
        Assertions.assertEquals(piecesMap.get(elementId2).getTop(), expectedPiece2.getTop());
    }
    
    @Test
    static Stream<Arguments> testFileParserUnhappyNumOfElementsInput() {
        return Stream.of(Arguments.of("NumElements=-2", 2, 2, "2 1 -1 1 0", 1, "1 -1 -1 -1 -1", new PuzzlePiece(1, -1, 1, 0), new PuzzlePiece(-1, -1, -1, -1)), //negative numOfElements
                         Arguments.of("NumElements=0", 2, 2, "2 1 -1 1 0", 1, "1 -1 -1 -1 -1", new PuzzlePiece(1, -1, 1, 0), new PuzzlePiece(-1, -1, -1, -1)), //numOfElements=0
                         Arguments.of("NumElements=11_000", 2, 2, "2 1 -1 1 0", 1, "1 -1 -1 -1 -1", new PuzzlePiece(1, -1, 1, 0), new PuzzlePiece(-1, -1, -1, -1)), //numOfElements out of range (>10_000)
                         Arguments.of("NumElements=3", 2, 2, "2 1 -1 1 0", 1, "1 -1 -1 -1 -1", new PuzzlePiece(1, -1, 1, 0), new PuzzlePiece(-1, -1, -1, -1)), //numOfElements > actual elements
                         Arguments.of("NumElements=1", 2, 2, "2 1 -1 1 0", 1, "1 -1 -1 -1 -1", new PuzzlePiece(1, -1, 1, 0), new PuzzlePiece(-1, -1, -1, -1)), //numOfElements < actual elements
                         Arguments.of("NumElements=", 2, 2, "2 1 -1 1 0", 1, "1 -1 -1 -1 -1", new PuzzlePiece(1, -1, 1, 0), new PuzzlePiece(-1, -1, -1, -1)), //numOfElements empty
                         Arguments.of(" ", 2, 2, "2 1 -1 1 0", 1, "1 0 0 0 0", new PuzzlePiece(1, -1, 1, 0), new PuzzlePiece(0, 0, 0, 0)) //numOfElements not exist
                    );
    }

    @ParameterizedTest
    @MethodSource("testFileParserUnhappyNumOfElementsInput")
    void testFileParserUnhappyNumOfElements(String numOfElements, int ExpectednumOfElements, int elementId1, String element1, int elementId2, String element2, PuzzlePiece expectedPiece1, PuzzlePiece expectedPiece2) {
        writeElementsToFile(numOfElements, element1, element2);
        FileParser fileParser = new FileParser(fileName);
        piecesMap = fileParser.parse();

        Assertions.assertEquals(ExpectednumOfElements, fileParser.getNumberOfElements());
        Assertions.assertEquals(piecesMap.get(elementId1).getLeft(), expectedPiece1.getLeft());
        Assertions.assertEquals(piecesMap.get(elementId1).getRight(), expectedPiece1.getRight());
        Assertions.assertEquals(piecesMap.get(elementId1).getBottom(), expectedPiece1.getBottom());
        Assertions.assertEquals(piecesMap.get(elementId1).getTop(), expectedPiece1.getTop());
        Assertions.assertEquals(piecesMap.get(elementId2).getLeft(), expectedPiece2.getLeft());
        Assertions.assertEquals(piecesMap.get(elementId2).getRight(), expectedPiece2.getRight());
        Assertions.assertEquals(piecesMap.get(elementId2).getBottom(), expectedPiece2.getBottom());
        Assertions.assertEquals(piecesMap.get(elementId2).getTop(), expectedPiece2.getTop());
    }
    
    @Test
    static Stream<Arguments> testFileParserUnhappyWrongIdsInput() {
        return Stream.of(Arguments.of("NumElements=2", 2, 2, "0 1 -1 1 0", 1, "1 -1 -1 -1 -1", new PuzzlePiece(1, -1, 1, 0), new PuzzlePiece(-1, -1, -1, -1)), // id=0
                         Arguments.of("NumElements=2", 2, 2, "1 1 -1 1 0", 1, "1 -1 -1 -1 -1", new PuzzlePiece(1, -1, 1, 0), new PuzzlePiece(-1, -1, -1, -1)), // duplicate id
                         Arguments.of("NumElements=2", 2, 2, "3 1 -1 1 0", 1, "1 -1 -1 -1 -1", new PuzzlePiece(1, -1, 1, 0), new PuzzlePiece(-1, -1, -1, -1)), // id out of range (>actual)
                         Arguments.of("NumElements=2", 2, 2, "-1 1 -1 1 0", 1, "1 -1 -1 -1 -1", new PuzzlePiece(1, -1, 1, 0), new PuzzlePiece(-1, -1, -1, -1)) // id out of range (<0)
                    );
    }

    @ParameterizedTest
    @MethodSource("testFileParserUnhappyWrongIdsInput")
    void testFileParserUnhappyWrongIds(String numOfElements, int ExpectednumOfElements, int elementId1, String element1, int elementId2, String element2, PuzzlePiece expectedPiece1, PuzzlePiece expectedPiece2) {
        writeElementsToFile(numOfElements, element1, element2);
        FileParser fileParser = new FileParser(fileName);
        piecesMap = fileParser.parse();

        Assertions.assertEquals(ExpectednumOfElements, fileParser.getNumberOfElements());
        Assertions.assertEquals(piecesMap.get(elementId1).getLeft(), expectedPiece1.getLeft());
        Assertions.assertEquals(piecesMap.get(elementId1).getRight(), expectedPiece1.getRight());
        Assertions.assertEquals(piecesMap.get(elementId1).getBottom(), expectedPiece1.getBottom());
        Assertions.assertEquals(piecesMap.get(elementId1).getTop(), expectedPiece1.getTop());
        Assertions.assertEquals(piecesMap.get(elementId2).getLeft(), expectedPiece2.getLeft());
        Assertions.assertEquals(piecesMap.get(elementId2).getRight(), expectedPiece2.getRight());
        Assertions.assertEquals(piecesMap.get(elementId2).getBottom(), expectedPiece2.getBottom());
        Assertions.assertEquals(piecesMap.get(elementId2).getTop(), expectedPiece2.getTop());
    }
    
// Write to file *******************************************************************************************************************************************************************
    
    private void writeElementsToFileWithSpaceAndComments(String numOfElements, String element1, String element2) {
        try (FileOutputStream fos = new FileOutputStream(new File(fileName)); BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos))) {
            bw.write("# This is a comments!!!");
            bw.newLine();
            bw.write(numOfElements);
            bw.newLine();
            bw.write("# This is a comments!!!");
            bw.newLine();
            bw.write("# This is a comments!!!");
            bw.newLine();
            bw.newLine();
            bw.write(element1);
            bw.newLine();
            bw.write("# This is a comments!!!");
            bw.newLine();
            bw.write(element2);
            bw.newLine();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void writeElementsToFile(String numOfElements, String element1, String element2) {
        try (FileOutputStream fos = new FileOutputStream(new File(fileName)); BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos))) {
            bw.write(numOfElements);
            bw.newLine();
            bw.write(element1);
            bw.newLine();
            bw.write(element2);
            bw.newLine();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// Unhappy:
// 1. NumElements=24 - =0, =-, not exist [V]
// 2. The IDS of all elements must be in the range [1-NumElements] [V]
// 3. Missing elements in the input file
// 4. Wrong element IDs (e.g. puzzle of size 6 cannot have an ID 7)
// 5. Wrong elements format (not having 4 edges, having edges which are not 0, 1, -1)
// 6. having more than 4 edges is an error!
// 7. Input file doesn�t exist / bad folder / can�t open file (permissions) etc
// 8. Bad input file format: strings that are not numbers where numbers are expected
//
// happy:
// 1. empty line or #
// 2. The elements in file doesn�t need to come in order