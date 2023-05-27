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

    int moves = 0;
    Random rand = new Random();
    int whoStart;
    boolean playerFlag;

    TicTacToe() {
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setSize(1000,1000);
        mainWindow.getContentPane().setBackground(new Color(255,255,255));
        mainWindow.setVisible(true);

        infoPanel.setLayout(new BorderLayout());
        infoPanel.setBounds(0,0,1000,200);

        textInfo.setBackground(new Color(255,255,255));
        textInfo.setForeground(new Color(255,0,0));
        textInfo.setHorizontalAlignment(JLabel.CENTER);
        textInfo.setFont(new Font( "Arial",Font.BOLD,50));

        gameBoard.setLayout(new GridLayout(3,3));

        for(int i = 0; i < 9; i++){
            boardButton[i] = new JButton();
            gameBoard.add(boardButton[i]);
            boardButton[i].addActionListener(this);
            boardButton[i].setBackground(new Color(255, 255, 255));
            boardButton[i].setFont(new Font("Arial",Font.BOLD,100));
        }

        infoPanel.add(textInfo);
        mainWindow.add(infoPanel, BorderLayout.NORTH);
        mainWindow.add(gameBoard);

        Start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(int i = 0; i < 9; i++){
            if(e.getSource() == boardButton[i]){
                if(playerFlag){
                    if(Objects.equals(boardButton[i].getText(), "")){
                        boardButton[i].setForeground(new Color(0xFF06AD));
                        boardButton[i].setText("X");
                        playerFlag = false;
                        textInfo.setText("Ruch gracza O!");
                        // check win
                    }
                }
                else {
                    if(Objects.equals(boardButton[i].getText(), "")){
                        boardButton[i].setForeground(new Color(0xFF06AD));
                        boardButton[i].setText("O");
                        playerFlag = true;
                        textInfo.setText("Ruch gracza X!");
                        // check win
                    }
                }
            }

        }
    }

    public void Start(){
        whoStart = rand.nextInt(2);

        if(whoStart == 0){
            playerFlag = false;
            textInfo.setText("ZACZYNA X");
        }
        else {
            playerFlag = true;
            textInfo.setText("ZACZYNA O");
        }
    }
}
