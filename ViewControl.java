import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.event.*;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;


class ViewControl extends JFrame implements ActionListener {

    private static Chess game;
    private static int size;
    private static Square[][] graphicBoard;
    private JTextField mess = new JTextField();                     // JLabel funkar också
    private Color dark = new Color(115,149,90);
    private Color light = new Color(238,237,211);
    private Color lightRed = new Color(255, 179, 179);
    private Color darkRed = new Color(255, 102, 102);
    String dir = System.getProperty("user.dir").replace("\\", "\\\\") + "\\\\proj\\\\images";
    boolean movePhase = true;

    ViewControl (Chess gm, int n) {
        JPanel boardHost = new JPanel();
        
        graphicBoard = new Square[n][n];
        game = gm;
        size = n;
        
        boardHost.setLayout(new GridLayout(n,n));
        System.out.println(dir);
        
        for (int i=0; i<n; i++){     
            for (int j=0; j<n; j++){
                 
                graphicBoard[i][j] = new Square();
                
                graphicBoard[i][j].x = i;
                graphicBoard[i][j].y = j;
                

                graphicBoard[i][j].setBorderPainted(false);
                graphicBoard[i][j].setFocusPainted( false );

                chequer(graphicBoard, i, j);
                setIcon(graphicBoard, i, j);

                
                graphicBoard[i][j].addActionListener(this);
                boardHost.add(graphicBoard[i][j]);
            }
            this.add(boardHost);
        }

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(960, 1050);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setTitle("Samuel Khedri och Josef Höglund");
        
        mess.setEditable(false);
        mess.setHorizontalAlignment(JTextField.CENTER);
        mess.setPreferredSize(new Dimension( 960, 45));
        this.add(mess, BorderLayout.NORTH);
        mess.setText("Welcome!");
    }

    private void chequer(Square[][] graphicBoard, int i, int j) {
        if (i%2==0) {
            if (j%2==0) {
                graphicBoard[i][j].setBackground(dark);
            }
        else {graphicBoard[i][j].setBackground(light);}
        }
        else {
            if (j%2==0) {
                graphicBoard[i][j].setBackground(light);
            }
            else {graphicBoard[i][j].setBackground(dark);}
        }
    }

    private void highlightMoves(Square[][] graphicBoard, int i, int j) {
        if (i%2==0) {
            if (j%2==0) {
                graphicBoard[i][j].setBackground(darkRed);
            }
        else {graphicBoard[i][j].setBackground(lightRed);}
        }
        else {
            if (j%2==0) {
                graphicBoard[i][j].setBackground(lightRed);
            }
            else {graphicBoard[i][j].setBackground(darkRed);}
        }
    }

    private void setIcon(Square[][] graphicBoard, int i, int j) {
        Piece piece = game.board[i][j].getPiece();
        if (piece!=null) {
            graphicBoard[i][j].setIcon(piece.image);
        }
        else { graphicBoard[i][j].setIcon(null); } 
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Square s = (Square) e.getSource();
        int x = s.x;//getX();
        int y = s.y;//getY();
        int moveCount = game.move(x, y);
        int i; int j;
        if(moveCount==2) { // black grab WAS MADE
            for (oldSquare sq:game.possibleSquares) {
                i = sq.getX(); j = sq.getY();
                highlightMoves(graphicBoard,i,j);
            }
        }
        else if(moveCount==3) { // black place WAS MADE
            for (oldSquare sq:game.possibleSquares) {
                i = sq.getX(); j = sq.getY();
                chequer(graphicBoard,i,j);
            }
        }
        else if(moveCount==0) { // white grab WAS MADE
            for (oldSquare sq:game.possibleSquares) {
                i = sq.getX(); j = sq.getY();
                highlightMoves(graphicBoard,i,j);
            }
        }
        else if(moveCount==1) { // white place WAS MADE
            for (oldSquare sq:game.possibleSquares) {
                i = sq.getX(); j = sq.getY();
                chequer(graphicBoard,i,j);
            }
        }
        else {
            System.out.println("hamande i movecount = -1");
        }


        mess.setText(game.getMessage());
        setIcon(graphicBoard, x, y);

        movePhase = !movePhase;
        
        /* 
        for (int ii=0; ii<size; ii++){         // Update the buttonfield with getStatus(i, j)
            for (int jj=0; jj<size; jj++){
                //setIcon(graphicBoard, i, j); 
                //board[i][j].setText(game.getStatus(i, j));
            }
        }  */      
    }
}
