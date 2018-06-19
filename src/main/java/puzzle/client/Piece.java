package puzzle.client;

public class Piece {
    private int id;
    private int [] piece = new int[4];

    public int[] getPiece() {
        return piece;
    }

    public void setPiece(int[] piece) {
        this.piece = piece;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
