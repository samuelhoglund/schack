
import javax.swing.Icon;

public class Bishop extends Piece {

    Bishop(boolean color, Icon image) {
        super(color, image);
    }

    @Override
    boolean moveOK(gameSquare s1, gameSquare s2, gameSquare[][] board) {
        int dx; int dy;

        // base base base case
        if (s1.equals(s2)) {return false; }

        dx = Math.abs(s2.x-s1.x); dy = Math.abs(s2.y-s1.y);
        // base cases
        if (dx-dy != 0 | (s2.hasPiece() && s2.getPiece().color==this.color)) { return false; }

        if (s2.y-s1.y > 0) {    // to the right
            if (s2.x-s1.x < 0) {    // up
                for (int i : new Range(dx)) {
                    if (board[s1.x-i][s1.y+i].hasPiece()) { return false; }
                }
                return true;
            }
            else {                  // down
                for (int i : new Range(dx)) {
                    if (board[s1.x+i][s1.y+i].hasPiece()) { return false; }
                }
                return true;
            }
        }
        else {              // to the left
            if (s2.x-s1.x < 0) {    // up
                for (int i : new Range(dx)) {
                    if (board[s1.x-i][s1.y-i].hasPiece()) { return false; }
                }
                return true;
            }
            else {                  // down
                for (int i : new Range(dx)) {
                    if (board[s1.x+i][s1.y-i].hasPiece()) { return false; }
                }
                return true;
            }
        }
    }
    
}
