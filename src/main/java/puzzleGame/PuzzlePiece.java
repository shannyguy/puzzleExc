package puzzleGame;

public class PuzzlePiece {

    private int left;
    private int right;
    private int top;
    private int bottom;

    public PuzzlePiece(int left, int right, int top, int bottom){
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    // Added for TestPuzzleValidator unit tests
    public PuzzlePiece(PuzzlePiece puzzlePiece){
        this.left = puzzlePiece.left;
        this.right = puzzlePiece.right;
        this.top = puzzlePiece.top;
        this.bottom = puzzlePiece.bottom;
    }

    public int getLeft(){
        return left;
    }

    public int getRight(){
        return right;
    }

    public int getTop(){
        return top;
    }

    public int getBottom(){
        return bottom;
    }

    public boolean isValid(){
        return left < 2 && left > -2 && right < 2 && right > -2 && top < 2 && top > -2 && bottom < 2 && bottom > -2;
    }

    public void printPiece(){
        System.out.print("[" +left + ", " + right + ", " + top + ", " + bottom + "] ");
    }

    @Override
    public String toString() {
        return "PuzzlePiece [left=" + left + ", right=" + right + ", top=" + top + ", bottom=" + bottom + "]\n";
    }
}
