

import javax.swing.ImageIcon;

public class Chess implements Boardgame {

    oldSquare[][] board;
    int moveCount = 0;
    Piece currPiece;    //////////
    static int count = 0;
    static String currMessage;
    String dir = System.getProperty("user.dir").replace("\\", "\\\\") + "\\\\images\\\\";

    public Chess() {
        this.board = new oldSquare[8][8];
        initCoords(); initWhite(); initBlack();
    }

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


    private Piece grabPiece(int x, int y, boolean white) {
        System.out.println("här");
        String tempMessage;
        if (white) {
            tempMessage = "White's turn. ";
        }
        else {tempMessage = "Black's turn. ";}

        Piece piece = board[x][y].getPiece();
        if (piece==null) { currMessage = tempMessage + "Grab a piece"; return piece; }
        
        moveCount++;
        System.out.println(moveCount);
        board[x][y].setEmpty();

        if (white) {
            tempMessage = "White's turn. ";
        }

        currMessage = tempMessage + "Place your piece.";

        return piece;
    }
    private void placePiece(int x, int y, Piece p, boolean white) {
        String tempMessage;
        if (white) {
            tempMessage = "White's turn. ";
        }
        else {tempMessage = "Black's turn. ";}
        currMessage = tempMessage + "Grab a piece. ";
        
        moveCount++;
        board[x][y].addPiece(p);//// 
    }


    @Override
    public boolean move(int x, int y) {
        //if (board[x][y].piece==null) {System.out.println("empty"); return false; }
        
        //Piece piece = board[x][y].getPiece();
        //System.out.println(piece.toString());
        System.out.println("moveCunt=" + moveCount);

        if(moveCount%4==2) {
            currPiece = grabPiece(x,y, false);
            //currMessage = "Black's turn. Grab a piece.";
            }
        else if(moveCount%4==3) {
            placePiece(x,y,currPiece, true);
            //currMessage = "Black's turn. Place your piece.";
        }
        else if(moveCount%4==0) {
            currPiece = grabPiece(x,y, true);
            //currMessage = "White's turn. Grab a piece.";
            }
        else if(moveCount%4==1) {
            placePiece(x,y,currPiece,false);
            //currMessage = "White's turn. Place your piece.";
        }
    
    return false;

    }

    @Override
    public oldSquare getStatus(int x, int y) {
        return board[x][y];
    }

    @Override
    public String getMessage() {
        count += 1;
        //System.out.println("Click " + count);
        return count + ": " + currMessage;
    }
    
}
