package com.njok.forms;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.Random;
import com.njok.GameUtils.*;

public class Game extends JFrame{
    private JPanel gameContainer;
    private JPanel titleText;
    private JLabel title;
    private JButton button4;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button5;
    private JButton button6;
    private JButton button7;
    private JButton button8;
    private JButton button9;
    private JPanel resetButton;
    private JButton reset;
    private JPanel gameButtons;
    private JPanel playerTurn;
    private JLabel playerTurnDisplay;
    private JPanel titleContainer;
    public JButton buttonPressed;
    private final JButton[][] buttonArray;
    private final Random random = new Random( 48239408);
    private Thread t;
    private String currentPlayer;
    private final Color buttonColor = new Color(204, 204, 0);
    private volatile boolean hasResetBeenPressed = false;
    private volatile boolean hasButtonBeenPressed = false;

    /*
         Class constructor that initialises the JFrame and its components

                @params
                    none
     */
    public Game() throws InterruptedException {
        final Color panelColor = new Color(238, 232, 170);

        this.setTitle("TicTacToe");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(750, 750);
        this.setVisible(true);
        this.setBackground(panelColor);


        gameContainer.setBackground(panelColor);
        titleText.setBackground(panelColor);
        playerTurn.setBackground(panelColor);
        resetButton.setBackground(panelColor);
        gameButtons.setBackground(panelColor);
        titleContainer.setBackground(panelColor);
        reset.setBackground(buttonColor);

        buttonArray = initButtons(button1, button2, button3, button4, button5, button6, button7, button8, button9);
        reset.addActionListener(e -> {
            hasResetBeenPressed = true;
            resetGame();
        });
        gameContainer.setVisible(true);
        this.add(gameContainer);

        System.out.println("Done w/ Setup");
        mainLoop(buttonArray);
    }

    /*
         Runs the main loop of events needed to play the game

                @params
                    JButton[][] buttons
                        The array of buttons on which operations will be performed
     */
    public void mainLoop(JButton[][] buttons) throws InterruptedException {

        if (random.nextInt(5) % 2 == 0) {
            playerTurnDisplay.setText("O's Turn!");
            currentPlayer = "O";
        } else {
            playerTurnDisplay.setText("X's Turn!");
            currentPlayer = "X";
        }

        //IntelliJ I know what I'm doing :100:
        while (true) {
            while (!hasButtonBeenPressed) {
                //Beyblade
                Thread.onSpinWait();

            }
            hasButtonBeenPressed = false;

            //If the turn display updates pre-currentPlayer update we get desync
            t.join();
            playerTurnDisplay.setText(Objects.equals(currentPlayer, "X") ? "X's Turn!" : "O's Turn!");
            gameButtons.updateUI();
            playerTurn.updateUI();

            if (StateDetection.isWin(buttons, currentPlayer)) {
                playerTurnDisplay.setText(Objects.equals(currentPlayer, "X") ? "O has won!" : "X has won!");
                lockAll();
                while (!hasResetBeenPressed) {
                    //Next line is courtesy of the IDE
                    Thread.onSpinWait();
                }
            } else if (StateDetection.isBoardDisabled(buttons) && !StateDetection.isWin(buttons, currentPlayer)) {
                playerTurnDisplay.setText("It's a Tie! Press reset to play again!");
                while (!hasResetBeenPressed) {
                    Thread.onSpinWait();
                }
            }
        }

    }

    /*
         Initializes the buttons passed to it into a 3x3 Array

                @params
                    JButton... buttons
                        The array of buttons on which operations will be performed (varargs because we cannot be bothered to create an array)
     */
    private JButton[][] initButtons(JButton... buttons) {
        JButton[][] buttonGridArray = new JButton[3][3];

        for (JButton button : buttons) {

            button.setText("-");
            button.setFont(new Font("JetBrains Mono", Font.BOLD, 40));
            button.setBackground(buttonColor);
            button.setForeground(buttonColor);
            button.addActionListener(e -> {
                t = new Thread(() -> {
                    buttonPressed = (JButton) e.getSource();
                    buttonPressed.setEnabled(false);
                    hasButtonBeenPressed = true;
                    buttonPressed.setText(currentPlayer);
                    currentPlayer = Objects.equals(currentPlayer, "X") ? "O" : "X";
                });

                t.start();
            });
        }

        int h = 0;
        for (int i = 0; i < buttonGridArray.length; i++) {
            for (int j = 0; j < buttonGridArray[i].length; j++) {
                buttonGridArray[i][j] = buttons[h];
                h++;
            }
        }

        return buttonGridArray;
    }

    /*
         Resets the game to a first run state

                @params
                    none
     */
    private void resetGame() {
        for (JButton[] jButtons : buttonArray) {
            for (JButton jButton : jButtons) {
                jButton.setText("-");
                jButton.setEnabled(true);
            }
        }
        hasResetBeenPressed = false;
        if (random.nextInt() % 2 == 0) {
            playerTurnDisplay.setText("X's Turn!");
            currentPlayer = "X";
        } else {
            playerTurnDisplay.setText("O's Turn!");
            currentPlayer = "O";
        }

    }

    /*
         Locks all buttons on the game board

                @params
                    none
     */
    private void lockAll() {
        for (JButton[] jButtons : buttonArray) {
            for (JButton button : jButtons) {
                button.setEnabled(false);
            }
        }
    }

}
