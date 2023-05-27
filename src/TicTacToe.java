import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class TicTacToe implements ActionListener {
    JFrame mainWindow = new JFrame();
    JPanel infoPanel = new JPanel();
    JPanel gameBoard = new JPanel();
    JLabel textInfo = new JLabel();
    JButton[] boardButton = new JButton[9];

    TicTacToe() {
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setSize(1000,1000);
        mainWindow.getContentPane().setBackground(new Color(255,255,255));
        mainWindow.setVisible(true);

        gameBoard.setLayout(new GridLayout(3,3));

        for(int i = 0; i < 9; i++){
            boardButton[i] = new JButton();
            gameBoard.add(boardButton[i]);
            boardButton[i].addActionListener(this);
        }

        mainWindow.add(gameBoard);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
