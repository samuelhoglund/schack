
import javax.swing.Icon;

public class Pawn extends Piece {

    Pawn(boolean color, Chess board, Icon image) {
        super(color, board, image);
        //TODO Auto-generated constructor stub
    }

    @Override
    boolean moveOK(oldSquare s1, oldSquare s2) {
        if (this.color) {   // white
            
        }
        else {              // black

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