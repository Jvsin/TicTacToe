import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;
import java.util.Timer;
import javax.swing.*;
import static java.lang.System.exit;

public class TicTacToe implements ActionListener {
    JFrame mainWindow = new JFrame();
    JPanel infoPanel = new JPanel();
    JPanel gameBoard = new JPanel();
    JLabel textInfo = new JLabel();
    JButton[] boardButton = new JButton[9];
    JPanel timePanel = new JPanel();
    Timer timer;
    int timerOfX = 0;
    int timerOfO = 0;
    int moves = 0;
    //Random rand = new Random();
    //int whoStart;
    Players playerFlag;
    boolean endGameFlag = false;

    Socket socket = null;
    PrintWriter sender = null;
    BufferedReader reader = null;

    TicTacToe() {
        connectToServer();

        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setSize(1000,1000);
        mainWindow.getContentPane().setBackground(new Color(255,255,255));
        mainWindow.setVisible(true);
        mainWindow.setTitle("Kółko i krzyżyk");

        infoPanel.setLayout(new BorderLayout());
        infoPanel.setBounds(0,0,1000,200);

        timePanel.setLayout(new BorderLayout());
        timePanel.setBounds(0,0,1000,200);

        textInfo.setBackground(new Color(255,255,255));
        textInfo.setForeground(new Color(255,0,0));
        textInfo.setHorizontalAlignment(JLabel.CENTER);
        textInfo.setFont(new Font( "Arial",Font.BOLD,50));

        gameBoard.setLayout(new GridLayout(3,3));

        timer = new Timer();
        timer.scheduleAtFixedRate(new Timers(),0,1000);
        timer.scheduleAtFixedRate(new readString(),0,1000);

        for(int i = 0; i < 9; i++){
            boardButton[i] = new JButton();
            gameBoard.add(boardButton[i]);
            boardButton[i].addActionListener(this);
            boardButton[i].setBackground(new Color(255, 255, 255));
            boardButton[i].setFont(new Font("Arial",Font.BOLD,100));
            boardButton[i].setText(" ");
        }

        infoPanel.add(textInfo);

        mainWindow.add(infoPanel, BorderLayout.NORTH);
        mainWindow.add(gameBoard);
        mainWindow.add(timePanel,BorderLayout.SOUTH);

        Start();
    }

    public void connectToServer () {
        try {
            socket = new Socket("localhost", 2137);
        } catch (UnknownHostException e) {
            exit(1);
        } catch (IOException e) {
            System.out.println(e);
            exit(2);
        }

        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            sender = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            exit(3);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(int i = 0; i < 9; i++){
            if(e.getSource() == boardButton[i]){
                if(playerFlag == Players.O) {
                    if (Objects.equals(boardButton[i].getText(), " ")) {
                        boardButton[i].setForeground(new Color(0x075AF5));
                        boardButton[i].setText("O");
                        sendString();
                        playerFlag = Players.X;
                        checkWin();
                    }
                }
            }
        }
    }

    public void Start(){
        playerFlag = Players.X;
        textInfo.setText("ZACZYNA X");
    }
    public void endGame(Players result)  {
        String message;
        if(result == Players.X){
            message = "Wygral X!";
        }
        else if(result == Players.O){
            message = "Wygral O!";
        }
        else {
            message = "Remis!";
        }
        message += "\nAby zagrać ponownie, połącz się z serwerem jeszcze raz";
        JOptionPane.showMessageDialog(null,message);
        try{
            socket.close();
            sender.close();
            reader.close();
        }
        catch(IOException e){
            System.out.println("Closing client error");
            exit(2);
        }

        mainWindow.dispose();
//        Object[] buttons ={"Restart","Wyjdź"};
//        int check = JOptionPane.showOptionDialog(mainWindow, message + "\nChcesz zagrac jeszcze raz?","Koniec gry!",
//                JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,buttons,buttons[0]);
//        if(check == 0){
//            mainWindow.dispose();
//            new TicTacToe();
//        }
//        else{
//            mainWindow.dispose();
//        }
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
            sendString();
            setWin(Players.TIE,0,0,0);
        }
    }

    public void setWin(Players winner,int x1,int x2, int x3){
        endGameFlag = true;
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

    private void sendString() {
        sender.println("Start");
        String s = "";
        for ( int i = 0 ; i < 9 ; i++) {
            s += boardButton[i].getText();
        }
        sender.println(s);
    }


    private class readString extends TimerTask {
        @Override
        public void run() {
            try {
                if(!reader.ready()) {
                    mainWindow.repaint();
                    return;
                }

                String wejscie = reader.readLine();
                if (wejscie.equals("Start")) {
                    String s = reader.readLine();
                    for (int i = 0 ; i < 9 ; i++) {
                        boardButton[i].setText(String.valueOf(s.charAt(i)));
                        if(!String.valueOf(s.charAt(i)).equals(" ")){
                            moves++;
                        }
                    }
                    System.out.println(moves);
                    playerFlag = Players.O;
                    checkWin();
                    moves = 0;
                }
            } catch (IOException e) {
                exit(4);
            }
            mainWindow.repaint();
            textInfo.repaint();
        }
    }


    private class Timers extends TimerTask {
        @Override
        public void run() {
            if ( moves >= 0 && !endGameFlag) {
                if (playerFlag == Players.O) {
                    timerOfO++;
                    if(timerOfO < 10){
                        textInfo.setText("Ruch gracza O! " + "Czas: 00:0" + timerOfO);
                    }
                    else {
                        textInfo.setText("Ruch gracza O! " + "Czas: 00:" + timerOfO);
                    }
                }
                else {
                    timerOfX++;
                    if(timerOfX < 10){
                        textInfo.setText("Ruch gracza X! " + "Czas: 00:0" + timerOfX);
                    }
                    else {
                        textInfo.setText("Ruch gracza X! " + "Czas: 00:" + timerOfX);
                    }
                }
                textInfo.repaint();
            }
        }
    }
}
