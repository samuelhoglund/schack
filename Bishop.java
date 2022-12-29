
import javax.swing.Icon;

public class Bishop extends Piece {

    Bishop(boolean color, Chess board, Icon image) {
        super(color, board, image);
        //TODO Auto-generated constructor stub
    }

    @Override
    boolean moveOK(oldSquare s1, oldSquare s2, oldSquare[][] board) {
        int dx; int dy;

        // base base base case
        if (s1.equals(s2)) {return false; }

        //if (s1.x == s2.x | s1.y == s2.y) { return false; }
        dx = Math.abs(s2.x-s1.x); dy = Math.abs(s2.y-s1.y);
        // base cases
        if (dx-dy != 0 | (s2.hasPiece() && s2.getPiece().color==this.color)) { return false; }

        if (dx == 1 & dy == 1){    // one square away
            return true;
        }
        if (s2.x-s1.x > 0) {
            if (s2.y-s1.y > 0) {
                for (int i : new Range(dx)) {
                    if (board[s1.x+i][s1.y+i].hasPiece()) { return false; }
                }
                return true;
            }
            else {
                for (int i : new Range(dx)) {
                    if (board[s1.x+i][s1.y-i].hasPiece()) { return false; }
                }
                return true;
            }
        }
        else {
            if (s2.y-s1.y > 0) {
                for (int i : new Range(dx)) {
                    if (board[s1.x-i][s1.y+i].hasPiece()) { return false; }
                }
                return true;
            }
            else {
                for (int i : new Range(dx)) {
                    if (board[s1.x-i][s1.y-i].hasPiece()) { return false; }
                }
                return true;
            }
        }
    }
    
}
