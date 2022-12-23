public class Board {
    oldSquare[][] board = new oldSquare[8][8];
    String dir = System.getProperty("user.dir").replace("\\", "\\\\") + "\\\\proj\\\\images";


    public oldSquare getSquare(int x, int y) {
        return board[x][y];
    }

    /* 
    void initCoords() {     // initializing all squares and their coordinates
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = new oldSquare(i, j, null);
                //board[i][j].setX(i);
                //board[i][j].setY(j);
            }
        }
    }
    void initWhite() {
        System.out.println("hello");
        board[0][0].piece = new Rook(true, this, new ImageIcon(dir + "WhiteRook.png"));
        board[0][1].piece = new Knight(true, this, new ImageIcon(dir + "WhiteKnight.png"));
        board[0][2].piece = new Bishop(true, this, new ImageIcon(dir + "WhiteBishop.png"));
        board[0][3].piece = new Queen(true, this, new ImageIcon(dir + "WhiteQueen.png"));
        board[0][4].piece = new King(true, this, new ImageIcon(dir + "WhiteKing.png"));
        board[0][5].piece = new Bishop(true, this, new ImageIcon(dir + "WhiteBishop.png"));
        board[0][6].piece = new Knight(true, this, new ImageIcon(dir + "WhiteKnight.png"));
        board[0][7].piece = new Rook(true, this, new ImageIcon(dir + "WhiteRook.png"));
        for (int i = 0; i < 8; i++) {
            board[1][i].piece = new Pawn(true, this, new ImageIcon(dir + "WhitePawn.png"));
        }
    }
    void initBlack() {
        board[7][0].piece = new Rook(false, this, new ImageIcon(dir + "BlackRook.png"));
        board[7][1].piece = new Knight(false, this, new ImageIcon(dir + "BlackKnight.png"));
        board[7][2].piece = new Bishop(false, this, new ImageIcon(dir + "BlackBishop.png"));
        board[7][4].piece = new King(false, this, new ImageIcon(dir + "BlackKing.png"));
        board[7][3].piece = new Queen(false, this, new ImageIcon(dir + "BlackQueen.png"));
        board[7][5].piece = new Bishop(false, this, new ImageIcon(dir + "BlackBishop.png"));
        board[7][6].piece = new Knight(false, this, new ImageIcon(dir + "BlackKnight.png"));
        board[7][7].piece = new Rook(false, this, new ImageIcon(dir + "BlackRook.png"));
        for (int i = 0; i < 8; i++) {
            board[6][i].piece = new Pawn(false, this, new ImageIcon(dir + "BlackPawn.png"));
        }
    }
    */
}
