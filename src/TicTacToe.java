import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Timer;
import javax.swing.*;

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
    Random rand = new Random();
    int whoStart;
    Players playerFlag;
    boolean endGameFlag = false;
    boolean botFlag = false;
    Players playerSymbol;

    TicTacToe() {
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setSize(1000, 1000);
        mainWindow.getContentPane().setBackground(new Color(255, 255, 255));
        mainWindow.setVisible(true);
        mainWindow.setTitle("Kółko i krzyżyk");

        infoPanel.setLayout(new BorderLayout());
        infoPanel.setBounds(0, 0, 1000, 200);

        timePanel.setLayout(new BorderLayout());
        timePanel.setBounds(0, 0, 1000, 200);

        textInfo.setBackground(new Color(255, 255, 255));
        textInfo.setForeground(new Color(255, 0, 0));
        textInfo.setHorizontalAlignment(JLabel.CENTER);
        textInfo.setFont(new Font("Arial", Font.BOLD, 50));

        gameBoard.setLayout(new GridLayout(3, 3));

        timer = new Timer();
        timer.scheduleAtFixedRate(new Timers(), 0, 1000);

        for (int i = 0; i < 9; i++) {
            boardButton[i] = new JButton();
            gameBoard.add(boardButton[i]);
            boardButton[i].addActionListener(this);
            boardButton[i].setBackground(new Color(255, 255, 255));
            boardButton[i].setFont(new Font("Arial", Font.BOLD, 100));
        }

        infoPanel.add(textInfo);

        mainWindow.add(infoPanel, BorderLayout.NORTH);
        mainWindow.add(gameBoard);
        mainWindow.add(timePanel, BorderLayout.SOUTH);

        Start();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 9; i++) {
            if (e.getSource() == boardButton[i]) {
                if (!botFlag) {
                    if (playerFlag == Players.X) {
                        if (Objects.equals(boardButton[i].getText(), "")) {
                            boardButton[i].setForeground(new Color(0xF50707));
                            boardButton[i].setText("X");
                            playerFlag = Players.O;
                        }
                    } else {
                        if (Objects.equals(boardButton[i].getText(), "")) {
                            boardButton[i].setForeground(new Color(0x075AF5));
                            boardButton[i].setText("O");
                            playerFlag = Players.X;
                        }
                    }
                    moves++;
                    checkWin(Players.X, Players.O);
                } else {
                    if (Objects.equals(boardButton[i].getText(), "")) {
                        boardButton[i].setForeground(new Color(0xF50707));
                        switch (playerSymbol) {
                            case X:
                                boardButton[i].setText("X");
                                break;
                            case O:
                                boardButton[i].setText("O");
                                break;
                        }
                        moves++;
                        if (playerSymbol == Players.X) {
                            System.out.println("tu wlaze X " + i);
                            checkWin(Players.PLAYER, Players.BOT);
                        } else {
                            System.out.println("tu wlaze B" + i);
                            checkWin(Players.BOT, Players.PLAYER);
                        }
                        playerFlag = Players.BOT;
                        botMove();
                    }
                }
            }
        }
    }


    public void Start() {
        Object[] options = {"Gra z graczem", "Gra z botem"};
        int n = JOptionPane.showOptionDialog(mainWindow, "Wybierz tryb: ", "Witaj!", JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        whoStart = rand.nextInt(2);
        if (n == 0) {
            if (whoStart == 0) {
                playerFlag = Players.X;
                textInfo.setText("ZACZYNA X");
            } else {
                playerFlag = Players.O;
                textInfo.setText("ZACZYNA O");
            }
        } else {
            botFlag = true;
            if (whoStart == 0) {
                playerFlag = Players.PLAYER;
                textInfo.setText("ZACZYNASZ");
                playerSymbol = Players.X;
            } else {
                playerFlag = Players.BOT;
                textInfo.setText("TWOJA KOLEJ");
                playerSymbol = Players.O;
                botMove();
            }
        }

    }

    public void endGame(Players result) {
        String message;
        if (!botFlag) {
            if (result == Players.X) {
                message = "Wygral X!";
            } else if (result == Players.O) {
                message = "Wygral Y!";
            } else {
                message = "Remis!";
            }
        } else {
            System.out.println("check\n");
            if (result == Players.PLAYER) {
                message = "Wygraleś!";
            } else if (result == Players.BOT) {
                message = "Przegrałeś!";
            } else {
                message = "Remis!";
            }
        }
        Object[] buttons = {"Restart", "Wyjdź"};
        int check = JOptionPane.showOptionDialog(mainWindow, message + "\nChcesz zagrac jeszcze raz?", "Koniec gry!",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons, buttons[0]);
        if (check == 0) {
            for(int i = 0; i < 9; i++){
                boardButton[i].setText("");
            }
            mainWindow.dispose();
            new TicTacToe();
        } else {
            for(int i = 0; i < 9; i++){
                boardButton[i].setText("");
            }
            mainWindow.dispose();
        }
    }

    public void checkWin(Players player1, Players player2) {
        if (boardButton[0].getText().equals("X") && boardButton[1].getText().equals("X") && boardButton[2].getText().equals("X")) {System.out.println("tu wlaze1");
            setWin(player1, 0, 1, 2);
        } else if (boardButton[0].getText().equals("X") && boardButton[3].getText().equals("X") && boardButton[6].getText().equals("X")) {System.out.println("tu wlaze2");
            setWin(player1, 0, 3, 6);
        } else if (boardButton[0].getText().equals("X") && boardButton[4].getText().equals("X") && boardButton[8].getText().equals("X")) {System.out.println("tu wlaze3");
            setWin(player1, 0, 4, 8);
        } else if (boardButton[1].getText().equals("X") && boardButton[4].getText().equals("X") && boardButton[7].getText().equals("X")) {System.out.println("tu wlaze4");
            setWin(player1, 1, 4, 7);
        } else if (boardButton[2].getText().equals("X") && boardButton[5].getText().equals("X") && boardButton[8].getText().equals("X")) {System.out.println("tu wlaz5e");
            setWin(player1, 2, 5, 8);
        } else if (boardButton[3].getText().equals("X") && boardButton[4].getText().equals("X") && boardButton[5].getText().equals("X")) {System.out.println("tu wlaze6");
            setWin(player1, 3, 4, 5);
        } else if (boardButton[6].getText().equals("X") && boardButton[7].getText().equals("X") && boardButton[8].getText().equals("X")) {System.out.println("tu wlaze7");
            setWin(player1, 6, 7, 8);
        } else if (boardButton[2].getText().equals("X") && boardButton[4].getText().equals("X") && boardButton[6].getText().equals("X")) {System.out.println("tu wlaze8");
            setWin(player1, 2, 4, 6);
        } else if (boardButton[0].getText().equals("O") && boardButton[1].getText().equals("O") && boardButton[2].getText().equals("O")) {System.out.println("tu wlaze9");
            setWin(player2, 0, 1, 2);
        } else if (boardButton[0].getText().equals("O") && boardButton[3].getText().equals("O") && boardButton[6].getText().equals("O")) {System.out.println("tu wlaze10");
            setWin(player2, 0, 3, 6);
        } else if (boardButton[0].getText().equals("O") && boardButton[4].getText().equals("O") && boardButton[8].getText().equals("O")) {System.out.println("tu wlaze11");
            setWin(player2, 0, 4, 8);
        } else if (boardButton[1].getText().equals("O") && boardButton[4].getText().equals("O") && boardButton[7].getText().equals("O")) {System.out.println("tu wlaze12");
            setWin(player2, 1, 4, 7);
        } else if (boardButton[2].getText().equals("O") && boardButton[5].getText().equals("O") && boardButton[8].getText().equals("O")) {System.out.println("tu wlaze13");
            setWin(player2, 2, 5, 8);
        } else if (boardButton[3].getText().equals("O") && boardButton[4].getText().equals("O") && boardButton[5].getText().equals("O")) {System.out.println("tu wlaze14");
            setWin(player2, 3, 4, 5);
        } else if (boardButton[6].getText().equals("O") && boardButton[7].getText().equals("O") && boardButton[8].getText().equals("O")) {System.out.println("tu wlaze15");
            setWin(player2, 6, 7, 8);
        } else if (boardButton[2].getText().equals("O") && boardButton[4].getText().equals("O") && boardButton[6].getText().equals("O")) {System.out.println("tu wlaze16");
            setWin(player2, 2, 4, 6);
        } else if (moves == 9) {
            setWin(Players.TIE, 0, 0, 0);
        }
    }

    public void setWin(Players winner, int x1, int x2, int x3) {
        endGameFlag = true;
        System.out.println(winner);
        if (!botFlag) {
            if (winner == Players.X) {
                boardButton[x1].setBackground(new Color(0xF50707));
                boardButton[x1].setForeground(new Color(0xF4F4F6));
                boardButton[x2].setBackground(new Color(0xF50707));
                boardButton[x2].setForeground(new Color(0xF4F4F6));
                boardButton[x3].setBackground(new Color(0xF50707));
                boardButton[x3].setForeground(new Color(0xF4F4F6));
                textInfo.setText("Wygrał X!");
            } else if (winner == Players.O) {
                boardButton[x1].setBackground(new Color(0x075AF5));
                boardButton[x1].setForeground(new Color(0xF4F4F6));
                boardButton[x2].setBackground(new Color(0x075AF5));
                boardButton[x2].setForeground(new Color(0xF4F4F6));
                boardButton[x3].setBackground(new Color(0x075AF5));
                boardButton[x3].setForeground(new Color(0xF4F4F6));
                textInfo.setText("Wygrał O!");
            } else {
                for (int i = 0; i < 9; i++) {
                    boardButton[i].setBackground(new Color(0x78808C));
                    boardButton[i].setForeground(new Color(0x000000));
                }
                textInfo.setText("Remis!");
            }
        } else {
            if (winner == Players.PLAYER) {
                boardButton[x1].setBackground(new Color(0xF50707));
                boardButton[x1].setForeground(new Color(0xF4F4F6));
                boardButton[x2].setBackground(new Color(0xF50707));
                boardButton[x2].setForeground(new Color(0xF4F4F6));
                boardButton[x3].setBackground(new Color(0xF50707));
                boardButton[x3].setForeground(new Color(0xF4F4F6));
                textInfo.setText("Wygrałeś!");
            } else if (winner == Players.BOT) {
                boardButton[x1].setBackground(new Color(0x0C8160));
                boardButton[x1].setForeground(new Color(0xF4F4F6));
                boardButton[x2].setBackground(new Color(0x0C8160));
                boardButton[x2].setForeground(new Color(0xF4F4F6));
                boardButton[x3].setBackground(new Color(0x0C8160));
                boardButton[x3].setForeground(new Color(0xF4F4F6));
                textInfo.setText("Przegrałeś!");
            } else {
                for (int i = 0; i < 9; i++) {
                    boardButton[i].setBackground(new Color(0x78808C));
                    boardButton[i].setForeground(new Color(0x000000));
                }
                textInfo.setText("Remis!");
            }
        }
        endGame(winner);
    }

    public int checkLines(int x1, int x2, int x3) {
        if(!boardButton[x1].getText().equals("") && !boardButton[x2].getText().equals("") && boardButton[x3].getText().equals("")){
            return x3;
        }
        if(!boardButton[x1].getText().equals("") && boardButton[x2].getText().equals("") && !boardButton[x3].getText().equals("")){
            return x2;
        }
        if(boardButton[x1].getText().equals("") && !boardButton[x2].getText().equals("") && !boardButton[x3].getText().equals("")){
            return x1;
        }
        return -1;
    }

    public int checkPossibleMove() {
        int result = checkLines(0,3,6);
        if(result > 0) return result;
        result = checkLines(0,4,8);
        if(result > 0) return result;
        result = checkLines(0,1,2);
        if(result > 0) return result;
        result = checkLines(1,4,7);
        if(result > 0) return result;
        result = checkLines(2,5,8);
        if(result > 0) return result;
        result = checkLines(6,7,8);
        if(result > 0) return result;
        result = checkLines(2,4,6);
        if(result > 0) return result;
        result = checkLines(3,4,5);
        if(result > 0) return result;
        return -1;
    }


    public void botMove() {
        boolean check = false;
        int pole = checkPossibleMove();
        if (pole == -1) {
            while (!check) {
                int botTry = rand.nextInt(9);
                if (Objects.equals(boardButton[botTry].getText(), "")) {
                    boardButton[botTry].setForeground(new Color(0x0C8160));
                    switch (playerSymbol) {
                        case X:
                            boardButton[botTry].setText("O");
                            break;
                        case O:
                            boardButton[botTry].setText("X");
                            break;
                    }
                    check = true;
                }
            }
        }
        else {
            boardButton[pole].setForeground(new Color(0x0C8160));
            switch (playerSymbol) {
                case X:
                    boardButton[pole].setText("O");
                    break;
                case O:
                    boardButton[pole].setText("X");
                    break;
            }
        }
        moves++;
        if (playerSymbol == Players.X) {
            checkWin(Players.PLAYER, Players.BOT);
        } else {
            checkWin(Players.BOT, Players.PLAYER);
        }
        playerFlag = Players.PLAYER;
    }


    private class Timers extends TimerTask {
        @Override
        public void run() {
            if (moves > 0 && !endGameFlag) {
                if (playerFlag == Players.O) {
                    timerOfO++;
                    if (timerOfO < 10) {
                        textInfo.setText("Ruch gracza O! " + "Czas: 00:0" + timerOfO);
                    } else {
                        textInfo.setText("Ruch gracza O! " + "Czas: 00:" + timerOfO);
                    }
                } else {
                    timerOfX++;
                    if (timerOfX < 10) {
                        textInfo.setText("Ruch gracza X! " + "Czas: 00:0" + timerOfX);
                    } else {
                        textInfo.setText("Ruch gracza X! " + "Czas: 00:" + timerOfX);
                    }
                }
                textInfo.repaint();
            }
        }
    }
}
