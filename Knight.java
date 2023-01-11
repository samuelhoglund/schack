
import javax.swing.Icon;

public class Knight extends Piece {

    Knight(boolean color, Icon image) {
        super(color, image);
    }

    @Override
    boolean moveOK(gameSquare s1, gameSquare s2, gameSquare[][] board) {
        if (s2.hasPiece() && s2.getPiece().color==this.color) { return false; }
        if (Math.abs(s2.x-s1.x) == 1 & Math.abs(s2.y-s1.y) == 2) { return true; }
        if (Math.abs(s2.x-s1.x) == 2 & Math.abs(s2.y-s1.y) == 1) { return true; }
        return false;
    }
}
