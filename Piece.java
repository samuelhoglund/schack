import javax.swing.Icon;

abstract class Piece {
    // Inspiration från föreläsning 2
    boolean color; Icon image;

    Piece(boolean color, Icon image) {
        this.color = color;     // white == true, black == false
        this.image = image;
    }

    abstract boolean moveOK(gameSquare s1, gameSquare s2, gameSquare[][] board);  // Föreläsning 2
}
