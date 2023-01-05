

import javax.swing.ImageIcon;
import java.util.Scanner;

import java.util.LinkedList;
import java.util.Queue;

public class Chess implements Boardgame {

    oldSquare[][] board;
    int moveCount = 0;
    Piece currPiece;    //////////
    static int count = 0;
    static String currMessage;
    String dir = System.getProperty("user.dir").replace("\\", "\\\\") + "\\\\images\\\\";
    oldSquare startSquare; oldSquare endSquare;
    Queue<oldSquare> possibleSquares = new LinkedList<>();  // initally empty. To be filled with available spots a piece can move to.
    Scanner sc = new Scanner(System.in);
    oldSquare autoMovedFrom = null;
    Piece autoMovedKilled = null;

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

    private Piece grabPiece(int x, int y, boolean color) {
        String tempMessage;
        
        if (color) {
            tempMessage = "White's turn. ";
        }
        else {tempMessage = "Black's turn. ";}

        Piece piece = board[x][y].getPiece();
        if (piece==null) { currMessage = tempMessage + "Grab a piece."; return piece; }
        else if (piece.color != color) { currMessage = tempMessage + "Grab your own piece."; return piece; }
        
        moveCount++;
        board[x][y].setEmpty();

        currMessage = tempMessage + "Place your piece.";

        return piece;
    }
    private void placePiece(int x, int y, Piece p, boolean color) {
        String tempMessage;
        if (!color) {   // color = false (black)
            tempMessage = "White's turn. ";
        }
        else {tempMessage = "Black's turn. ";}
        currMessage = tempMessage + "Grab a piece. ";
        
        moveCount++;

        // PROMOVERING / PROMOTION
        if(p.getClass().getName().equals("Pawn") & (x==0 | x==7)) {     
            p = promotion(p, color);
        }

        board[x][y].addPiece(p);////

        // Identifiera när ett drag medför att andra spelarens kung är hotad (schack) och meddela det.
        oldSquare oppositeKingPos = findOppositeKing(color);
        if (oppositeKingPos!=null) {
            boolean check = isCheck(color, oppositeKingPos);
            if (check) {
                currMessage = "Check! " + currMessage; 
            }
        }
        else {
            currMessage = "Game over!";
        }
        
    }
    
