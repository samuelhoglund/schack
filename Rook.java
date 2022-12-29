
import javax.swing.Icon;

public class Rook extends Piece {

    Rook(boolean color, Chess board, Icon image) {
        super(color, board, image);
        //TODO Auto-generated constructor stub
    }

    @Override
    boolean moveOK(oldSquare s1, oldSquare s2, oldSquare[][] board) {
        int start = 0; int end = 0;

        // base base base case
        if (s1.equals(s2)) {return false; }

        // base case
        if (s2.hasPiece() && s2.getPiece().color==this.color) { return false; }
        if ((Math.abs(s2.x-s1.x) == 1 & s2.y == s1.y) || (Math.abs(s2.y-s1.y) == 1 & s2.x == s1.x)) {    // one square away
            return true;
        }

        if (s1.x == s2.x) {
            // same row, iterate through squares inbetween
            if (s1.y>s2.y) { start = s2.y+1; end = s1.y; }
            else if (s1.y<s2.y) { start = s1.y+1; end = s2.y; }
            for (int i : new Range(start, end)) {
                if (board[s1.x][i].hasPiece()) { return false; }
            }
            return true;
        }
        else if (s1.y == s2.y) {
            // same col, itrerate through squares inbetween
            if (s1.x>s2.x) { start = s2.x+1; end = s1.x; }
            else if (s1.x<s2.x) { start = s1.x+1; end = s2.x; }

            for (int i : new Range(start, end)) {
                if (board[i][s1.y].hasPiece()) { return false; }
            }
            return true;
        }
        return false;  
    }

}
