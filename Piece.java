package proj;

abstract class Piece {
    // Inspiration från föreläsning 2
    Color color; Board board; Image image;

    Piece(String color, Board board, Image image) {
        this.color = new Color(color);
        this.board = board;
        this.image = image;
    }

    abstract boolean moveOK(Square s1, Square s2);  // Föreläsning 2
    
}
