

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
        String tempMessage;
        
        if (white) {
            tempMessage = "White's turn. ";
        }
        else {tempMessage = "Black's turn. ";}

        Piece piece = board[x][y].getPiece();
        if (piece==null) { currMessage = tempMessage + "Grab a piece."; return piece; }
        else if (piece.color != white) { currMessage = tempMessage + "Grab your own piece."; return piece; }
        
        moveCount++;
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

        // PROMOVERING / PROMOTION
        if(p.getClass().getName().equals("Pawn") & (x==0 | x==7)) {     
            p = promotion(p, white);
        }

        board[x][y].addPiece(p);////

        // Identifiera när ett drag medför att andra spelarens kung är hotad (schack) och meddela det.
        oldSquare oppositeKingPos = findOppositeKing(white);
        boolean check = isCheck(white, oppositeKingPos);

        if (check) {
            currMessage = "Check! " + currMessage; 
        }
        
    }

    // kanske implementera en returnPiece-metod
    /* 
    public void returnPiece(oldSquare current, oldSquare destination) {
        Piece p = current.getPiece();
        current.setEmpty();
        destination.addPiece(p);
    }
    */
    
    private oldSquare findOppositeKing(boolean white) {
        oldSquare oppositeKingPos;
        Piece tempPiece;
        // om white, börja från botten; om black, börja från toppen PETITESEESPOAISOIDUJAODISADOIASH
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                tempPiece = board[i][j].getPiece();
                if (tempPiece!=null && (tempPiece.getClass().getName().equals("King") & tempPiece.color!=white)) {
                    oppositeKingPos = board[i][j];
                    return oppositeKingPos;
                }   
            }
        }
        return null;
    }

    private boolean isCheck(boolean white, oldSquare oppositeKingPos) {
        Piece tempPiece;

        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                tempPiece = board[i][j].getPiece();

                if (tempPiece!=null && tempPiece.color==white) {
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


    @Override
    public int move(int x, int y) {
        int returnInt = -1;
        
        ////////////////////// detta ska fixas härnäst, dvs att confirma/denya automovet
        if (autoMovedFrom != null) {
            oldSquare requestedSquare = board[x][y];
            if (requestedSquare.equals(autoMovedFrom)) {
                moveCount-=2; 

                placePiece(x, y, currPiece, false);
            }
        }
        
        autoMovedFrom = null;
        if(moveCount%4==2) {
            returnInt = 2;
            startSquare = board[x][y];
            currPiece = grabPiece(x,y, false);
            
            if (currPiece!=null && !currPiece.color) {     // if you picked up your own piece
                possibleSquares = analyze(startSquare, currPiece);

                if (possibleSquares.isEmpty()) {
                    moveCount-=2; 
                    placePiece(x, y, currPiece, false);
                    currMessage = "No free spots."; 
                }   // man får köra om
                else if (possibleSquares.size() == 1) {
                    autoMovedFrom = startSquare;
                    oldSquare autoMove = possibleSquares.peek();
                    placePiece(autoMove.getX(), autoMove.getY(), currPiece, false);
                    currMessage = "Auto-moved from blue to red. Approve by clicking red, disapprove by clicking blue.";
                    return 5;
                }
            }
            else { return -1; } // faulty move
            /* 
            else if (possibleSquares.size() == 1) {
                // "vill du gå hit?" "Spelaren ska kunna bekräfta det automatiska förflyttningen innan turen går över till nästa spelare." 
                oldSquare autoMove = possibleSquares.remove();
                int x2 = autoMove.getX(); int y2 = autoMove.getY();
                placePiece(x2,y2,currPiece, false);

                String ans = " ";
                while (!ans.equals("y") & !ans.equals("n")) {
                    System.out.print("Confirm move? y/n: ");
                    ans = sc.next();
                }
                if (ans.equals("n")) {
                    moveCount-=2; placePiece(x, y, currPiece, false);
                }
            }
            */
            // annars ska det vara som vanligt. ViewControl fyller i tillgängliga rutor

            }
        else if(moveCount%4==3) {
            returnInt = 3;
            endSquare = board[x][y];
            if (board[x][y].equals(startSquare)) {moveCount-=2; placePiece(x, y, currPiece, true);} // tillåta att gå tillbaka och köra igen om man e på samma ruta
            else if (currPiece.moveOK(startSquare, endSquare, board)) {
                placePiece(x,y,currPiece, false);    // eliminering av motståndarpjäs
            }
            else {return -1;}  // faulty move
            
        }
        else if(moveCount%4==0) {
            returnInt = 0;
            startSquare = board[x][y];
            currPiece = grabPiece(x,y, true);

            if (currPiece!=null && currPiece.color) {     // if you picked up your own piece
                possibleSquares = analyze(startSquare, currPiece);
            
                if (possibleSquares.isEmpty()) {
                    moveCount-=2; 
                    placePiece(x, y, currPiece, true);
                    currMessage = "No free spots."; 
                }   // man får köra om
            }
            else { return -1; } // faulty move
            /* 
            else if (possibleSquares.size() == 1) {
                // "vill du gå hit?" "Spelaren ska kunna bekräfta det automatiska förflyttningen innan turen går över till nästa spelare." 
                oldSquare autoMove = possibleSquares.peek();
                int x2 = autoMove.getX(); int y2 = autoMove.getY();
                placePiece(x2,y2,currPiece, true);
                System.out.println(x2 + " " + y2);

                String ans = " ";
                while (!ans.equals("y") & !ans.equals("n")) {
                    System.out.print("Confirm move? y/n: ");
                    ans = sc.next();
                }
                if (ans.equals("n")) {
                    moveCount-=2; placePiece(x, y, currPiece, true);
                }
            }
            */
        }
        else if(moveCount%4==1) {
            returnInt = 1;
            endSquare = board[x][y];
            if (board[x][y].equals(startSquare)) {moveCount-=2; placePiece(x, y, currPiece, false);} // tillåta att gå tillbaka och köra igen
            else if (currPiece.moveOK(startSquare, endSquare, board)) {
                placePiece(x,y,currPiece, true);    // vanlig utplacering
            }
            else {return -1;}  // faulty move   
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
        //System.out.println("Click " + count);
        return moveCount + ": " + currMessage;
    }
    
}
