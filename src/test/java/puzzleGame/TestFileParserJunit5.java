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

public class TestFileParserJunit5 {
    private String fileName = "puzzleTest.txt";
    private Map<Integer, PuzzlePiece> piecesMap;
    private String numElements = "NumElements=2";
    private int ExpectednumOfElements;
    private int elementId1;
    private int elementId2;

    
    @Test
    static Stream<Arguments> squareAreaAndPerimeterInput() {
              return Stream.of(
                    Arguments.of(2, "2 1 -1 1 0",1, "1 -1 -1 -1 -1", new PuzzlePiece(1,-1,1,0), new PuzzlePiece(-1,-1,-1,-1)),
                    Arguments.of(2, "2 1 -1 1 0",1, "1 -1 -1 -1 -1", new PuzzlePiece(1,-1,1,0), new PuzzlePiece(-1,-1,-1,-1))
            );
    
    }
        
    
    @ParameterizedTest
    @MethodSource("squareAreaAndPerimeterInput")
    void testSquareAreaAndPerimeter(int expectedElement1Id, String element1, int expectedElement2Id, String element2, PuzzlePiece expectedPiece1, PuzzlePiece expectedPiece2) {
        try (FileOutputStream fos = new FileOutputStream(new File(fileName)); BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos))) {
            bw.write(numElements);
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

        FileParser fileParser = new FileParser(fileName);
        piecesMap = fileParser.parse();

        Assertions.assertEquals(ExpectednumOfElements, Integer.parseInt(numElements.split("=")[1].trim()));
        Assertions.assertEquals(piecesMap.get(elementId1)
                .getLeft(), expectedPiece1.getLeft());
        Assertions.assertEquals(piecesMap.get(elementId1)
                .getTop(), expectedPiece1.getTop());
        Assertions.assertEquals(piecesMap.get(elementId1)
                .getRight(), expectedPiece1.getRight());
        Assertions.assertEquals(piecesMap.get(elementId1)
                .getBottom(), expectedPiece1.getBottom());
        Assertions.assertEquals(piecesMap.get(elementId2)
                .getLeft(), expectedPiece2.getLeft());
        Assertions.assertEquals(piecesMap.get(elementId2)
                .getTop(), expectedPiece2.getTop());
        Assertions.assertEquals(piecesMap.get(elementId2)
                .getRight(), expectedPiece2.getRight());
        Assertions.assertEquals(piecesMap.get(elementId2)
                .getBottom(), expectedPiece2.getBottom());

    }

}

// Unhappy:
// 1. NumElements=24 - =0, =-, not exist
// 2. The IDS of all elements must be in the range [1-NumElements]
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