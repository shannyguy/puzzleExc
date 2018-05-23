package puzzleGame;

public class PuzzlePiece {

    private int left;
    private int right;
    private int top;
    private int bottom;

    public PuzzlePiece(int left, int right, int top, int bottom){
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
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
}
