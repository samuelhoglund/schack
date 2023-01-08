
import javax.swing.Icon;

public class Queen extends Piece {

    Queen(boolean color, Icon image) {
        super(color, image);
        //TODO Auto-generated constructor stub
    }

    @Override
    boolean moveOK(gameSquare s1, gameSquare s2, gameSquare[][] board) {
        int dx = Math.abs(s2.x-s1.x); int dy = Math.abs(s2.y-s1.y);
        Piece thisQ = this;

        if (dx - dy == 0)  {
            thisQ = new Bishop(this.color, this.image);
        }
        else {
            thisQ = new Rook(this.color, this.image);
        }
        if (thisQ.moveOK(s1, s2, board)) {
            return true;
        }
        return false;
    }
    
}
