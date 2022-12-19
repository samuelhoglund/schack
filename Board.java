package proj;

public class Board {
    Square[][] board = new Square[8][8];

    void initCoords() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j].setX(i);
                board[i][j].setY(j);
            }
        }
    }
    void initWhite() {
        board[0][0].piece = new Rook("white", this, null);
        board[0][1].piece = new Knight("white", this, null);
        board[0][2].piece = new Bishop("white", this, null);
        board[0][3].piece = new Queen("white", this, null);
        board[0][4].piece = new King("white", this, null);
        board[0][5].piece = new Bishop("white", this, null);
        board[0][6].piece = new Knight("white", this, null);
        board[0][7].piece = new Rook("white", this, null);
        for (int i = 0; i < 8; i++) {
            board[1][i].piece = new Pawn("white", this, null);
        }
    }
    void initBlack() {
        board[7][0].piece = new Rook("black", this, null);
        board[7][1].piece = new Knight("black", this, null);
        board[7][2].piece = new Bishop("black", this, null);
        board[7][4].piece = new King("black", this, null);
        board[7][3].piece = new Queen("black", this, null);
        board[7][5].piece = new Bishop("black", this, null);
        board[7][6].piece = new Knight("black", this, null);
        board[7][7].piece = new Rook("black", this, null);
        for (int i = 0; i < 8; i++) {
            board[6][i].piece = new Pawn("black", this, null);
        }
    }
}
