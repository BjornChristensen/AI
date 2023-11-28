package TicTacToe;
// GUI til TicTacToe
// 26/11-2023

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeGUI extends JFrame {
    MyButton[][] buttons=new MyButton[3][3];				// The buttons
    GameState state=new GameState();
    int searchDepth=4;

    public static void main(String[] args) {
        TicTacToeGUI frame = new TicTacToeGUI();
        frame.setTitle("TicTacToe");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setVisible(true);
    }

    public TicTacToeGUI() {
        setLayout(new BorderLayout());
        JPanel center=new JPanel(new GridLayout(3, 3));
        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                buttons[i][j]=new MyButton(i,j);
                center.add(buttons[i][j]);
            }
        }
        add(center, BorderLayout.CENTER);
        JButton moveButtom=new JButton("AI-move");
        add(moveButtom, BorderLayout.SOUTH);

        // Make an AI move
        moveButtom.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                state.AB(-GameState.inf, GameState.inf, searchDepth);
                Move m=state.bestMove;
                buttons[m.row][m.col].setFont(new Font("Sans Serif", Font.PLAIN, 40));
                buttons[m.row][m.col].setText(m.player+""); // make move on GUI
                buttons[m.row][m.col].setEnabled(false);
                state=state.makeMove(state.bestMove);   // make move in current state
                if (state.threeInARow()) JOptionPane.showMessageDialog(null, "Won by "+m.player, "Game over", JOptionPane.OK_OPTION);
                if (state.boardIsFull()) JOptionPane.showMessageDialog(null, "The game is draw", "Game over", JOptionPane.OK_OPTION);
            }
        });
    }

    class MyButton extends JButton  implements ActionListener {
        int row,col;    // MyButton remember its position on the board
        MyButton(int r, int c) {
            row=r; col=c;
            setText(r+","+c);
            setBackground(Color.CYAN);
            addActionListener(this);
        }

        // User moves by pressing a button
        public void actionPerformed(ActionEvent e) {
            MyButton b=(MyButton)e.getSource();
            b.setFont(new Font("Sans Serif", Font.PLAIN, 40));
            b.setText(state.playerToMove+"");
            b.setEnabled(false);
            Move m=new Move(state.playerToMove, b.row, b.col);
            state=state.makeMove(m);
            System.out.println(state);
            if (state.threeInARow()) JOptionPane.showMessageDialog(null, "Won by "+m.player, "Game over", JOptionPane.OK_OPTION);
            if (state.boardIsFull()) JOptionPane.showMessageDialog(null, "The game is draw", "Game over", JOptionPane.OK_OPTION);
        }
    }
} // class TicTacToeGUI