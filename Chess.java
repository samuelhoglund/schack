

import javax.swing.ImageIcon;

public class Chess implements Boardgame {

    oldSquare[][] board;
    int moveCount = 0;
    Piece currPiece;    //////////
    static int count = 0;
    static String currMessage;
    String dir = System.getProperty("user.dir").replace("\\", "\\\\") + "\\\\images\\\\";
    oldSquare startSquare; oldSquare endSquare;

    public Chess() {
        this.board = new oldSquare[8][8];
        initCoords(); initWhite(); initBlack();
    }

    void initCoords() {     // initializing all squares and their coordinates
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = new oldSquare(i, j, null);
            }
        }
    }
    void initWhite() {
        board[7][0].piece = new Rook(true, this, new ImageIcon(dir + "WhiteRook.png"));
        board[7][1].piece = new Knight(true, this, new ImageIcon(dir + "WhiteKnight.png"));
        board[7][2].piece = new Bishop(true, this, new ImageIcon(dir + "WhiteBishop.png"));
        board[7][3].piece = new Queen(true, this, new ImageIcon(dir + "WhiteQueen.png"));
        board[7][4].piece = new King(true, this, new ImageIcon(dir + "WhiteKing.png"));
        board[7][5].piece = new Bishop(true, this, new ImageIcon(dir + "WhiteBishop.png"));
        board[7][6].piece = new Knight(true, this, new ImageIcon(dir + "WhiteKnight.png"));
        board[7][7].piece = new Rook(true, this, new ImageIcon(dir + "WhiteRook.png"));
        for (int i = 0; i < 8; i++) {
            board[6][i].piece = new Pawn(true, this, new ImageIcon(dir + "WhitePawn.png"));
        }
    }
    void initBlack() {
        board[0][0].piece = new Rook(false, this, new ImageIcon(dir + "BlackRook.png"));
        board[0][1].piece = new Knight(false, this, new ImageIcon(dir + "BlackKnight.png"));
        board[0][2].piece = new Bishop(false, this, new ImageIcon(dir + "BlackBishop.png"));
        board[0][4].piece = new King(false, this, new ImageIcon(dir + "BlackKing.png"));
        board[0][3].piece = new Queen(false, this, new ImageIcon(dir + "BlackQueen.png"));
        board[0][5].piece = new Bishop(false, this, new ImageIcon(dir + "BlackBishop.png"));
        board[0][6].piece = new Knight(false, this, new ImageIcon(dir + "BlackKnight.png"));
        board[0][7].piece = new Rook(false, this, new ImageIcon(dir + "BlackRook.png"));
        for (int i = 0; i < 8; i++) {
            board[1][i].piece = new Pawn(false, this, new ImageIcon(dir + "BlackPawn.png"));
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
        if (piece==null ) { currMessage = tempMessage + "Grab a piece."; return piece; }
        else if (piece.color != white) { currMessage = tempMessage + "Grab your own piece."; return piece; }
        
        moveCount++;
        System.out.println(moveCount);
        board[x][y].setEmpty();

        currMessage = tempMessage + "Place your piece.";

        return piece;
    }
    private void placePiece(int x, int y, Piece p, boolean white) {
        String tempMessage;
        if (!white) {
            tempMessage = "White's turn. ";
        }
        else {tempMessage = "Black's turn. ";}
        currMessage = tempMessage + "Grab a piece. ";
        
        moveCount++;
        board[x][y].addPiece(p);//// 
        
    }


    @Override
    public boolean move(int x, int y) {
        if(moveCount%4==2) {
            startSquare = board[x][y];
            currPiece = grabPiece(x,y, false);
            // kan ta ut gamla squaren
            //currMessage = "Black's turn. Grab a piece.";
            }
        else if(moveCount%4==3) {
            if ( board[x][y].hasPiece() && board[x][y].getPiece().color != currPiece.color) {       // KANSKE GÖRA TILL HJÄLPMETOD FÖR ATT SVART OCH VITS KOD ÄR SKITLIKA
                endSquare = board[x][y];
                if (currPiece.moveOK(startSquare, endSquare, board)) {
                    placePiece(x,y,currPiece, false);    // eliminering av motståndarpjäs
                }
                
            }
            else if ( !board[x][y].hasPiece()) {
                endSquare = board[x][y];
                if (currPiece.moveOK(startSquare, endSquare, board)) {
                    placePiece(x,y,currPiece, false);    // vanlig utplacering
                }
            }
            else if (board[x][y].equals(startSquare)) {moveCount--; placePiece(x, y, currPiece, false);} // tillåta att gå tillbaka och köra igen
            else { currMessage = "Faulty move."; }
            //currMessage = "Black's turn. Place your piece.";
        }
        else if(moveCount%4==0) {
            startSquare = board[x][y];
            currPiece = grabPiece(x,y, true);
            //currMessage = "White's turn. Grab a piece.";
            }
        else if(moveCount%4==1) {
            if ( board[x][y].hasPiece() && board[x][y].getPiece().color != currPiece.color) {
                endSquare = board[x][y];
                if (currPiece.moveOK(startSquare, endSquare, board)) {
                    placePiece(x,y,currPiece, true);   // eliminering av motståndarpjs
                }
            }
            else if ( !board[x][y].hasPiece()) {
                endSquare = board[x][y];
                if (currPiece.moveOK(startSquare, endSquare, board)) {
                    placePiece(x,y,currPiece, true);    // vanlig utplacering
                }
            }
            else if (board[x][y].equals(startSquare)) {moveCount--; placePiece(x, y, currPiece, true);} // tillåta att gå tillbaka och köra igen
            else { currMessage = "Faulty move."; }
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
        //System.out.println("Click " + count);
        return moveCount + ": " + currMessage;
    }
    
}
