package com.njok.forms;

import javax.swing.*;
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
    public JButton buttonPressed;
    private final JButton[][] buttonArray;
    private final Random random = new Random( 48239408);
    protected volatile boolean hasResetBeenPressed = false;
    private volatile boolean hasButtonBeenPressed = false;

    public Game() {

        this.setTitle("TicTacToe");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(750, 750);
        this.setVisible(true);

        buttonArray = initButtons(button1, button2, button3, button4, button5, button6, button7, button8, button9);
        reset.addActionListener(e -> hasResetBeenPressed = true);
        gameContainer.setVisible(true);
        this.add(gameContainer);

        System.out.println("Done w/ Setup");
        mainLoop(buttonArray);
    }

    /*
    * TODO:
    *   Check for a win (WinDetection.java)
    */
    public void mainLoop(JButton[][] buttons) {
        String currentPlayer;

        if (random.nextInt(5) % 2 == 0) {
            playerTurnDisplay.setText("O's Turn!");
            currentPlayer = "O";
        } else {
            playerTurnDisplay.setText("X's Turn!");
            currentPlayer = "X";
        }

        while (true) {
            while(!hasButtonBeenPressed) {
                Thread.onSpinWait();
            }
            hasButtonBeenPressed = false;
            buttonPressed.setText(currentPlayer);
            currentPlayer = Objects.equals(currentPlayer, "X") ? "O" : "X";

            if (WinDetection.isWin(buttons, currentPlayer)) {
                playerTurnDisplay.setText(currentPlayer + " has won!");
                while (!hasResetBeenPressed) {
                    //Beyblade Beyblade let it rip!
                    //Next line is courtesy of the IDE
                    Thread.onSpinWait();
                }
                break;
                //bruh
            }
        }


    }

    private JButton[][] initButtons(JButton... buttons) {
        JButton[][] buttonGridArray = new JButton[buttons.length / 3][buttons.length / 3];

        for (JButton button : buttons) {

            button.setText("-");
            button.addActionListener(e -> {
                buttonPressed = (JButton) e.getSource();
                buttonPressed.setEnabled(false);
                hasButtonBeenPressed = true;
            });

        }

        for (int i = 0; i < buttonGridArray.length; i++) {
            for (int j = 0; j < buttonGridArray[i].length; j++) {
                buttonGridArray[i][j] = buttons[i * j + j];
            }
        }

        return buttonGridArray;
    }

    private void resetGame() {
        for (int i = 0; i < buttonArray.length; i++) {
            for (int j = 0; j < buttonArray[i].length; i++) {
                buttonArray[i][j].setText("-");
            }
        }
    }

}
