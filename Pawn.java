package proj;

public class Pawn extends Piece {

    Pawn(String color, Board board, Image image) {
        super(color, board, image);
        //TODO Auto-generated constructor stub
    }

    @Override
    boolean moveOK(Square s1, Square s2) {
        // TODO Auto-generated method stub
        return false;
    }
    
}