

import javax.swing.ImageIcon;

import java.util.LinkedList;
import java.util.Queue;

public class Chess implements Boardgame {

    // Game functioning fields
    gameSquare[][] board;
    int moveCount = 0;
    Piece currPiece; 
    static String currMessage;
    gameSquare startSquare; gameSquare endSquare;
    Queue<gameSquare> possibleSquares = new LinkedList<>();  // initally empty. To be filled with available spots a piece can move to.

    // For image management
    String dir = System.getProperty("user.dir").replace("\\", "\\\\") + "\\\\images\\\\";
    
    // Auto-move fields
    gameSquare autoMovedFrom = null;
    Piece autoMovedKilled = null;

    public Chess() {
        this.board = new gameSquare[8][8];
        initCoords(); initWhite(); initBlack();
    }

    void initCoords() {     // initializing all squares and their coordinates
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = new gameSquare(i, j, null);
            }
        }
    }
    void initWhite() {
        board[7][0].piece = new Rook(true, new ImageIcon(dir + "WhiteRook.png"));
        board[7][1].piece = new Knight(true, new ImageIcon(dir + "WhiteKnight.png"));
        board[7][2].piece = new Bishop(true, new ImageIcon(dir + "WhiteBishop.png"));
        board[7][3].piece = new Queen(true, new ImageIcon(dir + "WhiteQueen.png"));
        board[7][4].piece = new King(true, new ImageIcon(dir + "WhiteKing.png"));
        board[7][5].piece = new Bishop(true, new ImageIcon(dir + "WhiteBishop.png"));
        board[7][6].piece = new Knight(true, new ImageIcon(dir + "WhiteKnight.png"));
        board[7][7].piece = new Rook(true, new ImageIcon(dir + "WhiteRook.png"));
        for (int i = 0; i < 8; i++) {
            board[6][i].piece = new Pawn(true, new ImageIcon(dir + "WhitePawn.png"));
        }
    }
    void initBlack() {
        board[0][0].piece = new Rook(false, new ImageIcon(dir + "BlackRook.png"));
        board[0][1].piece = new Knight(false, new ImageIcon(dir + "BlackKnight.png"));
        board[0][2].piece = new Bishop(false, new ImageIcon(dir + "BlackBishop.png"));
        board[0][4].piece = new King(false, new ImageIcon(dir + "BlackKing.png"));
        board[0][3].piece = new Queen(false, new ImageIcon(dir + "BlackQueen.png"));
        board[0][5].piece = new Bishop(false, new ImageIcon(dir + "BlackBishop.png"));
        board[0][6].piece = new Knight(false, new ImageIcon(dir + "BlackKnight.png"));
        board[0][7].piece = new Rook(false, new ImageIcon(dir + "BlackRook.png"));
        for (int i = 0; i < 8; i++) {
            board[1][i].piece = new Pawn(false, new ImageIcon(dir + "BlackPawn.png"));
        }
    }

    // Handles the different turns in the game.
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

    // Handles all cases during a grab phase
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
            }   
            else if (possibleSquares.size() == 1) {
                autoMovedFrom = startSquare;
                gameSquare autoMove = possibleSquares.peek();
                endSquare = board[autoMove.getX()][autoMove.getY()];
                
                if (endSquare.hasPiece()) {
                    autoMovedKilled = endSquare.getPiece();     // save the killed piece in case it is to be recovered
                }

                placePiece(autoMove.getX(), autoMove.getY(), currPiece, color);
                currMessage = "Auto-moved from blue to red. Approve by clicking red, disapprove by clicking blue.";
                return 5;   
            }
        }
        else { return -1; } // faulty move
        return returnInt;
    }

    // Handles all cases during a place phase
    private int placePhase(int x, int y, boolean color) {
        int returnInt = -1;
        if (color) {
            returnInt = 1;
        }
        else {
            returnInt = 3;
        }
        endSquare = board[x][y];
        if (board[x][y].equals(startSquare)) {moveCount-=2; placePiece(x, y, currPiece, !color);} // Allows user to go revoke move
        else if (currPiece.moveOK(startSquare, endSquare, board)) {
            placePiece(x,y,currPiece, color);    // Normal placement
        }
        else {return -1;}  // faulty move   
    return returnInt;     
    }

    // Picks up a piece at board[x][y]
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

    // Places the piece that has been picked up in the previous move
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

        // "Identifiera när ett drag medför att andra spelarens kung är hotad (schack) och meddela det."
        gameSquare oppositeKingPos = findOppositeKing(color);
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

    // Analyzes and saves the possible squares that a piece can travel to
    public Queue<gameSquare> analyze(gameSquare s1, Piece p) {
        Queue<gameSquare> possibleSquares = new LinkedList<>();
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                gameSquare s2 = board[i][j];
                if (p.moveOK(s1, s2, board)) {
                    possibleSquares.add(s2);
                }
            }
        }
        return possibleSquares;
    }
    
    // Finds the opposing king's position
    private gameSquare findOppositeKing(boolean color) {
        // boolean color: white = true, black = false.
        gameSquare oppositeKingPos;
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

    // Analyzes the field to control whether a user has check or not
    private boolean isCheck(boolean color, gameSquare oppositeKingPos) {
        // boolean color: white = true, black = false.
        Piece tempPiece;
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                tempPiece = board[i][j].getPiece();

                if (tempPiece!=null && tempPiece.color==color) {
                    gameSquare thisSquare = board[i][j];
                    if (tempPiece.moveOK(thisSquare, oppositeKingPos, board)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // Promotes a pawn
    private Piece promotion(Piece p, boolean white) {
        if (white) {
            p = new Queen(true, new ImageIcon(dir + "WhiteQueen.png"));
        }
        else {
            p = new Queen(false, new ImageIcon(dir + "BlackQueen.png"));
        }
        return p;
    }

    // Returns the killed opponent
    private void returnAutoMoved(int x, int y, boolean color) {
        moveCount-=2; 
        placePiece(x, y, currPiece, color);
        if (autoMovedKilled!=null) {
            
            moveCount -=2;
            placePiece(endSquare.x, endSquare.y, autoMovedKilled, autoMovedKilled.color); 
        }
        else {
            moveCount--;
            endSquare.setEmpty();
        }
    }

    // Entered when auto-move has been made. Prompts the user to accept or deny the auto-move
    private int assertAutoMove(int x, int y) {
        gameSquare requestedSquare = board[x][y];
            if (requestedSquare.equals(autoMovedFrom)) {
                // Move denied. Go back to the previous square and let the user play again.
                if(moveCount%4==2) {
                    returnAutoMoved(x, y, false);
                }
                else if(moveCount%4==0) {
                    returnAutoMoved(x, y, true);
                }
            }
            else if (requestedSquare.equals(endSquare)) {
                // Move confirmed.
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

    @Override
    public gameSquare getStatus(int x, int y) {
        return board[x][y];
    }

    @Override
    public String getMessage() {
        return moveCount + ": " + currMessage;
    }
}
