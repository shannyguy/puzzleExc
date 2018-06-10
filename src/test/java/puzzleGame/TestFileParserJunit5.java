package puzzleGame;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class TestFileParserJunit5 {
    private String fileName = "";

//    **********************************************************************************************************************************************************************************************************
    @Test
    static Stream<Arguments> testFileParserSimpleInput() {
        return Stream.of(Arguments.of("testFileParserSimple.txt", 2, 2, new PuzzlePiece(1, -1, 1, 0), 1, new PuzzlePiece(-1, -1, -1, -1)),
                Arguments.of("testFileParserSimpleWithSpaceAndCommands.txt", 2, 2, new PuzzlePiece(1, -1, 1, 0), 1, new PuzzlePiece(0, 0, 0, 0))
        );
    }

    @ParameterizedTest
    @MethodSource("testFileParserSimpleInput")
    void testFileParserSimple(String fileName, int ExpectednumOfElements, int elementId1, PuzzlePiece expectedPiece1, int elementId2, PuzzlePiece expectedPiece2) {
        Map<Integer, PuzzlePiece> piecesMap;
        PuzzleErrors puzzleErrors = new PuzzleErrors();
        FileParser fileParser = new FileParser(fileName, puzzleErrors);
        piecesMap = fileParser.parse();

        assertEquals(ExpectednumOfElements, fileParser.getNumberOfElements());
        assertEquals(piecesMap.get(elementId1).getLeft(), expectedPiece1.getLeft());
        assertEquals(piecesMap.get(elementId1).getRight(), expectedPiece1.getRight());
        assertEquals(piecesMap.get(elementId1).getBottom(), expectedPiece1.getBottom());
        assertEquals(piecesMap.get(elementId1).getTop(), expectedPiece1.getTop());
        assertEquals(piecesMap.get(elementId2).getLeft(), expectedPiece2.getLeft());
        assertEquals(piecesMap.get(elementId2).getRight(), expectedPiece2.getRight());
        assertEquals(piecesMap.get(elementId2).getBottom(), expectedPiece2.getBottom());
        assertEquals(piecesMap.get(elementId2).getTop(), expectedPiece2.getTop());
    }

//    **********************************************************************************************************************************************************************************************************

    @Test
    static Stream<Arguments> testFileParserUnhappyNumOfElementsInput() {
        return Stream.of(Arguments.of("testFileParserUnhappyNumOfElementsInput1.txt", -2, 2, new PuzzlePiece(1, -1, 1, 0), 1, new PuzzlePiece(-1, -1, -1, -1)), //NumOfElements<0
                Arguments.of("testFileParserUnhappyNumOfElementsInput2.txt", 0, 2, new PuzzlePiece(1, -1, 1, 0), 1, new PuzzlePiece(-1, -1, -1, -1)), //NumOfElements=0
                Arguments.of("testFileParserUnhappyNumOfElementsInput3.txt", 11_000, 2, new PuzzlePiece(1, -1, 1, 0), 1, new PuzzlePiece(-1, -1, -1, -1)) //NumOfElements> max
//                         Arguments.of("testFileParserUnhappyNumOfElementsInput4.txt", 3, 2, new PuzzlePiece(1, -1, 1, 0), 1, new PuzzlePiece(-1, -1, -1, -1)), //NumOfElements > actual
//                         Arguments.of("testFileParserUnhappyNumOfElementsInput5.txt", 1, 2, new PuzzlePiece(1, -1, 1, 0), 1, new PuzzlePiece(-1, -1, -1, -1)), //NumOfElements < actual
//                         Arguments.of("testFileParserUnhappyNumOfElementsInput6.txt", 2, 2, new PuzzlePiece(1, -1, 1, 0), 1, new PuzzlePiece(-1, -1, -1, -1)), //NumOfElements without a value
//                         Arguments.of("testFileParserUnhappyNumOfElementsInput7.txt", 2, 2, new PuzzlePiece(1, -1, 1, 0), 1, new PuzzlePiece(0, 0, 0, 0)) //NumOfElements is missing from file
        );
    }

    @ParameterizedTest
    @MethodSource("testFileParserUnhappyNumOfElementsInput")
    void testFileParserUnhappyNumOfElements(String fileName, int ExpectednumOfElements, int elementId1, PuzzlePiece expectedPiece1, int elementId2, PuzzlePiece expectedPiece2) {
        Map<Integer, PuzzlePiece> piecesMap;
        boolean errorExist = false;
        PuzzleErrors puzzleErrors = new PuzzleErrors();
        FileParser fileParser = new FileParser(fileName, puzzleErrors);
        piecesMap = fileParser.parse();

        for (String error : puzzleErrors.getErrorsList()) {
            errorExist = true;
        }
        assertTrue(errorExist);
    }

//    **********************************************************************************************************************************************************************************************************

    @Test
    static Stream<Arguments> testFileParserUnhappyWrongIdsInput() {
        return Stream.of(Arguments.of("testFileParserUnhappyNumOfElementsInput1.txt", 2, 0, new PuzzlePiece(1, -1, 1, 0), 1, new PuzzlePiece(-1, -1, -1, -1)), // id=0
                Arguments.of("testFileParserUnhappyNumOfElementsInput1.txt", 2, 1, new PuzzlePiece(1, -1, 1, 0), 1, new PuzzlePiece(-1, -1, -1, -1)), // duplicate id
                Arguments.of("testFileParserUnhappyNumOfElementsInput1.txt", 1, 2, new PuzzlePiece(1, -1, 1, 0), 1, new PuzzlePiece(-1, -1, -1, -1)), // id out of range (>actual)
                Arguments.of("testFileParserUnhappyNumOfElementsInput1.txt", 2, 1, new PuzzlePiece(1, -1, 1, 0), -1, new PuzzlePiece(-1, -1, -1, -1)) // id out of range (<0)
        );
    }

    @ParameterizedTest
    @MethodSource("testFileParserUnhappyWrongIdsInput")
    void testFileParserUnhappyWrongIds(String fileName, int ExpectednumOfElements, int elementId1, PuzzlePiece expectedPiece1, int elementId2, PuzzlePiece expectedPiece2) {
        Map<Integer, PuzzlePiece> piecesMap;
        boolean errorExist = false;
        PuzzleErrors puzzleErrors = new PuzzleErrors();
        FileParser fileParser = new FileParser(fileName, puzzleErrors);
        piecesMap = fileParser.parse();

        for (String error : puzzleErrors.getErrorsList()) {
            errorExist = true;
        }
        assertTrue(errorExist);
    }

//    **********************************************************************************************************************************************************************************************************

    @Test
    static Stream<Arguments> testFileParserUnhappyMissingElementsInput() {
        return Stream.of(Arguments.of("testFileParserUnhappyNumOfElementsInput1.txt", 2, 0, new PuzzlePiece(1, -1, 1, 0), 1, new PuzzlePiece(-1, -1, -1, -1)), // Missing 1 element
                Arguments.of("testFileParserUnhappyNumOfElementsInput1.txt", 2, 1, new PuzzlePiece(1, -1, 1, 0), 1, new PuzzlePiece(-1, -1, -1, -1)) // Missing all elements
        );
    }

    @ParameterizedTest
    @MethodSource("testFileParserUnhappyMissingElementsInput")
    void testFileParserUnhappyMissingElements(String fileName, int ExpectednumOfElements, int elementId1, PuzzlePiece expectedPiece1, int elementId2, PuzzlePiece expectedPiece2) {
        Map<Integer, PuzzlePiece> piecesMap;
        boolean errorExist = false;
        PuzzleErrors puzzleErrors = new PuzzleErrors();
        FileParser fileParser = new FileParser(fileName, puzzleErrors);
        piecesMap = fileParser.parse();

        for (String error : puzzleErrors.getErrorsList()) {
            errorExist = true;
        }
        assertTrue(errorExist);
    }

//    **********************************************************************************************************************************************************************************************************

    @Test
    static Stream<Arguments> testFileParserUnhappyInvalidElementsInput() {
        return Stream.of(Arguments.of("testFileParserUnhappyNumOfElementsInput1.txt", 2, 0, new PuzzlePiece(1, -1, 1, 0), 1, new PuzzlePiece(-1, -1, -1, -1)), // Missing element edges
                         Arguments.of("testFileParserUnhappyNumOfElementsInput1.txt", 2, 1, new PuzzlePiece(1, -1, 1, 0), 1, new PuzzlePiece(-1, -1, -1, -1)), // Outline out of range
                         Arguments.of("testFileParserUnhappyNumOfElementsInput1.txt", 2, 1, new PuzzlePiece(1, -1, 1, 0), 1, new PuzzlePiece(-1, -1, -1, -1)) // More then 4 edges
        );
    }

    @ParameterizedTest
    @MethodSource("testFileParserUnhappyInvalidElementsInput")
    void testFileParserUnhappyInvalidElements(String fileName, int ExpectednumOfElements, int elementId1, PuzzlePiece expectedPiece1, int elementId2, PuzzlePiece expectedPiece2) {
        Map<Integer, PuzzlePiece> piecesMap;
        boolean errorExist = false;
        PuzzleErrors puzzleErrors = new PuzzleErrors();
        FileParser fileParser = new FileParser(fileName, puzzleErrors);
        piecesMap = fileParser.parse();

        for (String error : puzzleErrors.getErrorsList()) {
            errorExist = true;
        }
        assertTrue(errorExist);
    }

//    **********************************************************************************************************************************************************************************************************

//    @Test
//    static Stream<Arguments> testFileParserUnhappyFileInput() {
//        return Stream.of(Arguments.of("testFileParserUnhappyNumOfElementsInput1.txt", 2, 0, new PuzzlePiece(1, -1, 1, 0), 1, new PuzzlePiece(-1, -1, -1, -1)), // File doesn't exist
//                         Arguments.of("testFileParserUnhappyNumOfElementsInput1.txt", 2, 1, new PuzzlePiece(1, -1, 1, 0), 1, new PuzzlePiece(-1, -1, -1, -1)) // File can't be opened
//        );
//    }
//
//    @ParameterizedTest
//    @MethodSource("testFileParserUnhappyFileInput")
//    void testFileParserUnhappyFile(String fileName, int ExpectednumOfElements, int elementId1, PuzzlePiece expectedPiece1, int elementId2, PuzzlePiece expectedPiece2) {
//        Map<Integer, PuzzlePiece> piecesMap;
//        boolean errorExist = false;
//
//        FileParser fileParser = new FileParser(fileName);
//        piecesMap = fileParser.parse();
//
//        for (String error : getErrorsList()) {
//            errorExist = true;
//        }
//        assertTrue(errorExist);
//    }

//    **********************************************************************************************************************************************************************************************************

    @Test
    static Stream<Arguments> testFileParserUnhappyFileFormatInput() {
        return Stream.of(Arguments.of("testFileParserUnhappyNumOfElementsInput1.txt", 2, 0, new PuzzlePiece(1, -1, 1, 0), 1, new PuzzlePiece(-1, -1, -1, -1)), // NumOfElements value letters
                Arguments.of("testFileParserUnhappyNumOfElementsInput1.txt", 2, 1, new PuzzlePiece(1, -1, 1, 0), 1, new PuzzlePiece(-1, -1, -1, -1)), // Id value letters
                Arguments.of("testFileParserUnhappyNumOfElementsInput1.txt", 2, 1, new PuzzlePiece(1, -1, 1, 0), 1, new PuzzlePiece(-1, -1, -1, -1)) // Element edges letters
        );
    }

    @ParameterizedTest
    @MethodSource("testFileParserUnhappyFileFormatInput")
    void testFileParserUnhappyFileFormat(String fileName, int ExpectednumOfElements, int elementId1, PuzzlePiece expectedPiece1, int elementId2, PuzzlePiece expectedPiece2) {
        Map<Integer, PuzzlePiece> piecesMap;
        boolean errorExist = false;
        PuzzleErrors puzzleErrors = new PuzzleErrors();
        FileParser fileParser = new FileParser(fileName, puzzleErrors);
        piecesMap = fileParser.parse();

        for (String error : puzzleErrors.getErrorsList()) {
            errorExist = true;
        }
        assertTrue(errorExist);
    }
}