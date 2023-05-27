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
    Players playerFlag;

    TicTacToe() {
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setSize(1000,1000);
        mainWindow.getContentPane().setBackground(new Color(255,255,255));
        mainWindow.setVisible(true);
        mainWindow.setTitle("Kółko i krzyżyk");

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
                if(playerFlag == Players.X){
                    if(Objects.equals(boardButton[i].getText(), "")){
                        boardButton[i].setForeground(new Color(0xF50707));
                        boardButton[i].setText("X");
                        playerFlag = Players.O;
                        textInfo.setText("Ruch gracza O!");
                    }
                }
                else {
                    if(Objects.equals(boardButton[i].getText(), "")){
                        boardButton[i].setForeground(new Color(0x075AF5));
                        boardButton[i].setText("O");
                        playerFlag = Players.X;
                        textInfo.setText("Ruch gracza X!");
                    }
                }
                moves++;
                checkWin();
            }

        }
    }

    public void Start(){
        whoStart = rand.nextInt(2);

        if(whoStart == 0){
            playerFlag = Players.X;
            textInfo.setText("ZACZYNA X");
        }
        else {
            playerFlag = Players.O;
            textInfo.setText("ZACZYNA O");
        }
    }
    public void endGame(Players result){
        String message;
        if(result == Players.X){
            message = "Wygral X!";
        }
        else if(result == Players.O){
            message = "Wygral Y!";
        }
        else {
            message = "Remis!";
        }
        Object[] buttons ={"Restart","Wyjdź"};
        int check = JOptionPane.showOptionDialog(mainWindow, message + "\nChcesz zagrac jeszcze raz?","Koniec gry!",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,buttons,buttons[0]);

        if(check == 0){
            mainWindow.dispose();
            new TicTacToe();
        }
        else{
            mainWindow.dispose();
        }
    }

    public void checkWin(){
        if(boardButton[0].getText().equals("X") && boardButton[1].getText().equals("X") && boardButton[2].getText().equals("X")){
            setWin(Players.X,0,1,2);
        }
        else if(boardButton[0].getText().equals("X") && boardButton[3].getText().equals("X") && boardButton[6].getText().equals("X")){
            setWin(Players.X,0,3,6);
        }
        else if(boardButton[0].getText().equals("X") && boardButton[4].getText().equals("X") && boardButton[8].getText().equals("X")){
            setWin(Players.X,0,4,8);
        }
        else if(boardButton[1].getText().equals("X") && boardButton[4].getText().equals("X") && boardButton[7].getText().equals("X")){
            setWin(Players.X,1,4,7);
        }
        else if(boardButton[2].getText().equals("X") && boardButton[5].getText().equals("X") && boardButton[8].getText().equals("X")){
            setWin(Players.X,2,5,8);
        }
        else if(boardButton[3].getText().equals("X") && boardButton[4].getText().equals("X") && boardButton[5].getText().equals("X")){
            setWin(Players.X,3,4,5);
        }
        else if(boardButton[6].getText().equals("X") && boardButton[7].getText().equals("X") && boardButton[8].getText().equals("X")){
            setWin(Players.X,6,7,8);
        }
        else if(boardButton[2].getText().equals("X") && boardButton[4].getText().equals("X") && boardButton[6].getText().equals("X")){
            setWin(Players.X,2,4,6);
        }
        else if(boardButton[0].getText().equals("O") && boardButton[1].getText().equals("O") && boardButton[2].getText().equals("O")){
            setWin(Players.O,0,1,2);
        }
        else if(boardButton[0].getText().equals("O") && boardButton[3].getText().equals("O") && boardButton[6].getText().equals("O")){
            setWin(Players.O,0,3,6);
        }
        else if(boardButton[0].getText().equals("O") && boardButton[4].getText().equals("O") && boardButton[8].getText().equals("O")){
            setWin(Players.O,0,4,8);
        }
        else if(boardButton[1].getText().equals("O") && boardButton[4].getText().equals("O") && boardButton[7].getText().equals("O")){
            setWin(Players.O,1,4,7);
        }
        else if(boardButton[2].getText().equals("O") && boardButton[5].getText().equals("O") && boardButton[8].getText().equals("O")){
            setWin(Players.O,2,5,8);
        }
        else if(boardButton[3].getText().equals("O") && boardButton[4].getText().equals("O") && boardButton[5].getText().equals("O")){
            setWin(Players.O,3,4,5);
        }
        else if(boardButton[6].getText().equals("O") && boardButton[7].getText().equals("O") && boardButton[8].getText().equals("O")){
            setWin(Players.O,6,7,8);
        }
        else if(boardButton[2].getText().equals("O") && boardButton[4].getText().equals("O") && boardButton[6].getText().equals("O")){
            setWin(Players.O,2,4,6);
        }
        else if(moves == 9){
            setWin(Players.TIE,0,0,0);
        }
    }

    public void setWin(Players winner,int x1,int x2, int x3){
        if(winner == Players.X){
            boardButton[x1].setBackground(new Color(0xF50707));
            boardButton[x1].setForeground(new Color(0xF4F4F6));
            boardButton[x2].setBackground(new Color(0xF50707));
            boardButton[x2].setForeground(new Color(0xF4F4F6));
            boardButton[x3].setBackground(new Color(0xF50707));
            boardButton[x3].setForeground(new Color(0xF4F4F6));
            textInfo.setText("Wygrał X!");
        }
        else if(winner == Players.O) {
            boardButton[x1].setBackground(new Color(0x075AF5));
            boardButton[x1].setForeground(new Color(0xF4F4F6));
            boardButton[x2].setBackground(new Color(0x075AF5));
            boardButton[x2].setForeground(new Color(0xF4F4F6));
            boardButton[x3].setBackground(new Color(0x075AF5));
            boardButton[x3].setForeground(new Color(0xF4F4F6));
            textInfo.setText("Wygrał O!");
        }
        else {
            for(int i = 0; i < 9; i++){
                boardButton[i].setBackground(new Color(0x78808C));
                boardButton[i].setForeground(new Color(0x000000));
            }
            textInfo.setText("Remis!");
        }
        endGame(winner);
    }
}
