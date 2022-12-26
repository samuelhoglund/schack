
import javax.swing.Icon;

public class Knight extends Piece {

    Knight(boolean color, Chess board, Icon image) {
        super(color, board, image);
        //TODO Auto-generated constructor stub
    }

    @Override
    boolean moveOK(oldSquare s1, oldSquare s2, oldSquare[][] board) {
        if (s2.hasPiece() && s2.getPiece().color==this.color) { return false; }
        if (Math.abs(s2.x-s1.x) == 1 & Math.abs(s2.y-s1.y) == 2) { return true; }
        if (Math.abs(s2.x-s1.x) == 2 & Math.abs(s2.y-s1.y) == 1) { return true; }
        return false;
    }
    
}
