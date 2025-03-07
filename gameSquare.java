


public class gameSquare {
    public int x; public int y;   // Coordinates
    Piece piece;
    
    public gameSquare(int x, int y, Piece piece) {
        this.x = x;
        this.y = y;
        this.piece = piece;
    }
    public boolean hasPiece() {
        if (this.piece==null) {
            return false;
        }
        return true;
    }
    public Piece getPiece() {
        return this.piece;
    }
    public void setEmpty() {
        this.piece = null;
    }
    public void addPiece(Piece p) {
        this.piece = p;
    }
    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }    
}
