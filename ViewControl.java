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
    private static visualSquare[][] graphicBoard;
    private JTextField mess = new JTextField();
    
    // Colors, for playing field and its different states
    private Color dark = new Color(115,149,90);
    private Color light = new Color(238,237,211);
    private Color lightRed = new Color(255, 179, 179);
    private Color darkRed = new Color(255, 102, 102);
    private Color lightBlue = new Color(102, 163, 255);
    private Color darkBlue = new Color(26, 117, 255);

    ViewControl (Chess gm, int n) {
        JPanel boardHost = new JPanel();
        
        graphicBoard = new visualSquare[n][n];
        game = gm;
        
        boardHost.setLayout(new GridLayout(n,n));
        
        for (int i=0; i<n; i++){     
            for (int j=0; j<n; j++){
                 
                graphicBoard[i][j] = new visualSquare();
                
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
        this.setTitle("Chess by Samuel Höglund och Josef Khedri");
        
        mess.setEditable(false);
        mess.setHorizontalAlignment(JTextField.CENTER);
        mess.setPreferredSize(new Dimension( 960, 45));
        this.add(mess, BorderLayout.NORTH);
        mess.setText("Welcome!");
    }

    private void chequer(visualSquare[][] graphicBoard, int i, int j) {
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

    private void highlightMoves(visualSquare[][] graphicBoard, int i, int j) {
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

    private void highlightAutoMove(visualSquare[][] graphicBoard, int i, int j) {
        if (i%2==0) {
            if (j%2==0) {
                graphicBoard[i][j].setBackground(darkBlue);
            }
        else {graphicBoard[i][j].setBackground(lightBlue);}
        }
        else {
            if (j%2==0) {
                graphicBoard[i][j].setBackground(lightBlue);
            }
            else {graphicBoard[i][j].setBackground(darkBlue);}
        }
    }

    private void setIcon(visualSquare[][] graphicBoard, int i, int j) {
        Piece piece = game.board[i][j].getPiece();
        if (piece!=null) {
            graphicBoard[i][j].setIcon(piece.image);
        }
        else { graphicBoard[i][j].setIcon(null); } 
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        visualSquare s = (visualSquare) e.getSource();
        int x = s.x;
        int y = s.y;
        int moveCount = game.move(x, y);    // To get corresponding graphical updates
        int i; int j;
        if(moveCount==0 | moveCount == 2 ) { // grab was made
            for (gameSquare sq:game.possibleSquares) {
                i = sq.getX(); j = sq.getY();
                highlightMoves(graphicBoard,i,j);
            }
        }
        else if(moveCount==1 || moveCount==3) { // place was made
            for (gameSquare sq:game.possibleSquares) {
                i = sq.getX(); j = sq.getY();
                chequer(graphicBoard,i,j);
            }
        }
        else if (moveCount == 5) {  // Auto-move
            i = game.startSquare.getX(); j = game.startSquare.getY();
            setIcon(graphicBoard, i, j);
            highlightAutoMove(graphicBoard, i, j);
            
            i = game.endSquare.getX(); j = game.endSquare.getY();
            setIcon(graphicBoard, i, j);
            highlightMoves(graphicBoard, i, j);
        }
        else if (moveCount == 6) {
            i = game.startSquare.getX(); j = game.startSquare.getY();
            setIcon(graphicBoard, i, j);
            chequer(graphicBoard, i, j);
            
            i = game.endSquare.getX(); j = game.endSquare.getY();
            setIcon(graphicBoard, i, j);
            chequer(graphicBoard, i, j);
        }

        mess.setText(game.getMessage());
        setIcon(graphicBoard, x, y);
    }
}
