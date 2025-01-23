package com.njok.GameUtils;

import javax.swing.*;
import java.util.Objects;

public class StateDetection {

    public static boolean isWin(JButton[][] board, String player) {
        if (diagonalMatch(board, player)) {
            return true;
        } else if (horizontalMatch(board, player)) {
            return true;
        } else if (verticalMatch(board, player)) {
            return true;
        }

        return false;
    }

    private static boolean diagonalMatch(JButton[][] board, String player) {
        //The easy part
        int count = 0;
        for (int i = 0; i < board.length; i++) {
            if (Objects.equals(board[i][i].getText(), player)) {
                count++;
            }
        }

        if (count == 3) {
            return true;
        }
        count = 0;

        //The harder part
        for (int i = 0; i < board.length; i++) {
            if (i == 0) {
                count += Objects.equals(board[i][2].getText(), player) ? 1 : 0;
            } else if (i == 2) {
                count += Objects.equals(board[i][0].getText(), player) ? 1 : 0;
            } else {
                count += Objects.equals(board[i][i].getText(), player) ? 1 : 0;
            }
            if (count != i + 1) {
                return false;
            }
        }

        return true;
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
