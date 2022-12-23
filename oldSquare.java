


public class oldSquare {
    public int x; public int y;   // Coordinates
    Piece piece;
    
    public oldSquare(int x, int y, Piece piece) {
        this.x = x;
        this.y = y;
        this.piece = piece;
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
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    
    
}
