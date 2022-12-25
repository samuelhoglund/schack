
import javax.swing.Icon;

abstract class Piece {
    // Inspiration från föreläsning 2
    boolean color; Chess board; Icon image;

    Piece(boolean color, Chess board, Icon image) {
        this.color = color;     // let's say white == true and black == false as standard
        this.board = board;
        this.image = image;
    }

    abstract boolean moveOK(oldSquare s1, oldSquare s2);  // Föreläsning 2

    public boolean coordinatesExist(int x, int y) {
        if (x<8 & x>=0 & y>=0 & y<8) {
            return true;
        }
        return false;
    }
    
}
