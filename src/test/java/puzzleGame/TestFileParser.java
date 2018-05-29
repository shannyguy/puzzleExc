package puzzleGame;

import static org.junit.Assert.assertEquals;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class TestFileParser {
    private String fileName = "puzzleTest.txt";
    private Map<Integer, PuzzlePiece> piecesMap;
    private String numElements;
    private int ExpectednumOfElements;
    private String element1;
    private int elementId1;
    private String element2;
    private int elementId2;
    private PuzzlePiece expectedPiece1;
    private PuzzlePiece expectedPiece2;

    public TestFileParser(String numElements, int ExpectednumOfElements, int expectedElement1Id, String element1, int expectedElement2Id, String element2, PuzzlePiece expectedPiece1, PuzzlePiece expectedPiece2) {
        this.numElements = numElements;
        this.ExpectednumOfElements = ExpectednumOfElements;
        this.element1 = element1;
        this.elementId1 = expectedElement1Id;
        this.element2 = element2;
        this.elementId2 = expectedElement2Id;
        this.expectedPiece1 = expectedPiece1;
        this.expectedPiece2 = expectedPiece2;
    }

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] { 
           {"NumElements=2", 2, 4, "4 1 -1 1 0", 2, "2 1 -1 -1 -1", new PuzzlePiece(1,-1,1,0), new PuzzlePiece(1,-1,-1,-1)},
           {"NumElements=2", 2, 4, "4 0 0 0 0", 2, "2 1 1 1 1", new PuzzlePiece(0,0,0,0), new PuzzlePiece(1,1,1,1)}
           });
        // new HashMap<Integer,PuzzlePiece>{new PuzzlePiece(4,1,-1 },
        // }
        // {"NumElements=2","4 1 1 1 1", " ", "6 1 1 1 1" },
        // {"NumElements=2","#frfrwfrf", "2 1 1 1 1" } },
        // {"NumElements=2","4 1 1 1 1", "2 1 1 1 1", "6 1 1 1 1", } }, });
    }
    @Test
    public void test() {
        try(FileOutputStream fos = new FileOutputStream(new File(fileName));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos))){
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
            
            assertEquals("Number Of Elements", Integer.parseInt(numElements.split("=")[1].trim()), ExpectednumOfElements);
            assertEquals(piecesMap.get(elementId1).getLeft(), expectedPiece1.getLeft());
            assertEquals(piecesMap.get(elementId1).getTop(), expectedPiece1.getTop());
            assertEquals(piecesMap.get(elementId1).getRight(), expectedPiece1.getRight());
            assertEquals(piecesMap.get(elementId1).getBottom(), expectedPiece1.getBottom());
            assertEquals(piecesMap.get(elementId2).getLeft(), expectedPiece2.getLeft());
            assertEquals(piecesMap.get(elementId2).getTop(), expectedPiece2.getTop());
            assertEquals(piecesMap.get(elementId2).getRight(), expectedPiece2.getRight());
            assertEquals(piecesMap.get(elementId2).getBottom(), expectedPiece2.getBottom());
    }  
}

// Unhappy:
// 1. NumElements=24 - =0, =-, not exist
// 2. The IDS of all elements must be in the range [1-NumElements]
// 3. Missing elements in the input file
// 4. Wrong element IDs (e.g. puzzle of size 6 cannot have an ID 7)
// 5. Wrong elements format (not having 4 edges, having edges which are not 0, 1, -1)
// 6. having more than 4 edges is an error!
// 7. Input file doesn’t exist / bad folder / can’t open file (permissions) etc
// 8. Bad input file format: strings that are not numbers where numbers are expected
//
// happy:
// 1. empty line or #
// 2. The elements in file doesn’t need to come in order