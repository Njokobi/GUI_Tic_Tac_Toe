package com.njok.GameUtils;

import javax.swing.*;
import java.util.Objects;

public class StateDetection {

    public static boolean isWin(JButton[][] board, String player) {
        String prevPlayer = Objects.equals(player, "X") ? "O" : "X";
        return verticalMatch(board, prevPlayer) || diagonalMatch(board, prevPlayer) || horizontalMatch(board, prevPlayer);
    }

    private static boolean diagonalMatch(JButton[][] board, String player) {
        if (Objects.equals(board[0][0].getText(), player) && Objects.equals(board[1][1].getText(), player) && Objects.equals(board[2][2].getText(), player)) return true;
        else return Objects.equals(board[0][2].getText(), player) && Objects.equals(board[1][1].getText(), player) && Objects.equals(board[2][0].getText(), player);
    }

    private static boolean horizontalMatch(JButton[][] board, String player) {
        for (JButton[] jButtons : board) {
            if (Objects.equals(jButtons[0].getText(), player) && Objects.equals(player, jButtons[1].getText()) && Objects.equals(player, jButtons[2].getText())) {
                return true;
            }
        }
        return false;
    }

    private static boolean verticalMatch(JButton[][] board, String player) {

        for (int i = 0; i < board.length; i++) {
            if (Objects.equals(board[0][i].getText(), player) && Objects.equals(board[1][i].getText(), player) && Objects.equals(board[2][i].getText(), player)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isBoardDisabled(JButton[][] buttons) {
        for (JButton[] jButtons : buttons) {
            for (JButton button : jButtons) {
                if (button.isEnabled()) {
                    return false;
                }
            }
        }

        return true;
    }

}
