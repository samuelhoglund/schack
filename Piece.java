package proj;
import javax.swing.Icon;

abstract class Piece {
    // Inspiration från föreläsning 2
    boolean color; Chess board; Icon image;

    Piece(boolean color, Chess board, Icon image) {
        this.color = color;     // let's say white == true and black == false as standard
        this.board = board;
        this.image = image;
    }

    abstract boolean moveOK(Square s1, Square s2);  // Föreläsning 2
    
}
