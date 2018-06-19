package puzzleGame;

public class PuzzlePiece {

    private int[] edges = new int[4];

    public PuzzlePiece(int left, int right, int top, int bottom) {
        this.edges[0] = left;
        this.edges[1] = right;
        this.edges[2] = top;
        this.edges[3] = bottom;
    }

    public int getLeft(){
        return edges[0];
    }

    public int getRight(){
        return edges[1];
    }

    public int getTop(){
        return edges[2];
    }

    public int getBottom(){
        return edges[3];
    }

    public int[] getEdges(){
        return edges;
    }

    public boolean isValid(){
        return edges[0] < 2 && edges[0] > -2 && edges[1] < 2 && edges[1] > -2 && edges[2] < 2 && edges[2] > -2 && edges[3] < 2 && edges[3] > -2;
    }

    public void printPiece(){
        System.out.print("[" +edges[0] + ", " + edges[1] + ", " + edges[2] + ", " + edges[3] + "] ");
    }

    @Override
    public String toString() {
        return "PuzzlePiece [left=" + edges[0] + ", right=" + edges[1] + ", top=" + edges[2] + ", bottom=" + edges[3] + "]\n";
    }
}
