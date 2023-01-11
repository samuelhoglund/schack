
import javax.swing.Icon;

public class King extends Piece {

    King(boolean color, Icon image) {
        super(color, image);
    }
    
    @Override
    boolean moveOK(gameSquare s1, gameSquare s2, gameSquare[][] board) {
        int dx = Math.abs(s2.x-s1.x); int dy = Math.abs(s2.y-s1.y);
        
        if (s2.hasPiece() && s2.getPiece().color==this.color) { return false; }
        if ((Math.abs(s2.x-s1.x) == 1 & s2.y == s1.y) || (Math.abs(s2.y-s1.y) == 1 & s2.x == s1.x)) {    // one square away
            return true;
        }
        if (dx == 1 & dy == 1){    // one square away
            return true;
        }
        return false;
    }
}
