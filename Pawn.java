
import javax.swing.Icon;

public class Pawn extends Piece {

    Pawn(boolean color, Chess board, Icon image) {
        super(color, board, image);
        //TODO Auto-generated constructor stub
    }

    @Override
    boolean moveOK(oldSquare s1, oldSquare s2, oldSquare[][] board) {
        if (!s2.hasPiece()) {   // rörelsefas
            if (!this.color) {   // black
                if (s1.x == 1) {
                    // tillåt ett eller två steg framåt (eller eliminering)
                    if (s2.y == s1.y & ((s2.x == 3 & !board[s2.x-1][s2.y].hasPiece()) | s2.x == 2)) {
                        return true;        // vanligt startdrag
                    }
                }
                else {
                    if (s2.y == s1.y & s2.x == s1.x+1) {
                        return true;        // vanligt drag förutom start
                    }
                }
            }
            
            else {              // white
                if (s1.x == 6) {
                    // tillåt ett eller två steg framåt (eller eliminering)
                    if (s2.y == s1.y & ((s2.x == 4 & !board[s2.x+1][s2.y].hasPiece()) | s2.x == 5)) {
                        return true;        // vanligt startdrag
                    } 
                }
                else {
                    if (s2.y == s1.y & s2.x ==  s1.x-1) {
                        return true;        // vanligt drag förutom start
                    }
                }
            }
        }
        else {      // ATTACK
            Piece p = s2.getPiece();
            if (!this.color) {
                if (p.color!=this.color & (s2.y == s1.y+1 | s2.y == s1.y-1) & s2.x == s1.x+1) { return true; }
            }
            else {
                if (p.color!=this.color & (s2.y == s1.y+1 | s2.y == s1.y-1) & s2.x == s1.x-1) { return true; }
            }
        }

        return false;
    }

    /* 
    @Override
    public String toString() {
        if (this == null) { return "null"; }
        else if (this.color==true) { return "white ahh pawn"; }
        else { return "black ahh pawn"; }
    }
    */
    
}