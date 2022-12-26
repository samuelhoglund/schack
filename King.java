
import javax.swing.Icon;

public class King extends Piece {

    King(boolean color, Chess board, Icon image) {
        super(color, board, image);
        //TODO Auto-generated constructor stub
    }

    @Override
    boolean moveOK(oldSquare s1, oldSquare s2, oldSquare[][] board) {
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
