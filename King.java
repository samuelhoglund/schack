
import javax.swing.Icon;

public class King extends Piece {

    King(boolean color, Chess board, Icon image) {
        super(color, board, image);
        //TODO Auto-generated constructor stub
    }

    @Override
    boolean moveOK(oldSquare s1, oldSquare s2, oldSquare[][] board) {
        // TODO Auto-generated method stub
        return false;
    }
    
}