    private oldSquare findOppositeKing(boolean color) {
        // boolean color: white = true, black = false.
        oldSquare oppositeKingPos;
        Piece tempPiece;

        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                tempPiece = board[i][j].getPiece();
                if (tempPiece!=null && (tempPiece.getClass().getName().equals("King") & tempPiece.color!=color)) {
                    oppositeKingPos = board[i][j];
                    return oppositeKingPos;
                }   
            }
        }
        return null;
    }

    private boolean isCheck(boolean color, oldSquare oppositeKingPos) {
        // boolean color: white = true, black = false.
        Piece tempPiece;
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                tempPiece = board[i][j].getPiece();

                if (tempPiece!=null && tempPiece.color==color) {
                    oldSquare thisSquare = board[i][j];
                    if (tempPiece.moveOK(thisSquare, oppositeKingPos, board)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private Piece promotion(Piece p, boolean white) {
        if (white) {
            p = new Queen(true, this, new ImageIcon(dir + "WhiteQueen.png"));
        }
        else {
            p = new Queen(false, this, new ImageIcon(dir + "BlackQueen.png"));
        }
        return p;
    }


    private int assertAutoMove(int x, int y) {
        oldSquare requestedSquare = board[x][y];
            if (requestedSquare.equals(autoMovedFrom)) {
                // movet denyat. gå tillbaka till föregående ruta och låta användaren köra igen
                if(moveCount%4==2) {
                    moveCount-=2; 
                    placePiece(x, y, currPiece, false);
                    if (autoMovedKilled!=null) {
                        moveCount -=2;
                        placePiece(endSquare.x, endSquare.y, autoMovedKilled, autoMovedKilled.color); 
                    }
                    else {
                        moveCount--;
                        endSquare.setEmpty();
                    }
                }
                else if(moveCount%4==0) {
                    moveCount-=2; 
                    placePiece(x, y, currPiece, true);
                    if (autoMovedKilled!=null) {
                        moveCount -=2;
                        placePiece(endSquare.x, endSquare.y, autoMovedKilled, autoMovedKilled.color); 
                    }
                    else {
                        moveCount--;
                        endSquare.setEmpty();
                    }
                }
            }
            else if (requestedSquare.equals(endSquare)) {

                // movet confirmat. turen går över.  
                String tempMessage = "";

                // Change message box
                if (moveCount%4 == 0) {
                    tempMessage = "White's turn. ";
                }
                else { tempMessage = "Black's turn. "; }
                currMessage = tempMessage + "Grab a piece.";       
            }
            else { 
                currMessage = "Faulty move. Please press the blue or red square.";
                return -1;
            }
            autoMovedFrom = null; autoMovedKilled = null; // reset autoMove fields
            return 6;
    }

    private int grabPhase(int x, int y, boolean color) {
        int returnInt = -1;
        if (color) {
            returnInt = 0;
        }
        else { returnInt = 2; }
        
        startSquare = board[x][y];
        currPiece = grabPiece(x,y, color);
        
        if (currPiece!=null && currPiece.color==color) {     // if you picked up your own piece
            possibleSquares = analyze(startSquare, currPiece);

            if (possibleSquares.isEmpty()) {
                moveCount-=2; 
                placePiece(x, y, currPiece, color);
                currMessage = "No free spots."; 
            }   // man får köra om
            else if (possibleSquares.size() == 1) {
                System.out.println("analyze size 1");
                autoMovedFrom = startSquare;
                oldSquare autoMove = possibleSquares.peek();
                endSquare = board[autoMove.getX()][autoMove.getY()];
                
                if (endSquare.hasPiece()) {
                    autoMovedKilled = endSquare.getPiece();     // save the killed piece in case it is to be recovered
                }

                placePiece(autoMove.getX(), autoMove.getY(), currPiece, color);
                currMessage = "Auto-moved from blue to red. Approve by clicking red, disapprove by clicking blue.";
                return 5;   ///////
            }
        }
        else { return -1; } // faulty move
        return returnInt;
    }

    private int placePhase(int x, int y, boolean color) {
        int returnInt = -1;
        if (color) {
            returnInt = 1;
        }
        else {
            returnInt = 3;
        }
        endSquare = board[x][y];
        if (board[x][y].equals(startSquare)) {moveCount-=2; placePiece(x, y, currPiece, !color);} // tillåta att gå tillbaka och köra igen
        else if (currPiece.moveOK(startSquare, endSquare, board)) {
            placePiece(x,y,currPiece, color);    // vanlig utplacering
        }
        else {return -1;}  // faulty move   
    return returnInt;     
    }

    @Override
    public int move(int x, int y) {
        int returnInt = -1;
        if (autoMovedFrom != null) {
            return assertAutoMove(x, y);
        }
        if(moveCount%4==2) {        // Black grab
           return grabPhase(x, y, false);
            }
        else if(moveCount%4==3) {       // Black place
            return placePhase(x, y, false);
        }
        else if(moveCount%4==0) {       // White grab
            return grabPhase(x, y, true);
        }
        else if(moveCount%4==1) {       // White place
            return placePhase(x, y, true);
        }    
    return returnInt;     // used in ViewControl
    }

    public Queue<oldSquare> analyze(oldSquare s1, Piece p) {
        Queue<oldSquare> possibleSquares = new LinkedList<>();
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                oldSquare s2 = board[i][j];
                if (p.moveOK(s1, s2, board)) {
                    possibleSquares.add(s2);
                }
            }
        }
        return possibleSquares;
    }


    @Override
    public oldSquare getStatus(int x, int y) {
        return board[x][y];
    }

    @Override
    public String getMessage() {
        return moveCount + ": " + currMessage;
    }
    
}
